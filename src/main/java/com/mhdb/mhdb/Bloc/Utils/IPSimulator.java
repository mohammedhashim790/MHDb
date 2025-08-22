package com.mhdb.mhdb.Bloc.Utils;

import java.util.Random;

public class IPSimulator {

    public static String RandomIPSimulator() {
        Random rand = new Random(255);
        return String.format("%s.%s.%s.%s",rand.nextInt(255),rand.nextInt(255),rand.nextInt(255),rand.nextInt(255));
    }
}
