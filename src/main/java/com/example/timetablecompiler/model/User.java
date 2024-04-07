package com.example.timetablecompiler.model;

public class User {

    private String login;
    private boolean admin;

    public User(String login, Boolean admin) {

        this.login = login;
        this.admin = admin;
    }

    public String getLogin() {

        return login;
    }

    public void setLogin(String login) {

        this.login = login;
    }

    public boolean isAdmin() {

        return admin;
    }

    public void setAdmin(boolean admin) {

        this.admin = admin;
    }
}
