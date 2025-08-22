package com.mhdb.mhdb.Bloc.Loggers;

import java.util.logging.Level;
import java.util.logging.Logger;

public class AppLogger implements ILogger {

    protected final Logger logger = new LoggerUtility().getLogger("");

    private static AppLogger instance;

    public static AppLogger getAppLogger() {
        if (instance == null) {
            instance = new AppLogger();
        }
        return instance;
    }

    private AppLogger() {
    }

    @Override
    public void logInfo(String message) {
        logger.log(Level.INFO, message);
    }

    @Override
    public void logInfo(String message, Object... args) {
        logger.log(Level.INFO, message, args);
    }


    @Override
    public void logError(String message) {
        logger.log(Level.SEVERE, message);
    }


    @Override
    public void logError(String message, Object... args) {
        logger.log(Level.SEVERE, message, args);
    }


    @Override
    public void logError(String message, Throwable throwable) {
        logger.log(Level.SEVERE, message, throwable);
    }


    @Override
    public void logError(String message, Throwable throwable, Object... args) {
        logger.log(Level.SEVERE, message, throwable);
    }


    @Override
    public void logError(Throwable throwable) {
        logger.log(Level.SEVERE, throwable.getMessage());
    }


    @Override
    public void logError(Throwable throwable, Object... args) {
        logger.log(Level.SEVERE, throwable.getMessage(), args);
    }


    @Override
    public void logWarning(String message) {
        logger.log(Level.WARNING, message);
    }


    @Override
    public void logWarning(String message, Object... args) {
        logger.log(Level.WARNING, message, args);
    }
}
