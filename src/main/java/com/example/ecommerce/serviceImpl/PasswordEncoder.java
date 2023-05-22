package com.example.ecommerce.serviceImpl;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class PasswordEncoder {
    public static String hashPassword(String password){
        String salt = BCrypt.gensalt(16);
        return BCrypt.hashpw(password,salt);
    }
    public static boolean checkPwd(String rawPass,String hashedPass){
        return BCrypt.checkpw(rawPass, hashedPass);

    }
}
