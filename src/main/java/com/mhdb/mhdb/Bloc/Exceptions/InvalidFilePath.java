package com.mhdb.mhdb.Bloc.Exceptions;


/**
 * Custom exception - InvalidFilePath, is thrown when the controller is trying to create entity.
 */
public class InvalidFilePath extends Exception {
    @Override
    public String getMessage() {
        return "Invalid file path";
    }
}
