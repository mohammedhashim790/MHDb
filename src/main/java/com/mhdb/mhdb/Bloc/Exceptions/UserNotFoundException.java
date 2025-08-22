package com.mhdb.mhdb.Bloc.Exceptions;


/**
 * .UserNotFoundException is thrown when the user is signing in with a username not present in the database.
 */
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {
        super("User not found");
    }
}
