package com.boluo.core.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author chao
 * @datetime 2025-04-03 21:19
 * @description
 */
public class ReadFile {

    public static void main(String[] args) throws IOException {
        File f = new File("/databricks/spark/dbconf/log4j/driver/log4j2.xml");

        System.out.println(".......");
        System.out.println(f.getPath());
        System.out.println(f.getAbsolutePath());
        System.out.println(f.getCanonicalPath());
        BufferedReader reader = new BufferedReader(new FileReader(f));
        String temp = null;
        int line = 1;
        while ((temp = reader.readLine()) != null) {
            System.out.println("line" + line + ":" + temp);
            line++;
        }

    }
}
