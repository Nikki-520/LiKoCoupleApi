package com.liko.service;

import com.liko.mapper.CoupleMapper;
import com.liko.mapper.UserMapper;
import com.liko.model.Couple;
import com.liko.model.User;
import com.liko.util.JwtUtil;
import com.liko.util.PasswordUtil;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class AuthService {

    private final UserMapper userMapper;
    private final CoupleMapper coupleMapper;

    public AuthService(UserMapper userMapper, CoupleMapper coupleMapper) {
        this.userMapper = userMapper;
        this.coupleMapper = coupleMapper;
    }

    public Map<String, Object> login(String username, String password) {
        User user = userMapper.findByUsername(username);
        if (user == null) {
            return null;
        }
        if (!PasswordUtil.matches(username, password, user.getPassword())) {
            return null;
        }

        String token = JwtUtil.generate(user.getId(), user.getCoupleId(), user.getRole());

        Couple couple = coupleMapper.findById(user.getCoupleId());

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("token", token);

        Map<String, Object> userMap = new LinkedHashMap<>();
        userMap.put("id", user.getId());
        userMap.put("username", user.getUsername());
        userMap.put("nickname", user.getNickname());
        userMap.put("role", user.getRole());
        result.put("user", userMap);

        Map<String, Object> coupleMap = new LinkedHashMap<>();
        coupleMap.put("id", couple.getId());
        coupleMap.put("name", couple.getName());
        coupleMap.put("startDate", couple.getStartDate());
        coupleMap.put("days", couple.getDays());
        result.put("couple", coupleMap);

        return result;
    }

    public Map<String, Object> me(Long userId) {
        User user = userMapper.findById(userId);
        if (user == null) {
            return null;
        }
        Couple couple = coupleMapper.findById(user.getCoupleId());

        Map<String, Object> result = new LinkedHashMap<>();

        Map<String, Object> userMap = new LinkedHashMap<>();
        userMap.put("id", user.getId());
        userMap.put("username", user.getUsername());
        userMap.put("nickname", user.getNickname());
        userMap.put("role", user.getRole());
        result.put("user", userMap);

        Map<String, Object> coupleMap = new LinkedHashMap<>();
        coupleMap.put("id", couple.getId());
        coupleMap.put("name", couple.getName());
        coupleMap.put("startDate", couple.getStartDate());
        coupleMap.put("days", couple.getDays());
        result.put("couple", coupleMap);

        return result;
    }
}
