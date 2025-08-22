package com.mhdb.mhdb.Bloc.Loggers;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.logging.*;

class LoggerUtility {
    private final static Logger logger = Logger.getLogger(LoggerUtility.class.getName());

    private void buildLogger() {
        try {
            String pathName = String.format("%s/logs/%s.log", System.getProperty("user.dir"), LocalDate.now());
            File file = new File(pathName);
            if (!file.exists()) file.getParentFile().mkdirs();
            FileHandler fileHandler = new FileHandler(pathName, true);
            fileHandler.setFormatter(new FileFormatter());
            ConsoleHandler consoleHandler = new ConsoleHandler();
            consoleHandler.setFormatter(new ConsoleFormatter());
            logger.addHandler(consoleHandler);
            logger.addHandler(fileHandler);
            logger.setLevel(Level.ALL);
            logger.setUseParentHandlers(false); // Prevent console logging
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Failed to initialize logger: " + e.getMessage());
        }
    }

    public Logger getLogger(String loggerName) {
        buildLogger();
        return logger;
    }


    /**
     * [ConsoleFormatter] is a private class to format logs in specific format.
     * The log will be in the following format
     * [color] [timestamp] - [LogLevel] - [Log messagfe]
     */
    static class ConsoleFormatter extends Formatter {
        private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        // ANSI Color Codes for Console Output
        private static final String RESET = "\u001B[0m";
        private static final String RED = "\u001B[31m"; // SEVERE
        private static final String YELLOW = "\u001B[33m"; // WARNING
        private static final String GREEN = "\u001B[32m"; // INFO

        public ConsoleFormatter() {

        }

        @Override
        public String format(LogRecord record) {
            String timestamp = dateFormat.format(new Date(record.getMillis()));
            String level = record.getLevel().getName();
            String message = record.getMessage();
            String color;
            switch (record.getLevel().getName()) {
                case "SEVERE":
                    color = RED;
                    // For Error, format with throwable object
                    message = String.format("[%s] %s %s", timestamp, message, record.getThrown() == null ? "" : record.getThrown().getMessage());
                    break;
                case "WARNING":
                    color = YELLOW;
                    break;
                case "INFO":
                default:
                    color = GREEN;
                    break;
            }
            String formattedMessage = String.format("[%s] [%s] %s%n", timestamp, level, message);
            if (record.getLoggerName().equals(LoggerUtility.class.getName())) {
                return color + formattedMessage + RESET;
            }
            return formattedMessage;
        }
    }


    /**
     * [FileFormatter] is a private class to format logs in specific format.
     * The log will be in the following format
     * [timestamp] - [LogLevel] - [Log messagfe]
     */
    static class FileFormatter extends Formatter {
        private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        @Override
        public String format(LogRecord record) {
            String timestamp = dateFormat.format(new Date(record.getMillis()));
            String level = record.getLevel().getName();
            String message = record.getMessage();
            return String.format("[%s] [%s] %s%n", timestamp, level, message);
        }
    }


}
