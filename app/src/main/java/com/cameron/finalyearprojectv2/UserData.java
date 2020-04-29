package com.cameron.finalyearprojectv2;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;

public final class UserData {
    private UserData() {
    }

    private ArrayList<Goal> goals = new ArrayList<Goal>();
    private ArrayList<TimeTable> timetable = new ArrayList<TimeTable>();

    private static UserData instance;

    public static UserData getInstance() {
        if (instance == null) {
            instance = new UserData();
        }
        return instance;
    }

    //Add a new goal, if it already exists, remove it and add new one, i could use goals.contains() but that wouldnt work if the user was adding a duplicate title with a different date
    public void addGoal(String goalTitle, String subGoal, Boolean goalComplete, Calendar deadline) {
        ArrayList<Goal> temp = new ArrayList<>();
        for (int counter = 0; counter < goals.size(); counter++) {
            if (goals.get(counter).getTitle().equals(goalTitle)) {
                temp.add(goals.get(counter));
            }
        }
        goals.removeAll(temp);
        goals.add(new Goal(goalTitle, subGoal, goalComplete, deadline));
    }

    //remove a goal, i could improve this by passing every bit of data, creating a new object of it then calling goals.remove with the new object
    public void removeGoal(String goal) {
        ArrayList<Goal> temp = new ArrayList<>();
        for (int counter = 0; counter < goals.size(); counter++) {
            if (goals.get(counter).getTitle().equals(goal)) {
                temp.add(goals.get(counter));
            }
        }
        goals.removeAll(temp);
    }

    public void addTimeTableData(String title, String details, Calendar time) {
        ArrayList<TimeTable> temp = new ArrayList<>();
        //see if it contains one with the same title, if it does remove it
        for (int counter = 0; counter < timetable.size(); counter++) {
            if (timetable.get(counter).getDateTime() == time) {
                temp.add(timetable.get(counter));
            }
        }
        //dont remove inside the first loop, would error
        timetable.removeAll(temp);
        timetable.add(new TimeTable(title, details, time));
    }

    public void removeTimeTableData(String title, Calendar time) {
        ArrayList<TimeTable> temp = new ArrayList<>();
        for (int counter = 0; counter < timetable.size(); counter++) {
            if (timetable.get(counter).getTitle().equals(title) && isDateSame(timetable.get(counter).getDateTime(), time)) {

                temp.add(timetable.get(counter));
            }
        }
        //dont remove inside the first loop, would error
        timetable.removeAll(temp);

    }

    //Cant remember if i use this?, return true if the date is the same
    private boolean isDateSame(Calendar c1, Calendar c2) {
        return (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR) &&
                c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH) &&
                c1.get(Calendar.DAY_OF_MONTH) == c2.get(Calendar.DAY_OF_MONTH) &&
                c1.get(Calendar.HOUR_OF_DAY) == c2.get(Calendar.HOUR_OF_DAY) &&
                c1.get(Calendar.MINUTE) == c2.get(Calendar.MINUTE));
    }

    public ArrayList<Goal> getGoals() {
        ArrayList<Goal> temp;
        temp = goals;

        //return a sorted arraylist for goals in order from first to last
        Collections.sort(temp, new Comparator<Goal>() {
            public int compare(Goal o1, Goal o2) {
                return o1.getDateTime().compareTo(o2.getDateTime());
            }
        });
        return temp;
    }

    public ArrayList<TimeTable> getTimeTable() {
        ArrayList<TimeTable> temp;
        temp = timetable;
        //return a sorted arraylist for timetable events in order from first to last
        Collections.sort(temp, new Comparator<TimeTable>() {
            public int compare(TimeTable o1, TimeTable o2) {
                return o1.getDateTime().compareTo(o2.getDateTime());
            }
        });
        return temp;
    }
}
