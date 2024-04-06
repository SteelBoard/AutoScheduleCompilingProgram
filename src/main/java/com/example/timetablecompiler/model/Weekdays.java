package com.example.timetablecompiler.model;

public enum Weekdays {

    MONDAY("Понедельник"),
    TUESDAY("Вторник"),
    WEDNESDDAY("Среда"),
    THURSDAY("Четверг"),
    FRIDAY("Пятница"),
    SATURDAY("Суббота"),
    SUNDAY("Воскресенье");

    private final String name;
    Weekdays(String name) {

        this.name = name;
    }

    public String getName() {

        return this.name;
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
}
