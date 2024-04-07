package com.example.timetablecompiler.model;

import com.example.timetablecompiler.util.DbConnectionManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class DbScheduleDataModel {

    public static Schedule getSchedule(Classes grade) {

        Schedule schedule = new Schedule();
        HashMap<String, String> subject_teachers = DbSubjectsDataModel.getSubjectWithTeachers();

        try (var connection = DbConnectionManager.open();
             PreparedStatement statement = connection.prepareStatement("SELECT FROM schedule WHERE grade = ?")) {

            statement.setString(1, grade.getGrade());
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                schedule.getLessonArray()
                        [Weekdays.getWeekdayByName(resultSet.getString("weekday")).getNumber()-1]
                        [resultSet.getInt("lessonnumber")-1] =
                        new Lesson(resultSet.getString("subject"), subject_teachers.get(resultSet.getString("subject")));
            }

            return schedule;
        }
        catch (SQLException ex) {

            throw new RuntimeException();
        }
    }

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
