package com.example.outsourcingprojects.common.filter;

import com.example.outsourcingprojects.common.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String requestURI = request.getRequestURI();

        // 인증이 필요 없는 경로 처리
        if (requestURI.equals("/api/users") || requestURI.equals("/api/auth/login")) {
            filterChain.doFilter(request, response);
            return;
        }

        String authorizationHeader = request.getHeader("Authorization");

        // Authorization 헤더 체크
        if (authorizationHeader == null || authorizationHeader.isBlank()) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "JWT가 필요합니다.");
            return;
        }

        // Bearer 체크
        if (!authorizationHeader.startsWith("Bearer ")) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "JWT가 존재하지 않습니다.");
            return;
        }

        // JWT 추출
        String jwt = authorizationHeader.substring(7);

        // JWT 유효성 검사
        if (!jwtUtil.validateToken(jwt)) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\": \"Unauthorized\"}");
            return;
        }

        // JWT에서 사용자 정보 추출
        Long userId = jwtUtil.extractUserId(jwt);
        String username = jwtUtil.extractUsername(jwt);
        String userRole = jwtUtil.extractUserRole(jwt);

        // 요청에 사용자 정보 저장
        request.setAttribute("userId", userId);
        request.setAttribute("username", username);
        request.setAttribute("role", userRole);

        // 인증된 사용자 정보 설정
        List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(userRole));

        User user = new User(username, "", authorities);
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities()));

        // 다음 필터로 전달
        filterChain.doFilter(request, response);
    }
}
