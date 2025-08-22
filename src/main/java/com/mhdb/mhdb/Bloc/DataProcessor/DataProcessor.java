package com.mhdb.mhdb.Bloc.DataProcessor;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;

public class DataProcessor {

    /**
     * Parses CSV data from a {@code BufferedReader} and converts it into a list of objects of the specified class type.
     *
     * <p><b>Usage:</b> This method is used to read db files (CSV) when the database starts,
     * where initial data is loaded into memory.</p>
     *
     * @param <T>       The type of objects to be created from the CSV data.
     * @param br        The {@code BufferedReader} containing the CSV data.
     * @param classType The {@code Class} type to which each row of CSV data should be mapped.
     * @return A {@code List} of objects of type {@code T} populated from the CSV data.
     * @throws IOException If an I/O error occurs while reading the CSV data.
     */
    public static <T> List<T> parseCSVData(BufferedReader br, Class<T> classType) throws IOException {
        List<T> resultList = new ArrayList<>();
        try {
            String headerLine = br.readLine();
            if (headerLine == null) return resultList; // Empty file

            String[] headers = headerLine.split(",");
            List<Field> fields = getClassFields(classType, headers);

            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                T obj = createObjectFromCsv(classType, fields, values);
                resultList.add(obj);
            }
        } catch (ReflectiveOperationException | IOException e) {
            e.printStackTrace();
        }
        return resultList;
    }

    private static <T> List<Field> getClassFields(Class<T> clazz, String[] headers) {
        List<Field> fields = new ArrayList<>();
        for (String header : headers) {
            try {
                Field field = clazz.getField(header.trim());
                field.setAccessible(true);
                fields.add(field);
            } catch (NoSuchFieldException e) {
                fields.add(null); // Skip if field is not present in class
            }
        }
        return fields;
    }

    private static <T> T createObjectFromCsv(Class<T> clazz, List<Field> fields, String[] values) throws ReflectiveOperationException {
        T obj = clazz.getDeclaredConstructor().newInstance();
        for (int i = 0; i < values.length && i < fields.size(); i++) {
            Field field = fields.get(i);
            if (field != null) {
                Object convertedValue = convertToFieldType(field, values[i].trim());
                field.set(obj, convertedValue);
            }
        }
        return obj;
    }

    private static Object convertToFieldType(Field field, String value) {
        Class<?> type = field.getType();
        if (type == int.class || type == Integer.class) return Integer.parseInt(value);
        if (type == double.class || type == Double.class) return Double.parseDouble(value);
        if (type == boolean.class || type == Boolean.class) return Boolean.parseBoolean(value);
        return value; // Default to String
    }

    /**
     * Retrieves the field names and their corresponding values from an object.
     * <p>
     * This method uses reflection to access all public fields of the given object's class
     * and stores them in a {@code LinkedHashMap}, preserving the order in which they are declared.
     *
     * @param obj The object from which field values are to be extracted.
     * @return A {@code Map} where the keys are field names and the values are the corresponding field values.
     */
    public static Map<String, Object> getFieldValues(Object obj) {
        Map<String, Object> fieldMap = new LinkedHashMap<>();
        Class<?> clazz = obj.getClass();
        for (Field field : clazz.getFields()) {
            field.setAccessible(true);
            try {
                fieldMap.put(field.getName(), field.get(obj));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return fieldMap;
    }
}
