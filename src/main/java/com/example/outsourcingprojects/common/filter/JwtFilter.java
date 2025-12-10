package com.example.outsourcingprojects.common.filter;

import com.example.outsourcingprojects.common.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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

        if (requestURI.equals("/api/users") || requestURI.equals("/api/auth/login")) {
            filterChain.doFilter(request, response);
            return;
        }

        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader == null || authorizationHeader.isBlank()) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "JWT가 필요합니다.");
            return;
        }

        String jwt = authorizationHeader.substring(7);

        if (!jwtUtil.validateToken(jwt)) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write("error: Unauthorized");
        }

        Long userId = jwtUtil.extractUserId(jwt);
        String username = jwtUtil.extractUsername(jwt);
        String userRole = jwtUtil.extractUserRole(jwt);
//        UserRoleType userRoleType = UserUserRoleType.get(userRole);

        request.setAttribute("subject", userId);
        request.setAttribute("username", username);
        request.setAttribute("userRole", userRole);

        User user = new User(username, "", List.of());
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities()));

        filterChain.doFilter(request, response);
    }
}
