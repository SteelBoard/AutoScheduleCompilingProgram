package com.example.timetablecompiler.model;

import java.util.ArrayList;
import java.util.Arrays;

public enum LessonTimes {

    LESSON1("8:00-8:40", 1),
    LESSON2("8:50-9:30", 2),
    LESSON3("9:50-10:30", 3),
    LESSON4("10:50-11:30", 4),
    LESSON5("11:45-12:25", 5),
    LESSON6("12:35-13:15", 6),
    LESSON7("13:35-14:15", 7),
    LESSON8("14:35-15:15", 8);

    private String time;
    private Integer number;

    LessonTimes(String time, int number) {

        this.time = time;
        this.number = number;
    }

    public String getTime() {

        return this.time;
    }

    public Integer getNumber() {

        return this.number;
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

    public static ArrayList<LessonTimes> getAllLessonTimesNumber() {

        return new ArrayList<>(Arrays.asList(LESSON1, LESSON2, LESSON3, LESSON4, LESSON5, LESSON6, LESSON7, LESSON8));
    }
}
