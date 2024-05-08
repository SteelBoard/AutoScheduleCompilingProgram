package com.example.timetablecompiler.model.rules;

import com.example.timetablecompiler.model.Schedule;

import java.util.ArrayList;

public class SequenceRule extends Rule{

    private ArrayList<String> sequence;

    public SequenceRule(ArrayList<String> sequence) {

        this.sequence = sequence;
    }

    @Override
    public boolean check(Schedule schedule) {

        int counter = 0;
        for (int i = 0, k = 0; i < schedule.getLessonArray().length; i++, k++) {

            for (int j = 0; j < schedule.getLessonArray()[i].length; j++) {

                if (schedule.getLessonArray()[i][j] != null && schedule.getLessonArray()[i][j].getSubject().equals(sequence.get(counter))) {

                    while ( j+counter != 8 && schedule.getLessonArray()[i][j+counter] != null) {

                        if (!schedule.getLessonArray()[i][j + counter].getSubject().equals(sequence.get(counter))) {

                            break;
                        }
                        else if (counter == sequence.size()-1) {

                            return false;
                        }
                        counter++;
                    }
                }
            }
        }
        return true;
    }
}
