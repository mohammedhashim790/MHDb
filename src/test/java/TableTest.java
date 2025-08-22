import com.mhdb.mhdb.Bloc.Loggers.AppLogger;
import com.mhdb.mhdb.Controllers.DatabaseStorageController.DatabaseStorageController;
import com.mhdb.mhdb.Models.Table.Table;

import java.util.Map;

public class TableTest {


    private static final AppLogger appLogger = AppLogger.getAppLogger();

    public static void main(String[] args) {
        testTableCreation();
        testTableInsertion();
    }

    private static void testTableCreation() {
        DatabaseStorageController databaseStorageController = DatabaseStorageController.getInstance();
        databaseStorageController.processQuery("CREATE DATABASE mydb");
        databaseStorageController.processQuery("USE mydb");
        databaseStorageController.processQuery("CREATE TABLE user (id INT PRIMARY KEY, name VARCHAR(255) NOT NULL)");
        appLogger.logInfo("Table " + databaseStorageController.getCurrentDatabase().getTableList().stream().allMatch(t -> t.getTableName().equals("user")));
    }

    private static void testTableInsertion() {
        DatabaseStorageController databaseStorageController = DatabaseStorageController.getInstance();
        databaseStorageController.processQuery("CREATE DATABASE mydb");
        databaseStorageController.processQuery("USE mydb");
        databaseStorageController.processQuery("CREATE TABLE user1 (id INT PRIMARY KEY, name VARCHAR(255) NOT NULL)");
        databaseStorageController.processQuery("INSERT INTO user1 (id, name) VALUES (1, 'John')");
        boolean contains = false;
        for (Table table : databaseStorageController.getCurrentDatabase().getTableList()) {
            for (Map<String, Object> row : table.getRows()) {
                contains = row.containsValue("'JOHN'");
                if (contains) {
                    break;
                }
            }
        }
        appLogger.logInfo("Table Insertion  " + contains);
    }
}
