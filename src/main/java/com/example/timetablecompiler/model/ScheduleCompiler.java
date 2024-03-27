package com.example.timetablecompiler.model;

import java.util.*;

public class ScheduleCompiler {

    public static ArrayList<ArrayList<Lesson>> compileInitialSchedule() {

        ArrayList<ArrayList<Lesson>> schedule = new ArrayList<>();
        for (int i = 0; i < 5; i++) {

            schedule.add(new ArrayList<Lesson>());
        }
        HashMap<String, Integer> quantity = DbSubjectsData.getQuantityOfSubjects();
        HashMap<String, String> subjects_teachers = DbSubjectsData.getSubjectWithTeachers();

        int i = 0;
        for (Map.Entry<String, Integer> entry : quantity.entrySet()) {

            while (entry.getValue() != 0) {

                schedule.get(i).add(new Lesson(entry.getKey(), subjects_teachers.get(entry.getKey())));
                entry.setValue(entry.getValue()-1);

                if (i == schedule.size() - 1) {

                    i = 0;
                }
                else {

                    i++;
                }
            }
        }

        return schedule;
    }


}
