package com.mhdb.mhdb.Controllers.DatabaseStorageController;


import com.mhdb.mhdb.Bloc.DBScanner;
import com.mhdb.mhdb.Bloc.Exceptions.InvalidPassword;
import com.mhdb.mhdb.Bloc.Exceptions.UserNotFoundException;
import com.mhdb.mhdb.Bloc.Loggers.AppLogger;
import com.mhdb.mhdb.Controllers.AuthenticationStorageController.AuthenticationStorageController;
import com.mhdb.mhdb.Models.Authentication.Authentication;
import com.mhdb.mhdb.Models.Database.Database;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * The type Database query.
 */
public class DatabaseStorageController {

    private final AppLogger logger = AppLogger.getAppLogger();
    private final AuthenticationStorageController authenticationController = new AuthenticationStorageController();

    private static DatabaseStorageController instance;

    private final DBScanner dbScanner = new DBScanner();

    private final List<Database> databases = new ArrayList<>();

    private Database currentDatabase;

    public List<Database> getDatabases() {
        return databases;
    }

    private boolean loggedIn = false;

    public Database getCurrentDatabase() {
        return currentDatabase;
    }

    public void setCurrentDatabase(Database currentDatabase) {
        this.currentDatabase = currentDatabase;
    }

    private DatabaseStorageController() {
    }

    public static DatabaseStorageController getInstance() {
        if (instance == null) instance = new DatabaseStorageController();
        return instance;
    }

    /**
     * Prepares the system by authenticating the user and starting the database console.
     * <p>
     * This method first attempts to authenticate the user by calling the {@link #authenticate()} method.
     * Upon successful authentication, it proceeds to start the database console via the {@link #startDBConsole()} method.
     * If an {@link IOException} occurs during either of these processes, it is caught and rethrown as a {@link RuntimeException}.
     *
     * @throws RuntimeException if an {@link IOException} occurs during authentication or starting the database console.
     * @see #authenticate()
     * @see #startDBConsole()
     */
    public void ready() {
        try {
            this.authenticate();
            this.setLoggedIn(true);
            this.startDBConsole();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Starts the database console interface that continuously listens for user input.
     * It waits for SQL-like queries to be entered by the user. The input is processed
     * line by line until the user enters "exit;" to terminate the console session.
     * <p>
     * The method reads user input using a scanner and processes each query using
     * the {@link #processQuery(String)} method. The loop terminates when the user
     * enters the string "exit;" (with a semicolon).
     * <p>
     * Note: This method assumes that the {@code dbScanner} has been properly initialized
     * and is ready to read from the standard input.
     *
     * @see #processQuery(String)
     */
    private void startDBConsole() {
        String query;
        while (!(query = this.dbScanner.nextLine()).equals("exit;")) {
            try {
                this.processQuery(query);
            } catch (Exception _) {
                this.logger.logError("Invalid query:" + query);
            }
        }
    }

    /**
     * Processes a query string by parsing the command and execute the Statements.
     * @param query the query string to be processed. The query should contain a command (e.g., "USE") followed by the relevant arguments.
     *
     * @throws IllegalArgumentException if the query format is incorrect or if no valid command is found.
     */
    public void processQuery(String query) {
        String[] parts = query.toUpperCase().trim().split("\\s+", 2);
        String command = parts[0].toUpperCase();

        if (command.equalsIgnoreCase("use")) {
            useDatabase(parts[1]);
            return;
        }
        if (command.equalsIgnoreCase("show") && parts[1].toLowerCase().contains("database")) {
            this.showDatabases();
        }
        if (command.equalsIgnoreCase("create") && parts[1].toLowerCase().contains("database")) {
            String databaseName = parts[1].split(" ")[1];
            this.databases.add(new Database(databaseName));
            return;
        }

        if (currentDatabase != null) {
            currentDatabase.processQuery(command, parts);
        }
    }

    /**
     * Sets the current database to the database specified, else it logs the error.
     * @param dbName
     */
    private void useDatabase(String dbName) {
        Database database = this.databases.stream().filter((db) -> db.dbName.equalsIgnoreCase(dbName)).findFirst().orElse(null);
        if (database != null) {
            this.currentDatabase = database;
            logger.logInfo("Using " + dbName + " database");
        } else {
            logger.logError("Database not found.");
        }
    }

    /**
     * Authenticates the database using the credentials.
     * Requires
     * 1. Username.
     * 2. Password
     *
     * if the user doesn't exists, it will prompt to create an account using the credentials shared.
     * On successful authentication, the user has to verify the captcha for successful authentication
     * @throws IOException
     */
    private void authenticate() throws IOException {
        String username = "";
        String password = "";
        int attempts = 0;
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                if (attempts > 1) {
                    logger.logError("You have reached the maximum number of attempts.");
                    authenticationController.requestResetPassword();
                    System.out.print("Please re-login");
                    attempts = 0;
                }
                System.out.println("Please enter your username: ");
                username = scanner.nextLine();
                System.out.println("Please enter your password: ");
                password = scanner.nextLine();
                authenticationController.authenticate(new Authentication(username, password));
                authenticationController.mfa();
                break;
            } catch (InvalidPassword _) {
                System.out.println("Invalid password. Please try again.");
                attempts++;
            } catch (UserNotFoundException _) {
                logger.logError("User does not exists. Do you want to create an account?[y/N]");
                boolean yes = System.console().readLine().equals("y");
                if (yes) {
                    authenticationController.create(new Authentication(username, password));
                    break;
                }
            }
        }
    }

    /**
     *  Displays the list of available databases. If there are no databases, a message indicating that no databases are available is shown.
     */
    public void showDatabases() {
        System.out.println("Databases: " + (databases.isEmpty() ? "None" : String.join("\n", databases.stream().map((item) -> item.dbName).toList())));
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }
}
