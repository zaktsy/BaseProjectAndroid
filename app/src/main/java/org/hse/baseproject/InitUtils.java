package org.hse.baseproject;

import android.content.Context;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class InitUtils {

    public static List<Group> initGroups() {
        List<Group> groups = new ArrayList<>();

        List<String> names = initGroupNames();
        for (String name : names) {
            groups.add(new Group(name));
        }
        return groups;
    }

    private static List<String> initGroupNames() {
        List<String> names = new ArrayList<>();
        names.add("Пи");
        names.add("Би");

        List<String> years = new ArrayList<>();
        years.add("19");
        years.add("20");
        years.add("21");
        years.add("22");

        List<String> groupNumbers = new ArrayList<>();
        groupNumbers.add("1");
        groupNumbers.add("2");

        List<String> groupNames = new ArrayList<>();

        for (String name : names) {

            for (String year: years) {

                for (String groupNumber: groupNumbers) {
                    String groupName = String.format("%s-%s-%s", name, year, groupNumber);
                    groupNames.add(groupName);

                }
            }
        }

        return groupNames;
    }


    public static String iniTime(){
        Date currentTime = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());

        SimpleDateFormat dayOfWeekFormat = new SimpleDateFormat("EEEE");

        String dayOfWeek = dayOfWeekFormat.format(currentTime);

        dayOfWeek =  dayOfWeek.substring(0, 1).toUpperCase() + dayOfWeek.substring(1);
        String currentTimeStr = dateFormat.format(currentTime);

        currentTimeStr += ", ";
        currentTimeStr += dayOfWeek;

        return currentTimeStr;
    }


    public static Map<String, String> initData(Context context){
        Map<String, String> data = new HashMap<>();

        data.put("have_class", context.getString(R.string.no_classes));
        data.put("subject", context.getString(R.string.subject));
        data.put("classroom", context.getString(R.string.classroom));
        data.put("corp", context.getString(R.string.corp));
        data.put("teacher", context.getString(R.string.teacher));

        return data;
    }
}
