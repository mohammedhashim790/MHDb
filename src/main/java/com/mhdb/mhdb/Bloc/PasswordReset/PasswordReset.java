package com.mhdb.mhdb.Bloc.PasswordReset;

public class PasswordReset {

    String token;
    String username;
    long expiryTime;

    public PasswordReset(String username, String token) {
        this.username = username;
        this.token = token;
        this.expiryTime = System.currentTimeMillis() + 15 * 60 * 1000; // Token valid for 15 minutes.
    }

    public boolean isValid() {
        return expiryTime > System.currentTimeMillis();
    }
}
