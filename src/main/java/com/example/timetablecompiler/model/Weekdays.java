package com.example.timetablecompiler.model;

import java.util.ArrayList;

public enum Weekdays {

    MONDAY("Понедельник",1),
    TUESDAY("Вторник", 2),
    WEDNESDDAY("Среда", 3),
    THURSDAY("Четверг", 4),
    FRIDAY("Пятница", 5),
    SATURDAY("Суббота", 6),
    SUNDAY("Воскресенье", 7);

    private final String name;
    private final Integer number;
    Weekdays(String name, int num) {

        this.name = name;
        this.number = num;
    }

    public String getName() {

        return this.name;
    }

    public Integer getNumber() {

        return this.number;
    }

    public static Weekdays getWeekdayByNumber(int num) {

        return switch (num) {

            case 1 -> MONDAY;
            case 2 -> TUESDAY;
            case 3 -> WEDNESDDAY;
            case 4 -> THURSDAY;
            case 5 -> FRIDAY;
            case 6 -> SATURDAY;
            case 7 -> SUNDAY;
            default -> null;
        };
    }

    public static Weekdays getWeekdayByName(String name) {

        return switch (name) {
            case "Понедельник" -> MONDAY;
            case "Вторник" -> TUESDAY;
            case "Среда" -> WEDNESDDAY;
            case "Четверг" -> THURSDAY;
            case "Пятница" -> FRIDAY;
            case "Суббота" -> SATURDAY;
            case "Воскресенье" -> SUNDAY;
            default -> null;
        };
    }
}
