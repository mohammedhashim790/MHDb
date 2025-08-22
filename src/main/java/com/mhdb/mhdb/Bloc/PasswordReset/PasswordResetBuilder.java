package com.mhdb.mhdb.Bloc.PasswordReset;

import java.security.SecureRandom;
import java.util.Base64;

public class PasswordResetBuilder {


    public static PasswordReset generatePasswordReset(String username) {
        return new PasswordReset(username, generateToken());
    }


    /**
     * <a href="https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/security/SecureRandom.html">Secure Random</a>
     *
     * @return String - a Token to validate reset password.
     */
    public static String generateToken() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[32];
        random.nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }
}
