package com.mhdb.mhdb.Bloc.Loggers;

public interface ILogger {

    /**
     * Logs an information message.
     *
     * @param message The message to log.
     */
    void logInfo(String message);

    /**
     * Logs an information message with additional arguments.
     *
     * @param message The message to log.
     * @param args    Additional arguments to format the message.
     */
    void logInfo(String message, Object... args);

    /**
     * Logs an error message.
     *
     * @param message The error message to log.
     */
    void logError(String message);

    /**
     * Logs an error message with additional arguments.
     *
     * @param message The error message to log.
     * @param args    Additional arguments to format the message.
     */
    void logError(String message, Object... args);

    /**
     * Logs an error message along with a throwable (exception) for more details.
     *
     * @param message   The error message to log.
     * @param throwable The throwable that caused the error.
     */
    void logError(String message, Throwable throwable);

    /**
     * Logs an error message, throwable, and additional arguments.
     *
     * @param message   The error message to log.
     * @param throwable The throwable that caused the error.
     * @param args      Additional arguments to format the message.
     */
    void logError(String message, Throwable throwable, Object... args);

    /**
     * Logs an error with the provided throwable (exception).
     *
     * @param throwable The throwable that caused the error.
     */
    void logError(Throwable throwable);

    /**
     * Logs an error with the provided throwable (exception) and additional arguments.
     *
     * @param throwable The throwable that caused the error.
     * @param args      Additional arguments to format the message.
     */
    void logError(Throwable throwable, Object... args);

    /**
     * Logs a warning message.
     *
     * @param message The warning message to log.
     */
    void logWarning(String message);

    /**
     * Logs a warning message with additional arguments.
     *
     * @param message The warning message to log.
     * @param args    Additional arguments to format the message.
     */
    void logWarning(String message, Object... args);
}
