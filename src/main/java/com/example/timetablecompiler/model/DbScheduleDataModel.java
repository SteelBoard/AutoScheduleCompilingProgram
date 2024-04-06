package com.example.timetablecompiler.model;

import com.example.timetablecompiler.util.DbConnectionManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DbScheduleDataModel {

    public static void updateSchedule(Schedule schedule, Classes grade) {

        deleteSchedule(grade);
        insertSchedule(schedule, grade);
    }

    public static void insertSchedule(Schedule schedule, Classes grade) {

        try (var connection = DbConnectionManager.open();
             PreparedStatement statement = connection.prepareStatement("""
                     INSERT INTO schedule (grade, weekday, lessonnumber, subject)
                     VALUES (?, ?, ?, ?)""")) {

            for (int i = 0; i < schedule.getLessonArray().length; i++) {

                for (int j = 0; j < schedule.getLessonArray()[i].length; j++) {

                    if (schedule.getLessonArray()[i][j] != null) {

                        statement.setString(1, grade.getGrade());
                        statement.setString(2, Weekdays.getWeekdayByNumber(i).getName());
                        statement.setInt(3, j+1);
                        statement.setString(4, schedule.getLessonArray()[i][j].getSubject());
                        statement.addBatch();
                    }
                }
            }

            statement.executeBatch();
        }
        catch (SQLException ex) {

            throw new RuntimeException();
        }
    }

    public static void deleteSchedule(Classes grade) {

        try (var connection = DbConnectionManager.open();
        PreparedStatement statement = connection.prepareStatement("DELETE FROM schedule WHERE grade = ?")) {

            statement.setString(1, grade.getGrade());
            statement.executeUpdate();
        }
        catch (SQLException ex) {

            throw new RuntimeException();
        }
    }
}
