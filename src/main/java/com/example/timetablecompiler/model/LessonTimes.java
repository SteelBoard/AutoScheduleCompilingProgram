package com.example.timetablecompiler.model;

public enum LessonTimes {

    LESSON1("8:00-8:40"),
    LESSON2("8:50-9:30"),
    LESSON3("9:50-10:30"),
    LESSON4("10:50-11:30"),
    LESSON5("11:45-12:25"),
    LESSON6("12:35-13:15"),
    LESSON7("13:35-14:15"),
    LESSON8("14:35-15:15");

    private String time;

    LessonTimes(String time) {

        this.time = time;
    }

    public String getTime() {

        return this.time;
    }

    public static LessonTimes getLessonTimeByNumber(int num) {

        return switch (num) {

            case 1 -> LESSON1;
            case 2 -> LESSON2;
            case 3 -> LESSON3;
            case 4 -> LESSON4;
            case 5 -> LESSON5;
            case 6 -> LESSON6;
            case 7 -> LESSON7;
            case 8 -> LESSON8;
            default -> null;
        };
    }
}
