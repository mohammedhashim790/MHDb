package com.mhdb.mhdb.Models.Authentication;

import com.mhdb.mhdb.Bloc.Utils.MD5Builder;

import java.util.Random;

public class Authentication {

    public String index = String.valueOf(new Random().nextInt());
    public String username;
    public String password;

    public String lastLoginAttempt = System.currentTimeMillis() + "";

    public Authentication() {
    }

    public Authentication(String username, String password) {
        this.username = username;
        this.password = new MD5Builder().encryptPassword(password);
    }

    public Authentication(String index,String username, String password) {
        this.index = index;
        this.username = username;
        this.password = new MD5Builder().encryptPassword(password);
    }

    @Override
    public boolean equals(Object obj) {
        return ((Authentication) obj).password.equals(this.password) && ((Authentication) obj).username.equals(this.username);
    }
}
