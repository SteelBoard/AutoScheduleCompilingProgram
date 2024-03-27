package com.example.timetablecompiler.model;

import com.example.timetablecompiler.util.DbConnectionManager;
import org.mindrot.jbcrypt.BCrypt;
import java.sql.*;

public class DbRegistrationLoginModel {

    public static boolean isPasswordCorrect(String login, String password) {

        try (var connection = DbConnectionManager.open();
             PreparedStatement statement = connection.prepareStatement("SELECT hashPassword, salt FROM users WHERE login = ?")) {

            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {

                return BCrypt.checkpw(password, resultSet.getString("hashPassword"));
            }
            else {

                return false;
            }
        }
        catch (SQLException ex) {

            throw new RuntimeException();
        }
    }

    public static boolean isUserExist(String login) {

        try (var connection = DbConnectionManager.open();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE login = ?")) {

            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();

            return resultSet.next();
        }
        catch (SQLException ex) {

            throw new RuntimeException();
        }
    }

    public static void registryUser(String login, String password) {

        try (var connection = DbConnectionManager.open();
             PreparedStatement statement = connection.prepareStatement("INSERT INTO users (login, hashPassword, salt) VALUES (?, ?, ?)")) {

            String salt = BCrypt.gensalt();
            statement.setString(1, login);
            statement.setString(2, BCrypt.hashpw(password, salt));
            statement.setString(3, salt);

            statement.executeUpdate();
        }
        catch (SQLException ex) {

            throw new RuntimeException();
        }
    }
}
