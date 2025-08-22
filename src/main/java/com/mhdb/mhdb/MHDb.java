package com.mhdb.mhdb;

import com.mhdb.mhdb.Controllers.DatabaseStorageController.DatabaseStorageController;

import java.io.IOException;


public class MHDb {

    public static void main(String[] args) throws IOException {
        DatabaseStorageController databaseQuery = DatabaseStorageController.getInstance();
        databaseQuery.ready();
    }


}
