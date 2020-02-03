package com.cameron.finalyearprojectv2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public final class UserData {
    private  UserData(){
    }
    private ArrayList<Goal> goals = new ArrayList<Goal>();
    private static UserData instance;
    public static UserData getInstance(){
        if (instance == null){
            instance = new UserData();
        }
        return instance;
    }

    public void addGoal(String goalTitle, String subGoal, Date deadline){
        goals.add(new Goal(goalTitle, subGoal, deadline));
    }
    public void removeGoal(String goal){
        goals.remove(goal);
    }

   public ArrayList<Goal> getGoals(){
        ArrayList<Goal> temp = new ArrayList<>();

        int arraySize = goals.size();
        temp = goals;
       Collections.sort(temp, new Comparator<Goal>() {
           public int compare(Goal o1, Goal o2) {
               return o1.getDateTime().compareTo(o2.getDateTime());
           }
       });
        return temp;
    }

}
