package com.example.timetablecompiler.util;

import com.example.timetablecompiler.TimeTableCompilerUltimate;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class PropertiesUtil {

    private static final Properties PROPERTIES = new Properties();
    private PropertiesUtil(){}

    static {

        loadProperties();
    }

    private static void loadProperties() {

       try (InputStream propertiesStream = new FileInputStream("Properties/database.properties")) {

           PROPERTIES.load(propertiesStream);
       } catch (IOException ex) {

           throw new RuntimeException();
       }
    }

    public static String get(String key) { return PROPERTIES.getProperty(key); }
}
