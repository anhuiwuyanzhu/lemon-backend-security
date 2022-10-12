package com.lemon.violet.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Test {
    public static void main(String[] args) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encode = passwordEncoder.encode("1234");
        boolean isT = passwordEncoder.matches("1234", encode);

        System.out.println(encode);
        System.out.println(isT);

    }
}
