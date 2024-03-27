package com.example.timetablecompiler.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DbConnectionManager {

    private DbConnectionManager() {}
    public static Connection open() {

        try {
            return DriverManager.getConnection(PropertiesUtil.get("url"));
        } catch (SQLException ex) {

            throw new RuntimeException();
        }
    }


}
