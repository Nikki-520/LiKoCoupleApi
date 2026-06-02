package com.liko.util;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class PasswordUtil {

    public static String encode(String username, String raw) {
        return BCrypt.hashpw(username + ":" + raw, BCrypt.gensalt());
    }

    public static boolean matches(String username, String raw, String encoded) {
        return BCrypt.checkpw(username + ":" + raw, encoded);
    }

    public static void main(String[] args) {
        String username = args.length > 0 ? args[0] : "liu";
        String raw = args.length > 1 ? args[1] : "123456";
        String encoded = encode(username, raw);
        System.out.println("用户: " + username);
        System.out.println("原文: " + raw);
        System.out.println("密文: " + encoded);
    }
}
