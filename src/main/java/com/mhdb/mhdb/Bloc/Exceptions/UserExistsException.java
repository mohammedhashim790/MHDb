package com.mhdb.mhdb.Bloc.Exceptions;


/**
 * An exception throw UserExistsException when user is creating a new user.
 */
public class UserExistsException extends RuntimeException {
    public UserExistsException() {
        super("User Already Found");
    }
}
