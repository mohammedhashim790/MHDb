package com.mhdb.mhdb.Bloc.Exceptions;


/**
 * Exception thrown when a password reset token has expired.
 *
 */
public class TokenExpiredException extends Exception {
    public TokenExpiredException() {
        super("Password Reset Token expired");
    }
}
