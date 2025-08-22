package com.mhdb.mhdb.Bloc.Utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Builder {

    public String encryptPassword(String password) {
        try{
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());

            byte[] bytes = md.digest();
            StringBuilder sb = new StringBuilder();
            for (byte aByte : bytes) {
                sb.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
            }
            return sb.toString();
        }catch (NoSuchAlgorithmException e){
            return password;
        }
    }
}
