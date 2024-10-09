package com.shashikant.linkedin.user_service.utils;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtil {

    //Hash a password for the first time
    public static String hashPassword(String plainTextPassword){
        return BCrypt.hashpw(plainTextPassword,BCrypt.gensalt());
    }

    //Check that a Plain text password matches a previously hashed one
    public  static boolean checkPassword(String plainTestPassword, String hashedPassword){
        return BCrypt.checkpw(plainTestPassword,hashedPassword);
    }


}
