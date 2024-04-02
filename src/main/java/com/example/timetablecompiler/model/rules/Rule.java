package com.example.timetablecompiler.model.rules;

import com.example.timetablecompiler.model.Schedule;

public abstract class Rule {

    public abstract boolean check(Schedule schedule);
}
