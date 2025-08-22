package com.mhdb.mhdb.Models.Database;

import com.mhdb.mhdb.Bloc.Loggers.AppLogger;
import com.mhdb.mhdb.Bloc.TransactionHandler.TransactionHandler;
import com.mhdb.mhdb.Bloc.TransactionHandler.TransactionParams;
import com.mhdb.mhdb.Models.Table.Table;

import java.util.*;

public class Database {


    private final AppLogger logger = AppLogger.getAppLogger();
    public String dbId = String.valueOf(new Random().nextInt());
    public String dbName;

    private final List<Table> tableList = new ArrayList<>();

    public String getDbId() {
        return dbId;
    }

    public void setDbId(String dbId) {
        this.dbId = dbId;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public List<Table> getTableList() {
        return tableList;
    }

    public TransactionHandler getTransactionHandler() {
        return transactionHandler;
    }

    public void setTransactionHandler(TransactionHandler transactionHandler) {
        this.transactionHandler = transactionHandler;
    }

    private TransactionHandler transactionHandler;


    /**
     * Constructs a {@code Database} object with the specified database name.
     * <p>
     * This constructor creates a new object of the {@code Database} instance with the name provided.
     *
     * @param dbName The name of the database to be created.
     */
    public Database(String dbName) {
        this.dbName = dbName;
    }


    /**
     * Processes a query command and executes actions based on the command.
     *
     * @param command
     * @param parts
     */
    public void processQuery(String command, String[] parts) {
        switch (command.toUpperCase()) {
            case "SHOW":
                this.showTables();
                break;
            case "CREATE":
                if (transactionHandler != null) {
                    this.transactionHandler.begin(command, parts);
                    break;
                }
                parts = parts[1].split("\\(", 2);
                String tableName = parts[0].split(" ")[1].trim();
                String[] columns = parts[1].replace(")", "").split(",");
                List<String> columnList = new ArrayList<>();
                for (String col : columns) {
                    columnList.add(col.trim().split(" ")[0].trim());
                }
                this.createTable(tableName, columnList);
                break;
            case "DESCRIBE":
                this.describeTable(parts[1]);
                break;
            case "SELECT":
                this.selectTable(parts[1]);
                break;
            case "INSERT":
                if (this.transactionHandler == null || !this.transactionHandler.isLocked())
                    this.insertInTable(parts[1]);
                else this.transactionHandler.begin(command, parts);
                break;
            case "UPDATE":
                if (this.transactionHandler == null || !this.transactionHandler.isLocked()) updateTable(parts[1]);
                else this.transactionHandler.begin(command, parts);
                break;
            case "DELETE":
                if (this.transactionHandler == null || !this.transactionHandler.isLocked()) deleteRecord(parts[1]);
                else this.transactionHandler.begin(command, parts);
                break;
            case "BEGIN":
                beginTransaction(command, parts);
                break;
            case "COMMIT":
                commitTransaction();
                break;
            case "ROLLBACK":
                rollbackTransaction();
                break;
            default:
                this.logger.logError("Invalid query: " + command);
        }
    }

    /**
     * Rollsback the current transaction and processes the associated query.
     */
    private void rollbackTransaction() {
        if (transactionHandler == null) return;

        this.transactionHandler.rollback();
        this.transactionHandler = null;

        this.logger.logInfo("Transaction : " + transactionHandler.getId() + " rolled back.");
    }

    /**
     * Commits the current transaction and processes the associated query.
     */
    private void commitTransaction() {
        if (transactionHandler == null) return;
        TransactionParams transactionParams = this.transactionHandler.commit();
        this.transactionHandler = null;
        this.processQuery(transactionParams.getCommand(), transactionParams.getParts());
        this.logger.logInfo("Transaction : " + transactionParams.getTransactionID() + " committed.");
    }

    /**
     * Begins a new transaction by creating a new {@code TransactionHandler} if no active transaction exists.
     *
     * @param command
     * @param parts
     */
    private void beginTransaction(String command, String[] parts) {
        if (transactionHandler != null) {
            this.logger.logError("A transaction with id  " + transactionHandler.getId() + " already exists. Rolling back.");
            this.rollbackTransaction();
            return;
        }

        this.transactionHandler = new TransactionHandler();
        this.logger.logInfo("Transaction : " + transactionHandler.getId() + " started.");
    }

    /**
     * Inserts data in the selected table.
     *
     * @param command
     */
    private void insertInTable(String command) {
        command = command.toUpperCase();
        String[] parts = command.toUpperCase().split("VALUES");
        String tableName = parts[0].replace("INTO ", "").split(" ")[0].trim();
        Table table = this.tableList.stream().filter(t -> t.getTableName().equalsIgnoreCase(tableName)).findFirst().orElse(null);
        String[] values = parts[1].replace("(", "").replace(")", "").split(",");

        List<String> columns = table.getColumns();
        if (columns == null) {
            System.out.println("Table '" + tableName + "' does not exist.");
            return;
        }
        if (columns.size() != values.length) {
            logger.logError("Column count mismatch.");
            return;
        }
        Map<String, Object> row = new HashMap<>();
        for (int i = 0; i < columns.size(); i++) {
            row.put(columns.get(i), values[i].trim());
        }
        table.insert(row);
    }

    /**
     * Update / Alters the data in a table.
     *
     * @param command
     */
    private void updateTable(String command) {
        command = command.toUpperCase();
        String[] parts = command.split("SET");
        String tableName = parts[0].replace("UPDATE", "").trim();

        Table table = this.tableList.stream().filter(t -> t.getTableName().equalsIgnoreCase(tableName)).findFirst().orElse(null);

        if (table == null) {
            System.out.println("Table '" + tableName + "' does not exist.");
            return;
        }

        int updatedRows = table.update(parts);

        this.logger.logInfo("Table " + tableName + " has been updated, with " + updatedRows + " rows.");
    }

    /**
     * Deletes a record based on the query from a table.
     *
     * @param command
     */
    private void deleteRecord(String command) {
        String[] parts = command.split("FROM");
        if (parts.length < 2) {
            System.out.println("Invalid DELETE query.");
            return;
        }

        String tableName = parts[1].trim().split("WHERE")[0].trim();

        Table table = this.tableList.stream().filter(t -> t.getTableName().equalsIgnoreCase(tableName)).findFirst().orElse(null);
        if (table == null) {
            this.logger.logError("Table '" + tableName + "' does not exist.");
            return;
        }

        int deletedRows = table.delete(command);
        this.logger.logInfo("Deleted " + deletedRows + " rows from table '" + tableName + "'.");


    }

    /**
     * Create a table with the tablename and columns specified in the argument.
     *
     * @param tableName
     * @param columns
     */
    public void createTable(String tableName, List<String> columns) {
        this.tableList.add(new Table(tableName, this, columns));
    }

    /**
     * Show Table Names in the selected database.
     */
    public void showTables() {
        System.out.println("Tables: \n" + (tableList.isEmpty() ? "None" : String.join(", ", tableList.stream().map(Table::getTableName).toList())));
    }

    /**
     * Describe the atrributes of the table, specified in the argument.
     *
     * @param tableName
     */
    public void describeTable(String tableName) {
        Table table = this.tableList.stream().filter(t -> t.getTableName().equals(tableName)).findFirst().orElse(null);
        if (table != null) {
            table.describe();
        } else {
            logger.logError("Table not found: " + tableName);
        }
    }


    @Override
    public String toString() {
        return "Database{" + "dbId='" + dbId + '\'' + ", dbName='" + dbName + '\'' + '}';
    }

    /**
     * Selects (Displays) all records from a table based on the provided command string.
     *
     * @param command The SQL-like command string used to select a table. It should include the "FROM" keyword followed by the table name.
     */
    public void selectTable(String command) {
        String tableName = command.toUpperCase().split("FROM")[1].trim();
        Table table = this.tableList.stream().filter(t -> t.getTableName().equalsIgnoreCase(tableName)).findFirst().orElse(null);
        if (table != null) {
            table.select();
        } else {
            logger.logError("Table not found: " + tableName);
        }
    }

}
