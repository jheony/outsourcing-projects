/**
package com.example.outsourcingprojects.common.filter;

import com.example.outsourcingprojects.common.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String requestURL = request.getRequestURI();
        if(requestURL.equals("/login")){
            filterChain.doFilter(request, response);
            return;
        }

        String authorizationHeader = request.getHeader("Authorization");
        if(authorizationHeader == null || authorizationHeader.isBlank()){
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "JWT가 필요합니다.");
            return;
        }

        String jwt = authorizationHeader.substring(7);
        if(!jwtUtil.validateToken(jwt)){
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write("error: Unauthorized");
        }

        filterChain.doFilter(request, response);
    }
}
**/