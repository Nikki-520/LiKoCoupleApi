package com.liko.config;

import com.liko.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class JwtInterceptor implements HandlerInterceptor {

    private static final String[] WHITELIST = {"/api/auth/login"};

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String path = request.getRequestURI();
        String ctx = request.getContextPath();
        if (ctx != null && !ctx.isEmpty()) {
            path = path.substring(ctx.length());
        }

        for (String wl : WHITELIST) {
            if (wl.equals(path)) {
                return true;
            }
        }

        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            response.setStatus(401);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"code\":401,\"message\":\"未登录\"}");
            return false;
        }

        String token = header.substring(7);
        try {
            Claims claims = JwtUtil.parse(token);
            request.setAttribute("userId", Long.parseLong(claims.getSubject()));
            request.setAttribute("coupleId", claims.get("coupleId", Long.class));
            request.setAttribute("role", claims.get("role", String.class));
        } catch (Exception e) {
            response.setStatus(401);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"code\":401,\"message\":\"Token已过期\"}");
            return false;
        }

        return true;
    }
}
