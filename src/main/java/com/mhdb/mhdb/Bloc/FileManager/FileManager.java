package com.mhdb.mhdb.Bloc.FileManager;

import java.io.File;

public class FileManager {

    private String fileName;

    private File file;

    FileManager(String fileName) {
        this.fileName = fileName;
        this.init();
    }


    private void init() {
        this.file = new File(fileName);
        if(!this.file.exists()) {

        }

    }

}
