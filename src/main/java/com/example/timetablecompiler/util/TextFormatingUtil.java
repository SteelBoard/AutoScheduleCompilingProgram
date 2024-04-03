package com.example.timetablecompiler.util;

public final class TextFormatingUtil {

    public static String formatTeacherName(String initialName) {

        String name = initialName;
        for (int i = name.length()-1, counter = 0; i > 0; i--) {

            if (name.charAt(i) >= 'А' && name.charAt(i) <= 'Я') {

                char[] chars = name.toCharArray();
                chars[i+1] = '.';
                name = String.valueOf(chars);
                counter++;
                if (counter == 2) {

                    break;
                }

            }
        }
        name = name.substring(0, name.lastIndexOf('.')+1);
        name = name.replace(name.substring(name.indexOf('.')+1, name.lastIndexOf(' ')), "");

        return name;
    }
}
