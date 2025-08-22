package com.mhdb.mhdb.Bloc.Exceptions;


/**
 * An exception throw InvalidPattern.
 */
public class InvalidPattern extends RuntimeException {
    public InvalidPattern() {
        super("The pattern is invalid. Try again.");
    }
}
