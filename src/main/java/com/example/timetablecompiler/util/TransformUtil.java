package com.example.timetablecompiler.util;

import com.example.timetablecompiler.model.DbSubjectsDataModel;
import com.example.timetablecompiler.model.Lesson;
import com.example.timetablecompiler.model.Schedule;
import com.example.timetablecompiler.model.Weekdays;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public final class TransformUtil {

    public static ArrayList<String> HashMapOfSubjectToArrayList(HashMap<String, Integer> subjects) {

        ArrayList<String> subjectsList = new ArrayList<>();

        for (Map.Entry<String, Integer> entry : subjects.entrySet()) {

            for (int i = 0; i < entry.getValue(); i++) {

                subjectsList.add(entry.getKey());
            }
        }

        return subjectsList;
    }
}
