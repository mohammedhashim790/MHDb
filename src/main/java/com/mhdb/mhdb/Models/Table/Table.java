package com.mhdb.mhdb.Models.Table;

import com.mhdb.mhdb.Bloc.QueryTable.QueryTable;
import com.mhdb.mhdb.Models.Database.Database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Table implements QueryTable {

    private final String tableName;

    private final Database database;
    private List<String> columns = new ArrayList<String>();

    private List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();

    public Table(String tableName, Database database, List<String> columns, List<Map<String, Object>> rows) {
        this.tableName = tableName;
        this.database = database;
        this.columns = columns;
        this.rows = rows;
    }

    public Table(String tableName, Database database, List<String> columns) {
        this.tableName = tableName;
        this.database = database;
        this.columns = columns;
    }


    @Override
    public void describe() {
        if (columns != null) {
            System.out.println("Table: " + tableName + " | Columns: " + columns);
        } else {
            System.out.println("Table '" + tableName + "' does not exist.");
        }
    }

    @Override
    public void select() {
        if (rows != null && !rows.isEmpty()) {
            for (Map<String, Object> row : rows) {
                System.out.println(row);
            }
        } else {
            System.out.println("------ Empty Table ------");
        }
    }


    @Override
    public void insert(List<String> rows) {
        if (columns == null) {
            System.out.println("Table '" + tableName + "' does not exist.");
            return;
        }
        if (columns.size() != rows.size()) {
            System.out.println("Column count mismatch.");
            return;
        }
        Map<String, Object> row = new HashMap<>();
        for (int index = 0; index < rows.size(); index++) {
            row.put(columns.get(index), rows.get(index).trim());
        }
        this.rows.add(row);
    }

    @Override
    public void insert(Map<String, Object> rows) {
        this.rows.add(rows);
    }

    @Override
    public int update(String[] parts) {
        String[] setParts = parts[1].split("WHERE");
        String[] setClauses = setParts[0].trim().split(",");
        Map<String, String> updates = new HashMap<>();
        for (String clause : setClauses) {
            String[] keyValue = clause.split("=");
            updates.put(keyValue[0].trim(), keyValue[1].trim().replace("'", ""));
        }
        String condition = setParts.length > 1 ? setParts[1].trim() : null;
        int updatedRows = 0;
        for (Map<String, Object> row : this.getRows()) {
            if (condition == null || checkIntegrityConstraints(row, condition)) {
                row.putAll(updates);
                updatedRows++;
            }
        }
        return updatedRows;
    }

    private boolean checkIntegrityConstraints(Map<String, Object> row, String condition) {
        String[] parts = condition.split("=");
        if (parts.length != 2) return false;
        String key = parts[0].trim();
        String value = parts[1].trim().replace("'", "");
        return row.containsKey(key) && row.get(key).toString().equals(value);
    }

    @Override
    public int delete(String command) {
        String condition = command.contains("WHERE") ? command.split("WHERE")[1].trim() : null;
        int size = this.getRows().size();
        rows.removeIf(row -> condition == null || checkIntegrityConstraints(row, condition));
        return size - rows.size();
    }

    public String getTableName() {
        return tableName;
    }

    public Database getDatabase() {
        return database;
    }

    public List<String> getColumns() {
        return columns;
    }

    public void setColumns(List<String> columns) {
        this.columns = columns;
    }

    public List<Map<String, Object>> getRows() {
        return rows;
    }

    public void setRows(List<Map<String, Object>> rows) {
        this.rows = rows;
    }
}
