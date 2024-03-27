package com.example.timetablecompiler.model;

import com.example.timetablecompiler.util.DbConnectionManager;
import javafx.util.Pair;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.TreeMap;

public class DbSubjectsData {

    public static HashMap<String, String> getSubjectWithTeachers() {

        try (var connection = DbConnectionManager.open();
             PreparedStatement statement = connection.prepareStatement("""
                     SELECT t2.subject, t1.name
                     FROM teachers t1, teacher_subject t2
                     WHERE t1.id = t2.teacher_id
                     GROUP BY t2.subject, t1.name
                     """)) {

            ResultSet resultSet = statement.executeQuery();
            HashMap<String, String> subjectTeacherArray = new HashMap<>();

            while (resultSet.next()) {

                subjectTeacherArray.put(resultSet.getString("subject"), resultSet.getString("name"));
            }

            return subjectTeacherArray;
        }
        catch (SQLException ex) {

            throw new RuntimeException();
        }
    }

    public static HashMap<String, Integer> getQuantityOfSubjects() {

        try (var connection = DbConnectionManager.open();
             PreparedStatement statement = connection.prepareStatement("SELECT name, quantity FROM subjects")) {

            ResultSet resultSet = statement.executeQuery();
            HashMap<String, Integer> quantityTable = new HashMap<>();

            while (resultSet.next()) {

                quantityTable.put(resultSet.getString("name"), resultSet.getInt("quantity"));
            }

            return quantityTable;
        }
        catch (SQLException ex) {

           throw new RuntimeException();
        }
    }
}