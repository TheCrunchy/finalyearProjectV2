package com.cameron.finalyearprojectv2;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;

public final class UserData {
    private  UserData(){
    }
    private ArrayList<Goal> goals = new ArrayList<Goal>();
    private ArrayList<TimeTable> timetable = new ArrayList<TimeTable>();

    private static UserData instance;
    public static UserData getInstance(){
        if (instance == null){
            instance = new UserData();
        }
        return instance;
    }

    public void addGoal(String goalTitle, String subGoal, Boolean goalComplete, Calendar deadline){
        ArrayList<Goal> temp = new ArrayList<>();
        for (int counter = 0; counter < goals.size(); counter++) {
            if (goals.get(counter).getTitle().equals(goalTitle)){
                temp.add(goals.get(counter));
            }
        }
        for (int counter1 = 0; counter1 < temp.size(); counter1++) {
            goals.remove(temp.get(counter1));
        }
        goals.add(new Goal(goalTitle, subGoal,goalComplete, deadline));
    }
    public void removeGoal(String goal){
        ArrayList<Goal> temp = new ArrayList<>();
        for (int counter = 0; counter < goals.size(); counter++) {
            if (goals.get(counter).getTitle().equals(goal)){
                temp.add(goals.get(counter));
            }
        }
        for (int counter1 = 0; counter1 < temp.size(); counter1++) {
            goals.remove(temp.get(counter1));
        }

    }
    public void addTimeTableData(String title, String details,Calendar time){
        ArrayList<TimeTable> temp = new ArrayList<>();
        for (int counter = 0; counter < timetable.size(); counter++) {
            if (timetable.get(counter).getDateTime() == time){
                temp.add(timetable.get(counter));
            }
        }
        for (int counter1 = 0; counter1 < temp.size(); counter1++) {
            timetable.remove(temp.get(counter1));
        }
        timetable.add(new TimeTable(title, details, time));
    }
    public void removeTimeTableData(String title, Calendar time){
        ArrayList<TimeTable> temp = new ArrayList<>();
        for (int counter = 0; counter < goals.size(); counter++) {
            if (timetable.get(counter).getTitle().equals(title) && timetable.get(counter).getDateTime() == time){
                temp.add(timetable.get(counter));
            }
        }
        for (int counter1 = 0; counter1 < temp.size(); counter1++) {
            timetable.remove(temp.get(counter1));
        }

    }

   public ArrayList<Goal> getGoals(){
        ArrayList<Goal> temp;
        temp = goals;
       Collections.sort(temp, new Comparator<Goal>() {
           public int compare(Goal o1, Goal o2) {
               return o1.getDateTime().compareTo(o2.getDateTime());
           }
       });
        return temp;
    }
    public ArrayList<TimeTable> getTimeTable(){
        ArrayList<TimeTable> temp;
        temp = timetable;
        Collections.sort(temp, new Comparator<TimeTable>() {
            public int compare(TimeTable o1, TimeTable o2) {
                return o1.getDateTime().compareTo(o2.getDateTime());
            }
        });
        return temp;
    }
}
