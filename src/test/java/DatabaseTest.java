import com.mhdb.mhdb.Bloc.Loggers.AppLogger;
import com.mhdb.mhdb.Controllers.AuthenticationStorageController.AuthenticationStorageController;
import com.mhdb.mhdb.Controllers.DatabaseStorageController.DatabaseStorageController;
import com.mhdb.mhdb.Models.Authentication.Authentication;

import java.io.IOException;

public class DatabaseTest {

    private static final AppLogger appLogger = AppLogger.getAppLogger();

    public static void main(String[] args) {
        testLogin();
        testNewDatabase();
        testUseDatabase();
    }

    private static void testLogin() {
        AuthenticationStorageController authenticationStorageController = new AuthenticationStorageController();
        try {
            authenticationStorageController.authenticate(new Authentication("admin", "admin"));
            appLogger.logInfo("Authentication Passed");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private static void testNewDatabase() {
        DatabaseStorageController databaseStorageController = DatabaseStorageController.getInstance();

        databaseStorageController.processQuery("CREATE DATABASE MYDB1");
        databaseStorageController.processQuery("CREATE DATABASE MYDB2");

        boolean status = databaseStorageController.getDatabases().stream().allMatch((item) -> item.dbName.contains("MYDB1") || item.dbName.contains("MYDB2"));

        appLogger.logInfo("New Database Result : " + (status ? "Passed" : "Failed"));
    }


    private static void testUseDatabase() {
        DatabaseStorageController databaseStorageController = DatabaseStorageController.getInstance();

        databaseStorageController.processQuery("CREATE DATABASE MYDB");

        databaseStorageController.processQuery("USE MYDB");

        appLogger.logInfo("Use MYDB Database : " + (databaseStorageController.getCurrentDatabase().dbName.equalsIgnoreCase("MYDB") ? "Passed" : "Failed"));
    }


}
