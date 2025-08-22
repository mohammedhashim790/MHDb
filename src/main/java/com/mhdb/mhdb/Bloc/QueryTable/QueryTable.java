package com.mhdb.mhdb.Bloc.QueryTable;

import java.util.List;
import java.util.Map;

public interface QueryTable {

    /**
     * Describes the structure of the table, such as column names and types.
     */
    void describe();

    /**
     * Selects data from the table. The specific selection criteria should be defined in the implementation.
     */
    void select();

    /**
     * Inserts rows into the table.
     *
     * @param rows A list of rows to insert into the table. Each row is represented as a string.
     */
    void insert(List<String> rows);

    /**
     * Inserts rows into the table using a map for key-value pair representation of columns and their values.
     *
     * @param rows A map representing the rows to insert, where the keys are column names and the values are the corresponding values to insert.
     */
    void insert(Map<String, Object> rows);

    /**
     * Updates data in the table.
     *
     * @param parts An array of strings representing the parts of the update command (e.g., conditions and values).
     * @return The number of rows affected by the update operation.
     */
    int update(String[] parts);

    /**
     * Deletes data from the table.
     *
     * @param command The delete command, typically including conditions for deletion.
     * @return The number of rows affected by the delete operation.
     */
    int delete(String command);
}

