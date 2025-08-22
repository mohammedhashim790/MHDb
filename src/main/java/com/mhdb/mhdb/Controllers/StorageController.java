package com.mhdb.mhdb.Controllers;

import com.mhdb.mhdb.Bloc.DataProcessor.DataProcessor;
import com.mhdb.mhdb.Bloc.Loggers.AppLogger;

import java.io.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class StorageController<T> {
    protected final AppLogger logger = AppLogger.getAppLogger();
    private File fileController;
    private OutputStreamWriter fileOutputStream;
    private BufferedReader fileInputStream;

    protected abstract Class<T> getClassType();

    public StorageController() {
        this.build();
    }

    protected String tableName;


    public String getTableName() {
        String tableName = this.tableName;
        if (tableName == null) {
            tableName = this.getClass().getSimpleName();
        }
        tableName = tableName.replaceAll("([a-z])([A-Z])", "$1_$2").toLowerCase();
        this.tableName = tableName;
        return tableName;
    }

    private void build() {
        try {
            String controllerPath = String.format("%s/%s/%s.db", System.getProperty("user.dir"), "sys", getTableName());
            fileController = new File(controllerPath);
            if (!fileController.exists()) {
                fileController.getParentFile().mkdirs();
                fileController.createNewFile();
            }
            this.fileController.setWritable(true);
            this.fileOutputStream = new OutputStreamWriter(new FileOutputStream(fileController, true));
            this.fileInputStream = new BufferedReader(new FileReader(fileController));
            this.addColumns();
        } catch (IOException e) {
            this.logger.logError("Could not create the controller", e.getMessage());
        }
    }

    private void addColumns() throws IOException {
        long lines = this.fileInputStream.lines().count();
        this.fileInputStream = new BufferedReader(new FileReader(fileController));
        if (lines == 0) {
            this.logger.logError("No lines found. Adding columns to ", this.getClassType().getSimpleName());
            List<String> fieldNames = new ArrayList<>();
            for (Field field : getClassType().getFields()) {
                fieldNames.add(field.getName());
            }
            this.insert(fieldNames);
        }
    }

    public void insert(Object... args) throws IOException {
        if (args.length == 0) return;
        String entry = Arrays.stream(args).map(Object::toString).collect(Collectors.joining(","));
        this.fileOutputStream.append(entry);
        this.fileOutputStream.append(",\n");
        this.fileOutputStream.flush();
    }


    public void clear() {
        try {
            this.fileOutputStream = new OutputStreamWriter(new FileOutputStream(fileController));
            this.fileOutputStream.write("");
            this.addColumns();
            this.fileOutputStream.close();
            this.fileOutputStream = new OutputStreamWriter(new FileOutputStream(fileController, true));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void insert(List<String> args) throws IOException {
        if (args.isEmpty()) return;
        String entry = args.stream().map(Object::toString).collect(Collectors.joining(","));
        this.fileOutputStream.append(entry);
        this.fileOutputStream.append(",\n");
        this.fileOutputStream.flush();
    }


    public void insert(T object) throws IOException {
        Map<String, Object> items = DataProcessor.getFieldValues(object);
        if (items.isEmpty()) return;
        String entry = items.values().stream().map(Object::toString).collect(Collectors.joining(","));
        this.fileOutputStream.append(entry);
        this.fileOutputStream.append(",\n");
        this.fileOutputStream.flush();
    }

    public List<T> listAll() throws IOException {
        String line = "";
        List<T> items = DataProcessor.parseCSVData(this.fileInputStream, getClassType());
        this.fileInputStream = new BufferedReader(new FileReader(fileController));
        return items;
    }
}
