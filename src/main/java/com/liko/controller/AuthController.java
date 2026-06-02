package com.liko.controller;

import com.liko.dto.ApiResult;
import com.liko.dto.LoginRequest;
import com.liko.service.AuthService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/couple-api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ApiResult<?> login(@RequestBody LoginRequest req) {
        Map<String, Object> data = authService.login(req.getUsername(), req.getPassword());
        if (data == null) {
            return ApiResult.fail(400, "用户名或密码错误");
        }
        return ApiResult.ok(data);
    }

    @GetMapping("/me")
    public ApiResult<?> me(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        Map<String, Object> data = authService.me(userId);
        if (data == null) {
            return ApiResult.fail(401, "用户不存在");
        }
        return ApiResult.ok(data);
    }
}
