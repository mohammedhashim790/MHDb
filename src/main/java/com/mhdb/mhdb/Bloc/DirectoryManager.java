package com.mhdb.mhdb.Bloc;

import com.mhdb.mhdb.Bloc.Exceptions.InvalidFilePath;
import com.mhdb.mhdb.Bloc.Loggers.AppLogger;

import java.io.File;

public class DirectoryManager {
    private static final AppLogger logger = AppLogger.getAppLogger();

    /**
     * Creates Directory in the path specified.
     * @param path
     * @param directoryName
     * @return
     * @throws InvalidFilePath
     */
    public static String CreateDirectory(String path, String directoryName) throws InvalidFilePath {
        File file = new File(path);
        boolean directoryCreated = file.mkdir();
        if (!directoryCreated) {
            throw new InvalidFilePath();
        }
        logger.logInfo("Created Directory: " + directoryName);
        return file.getAbsolutePath();
    }

}
