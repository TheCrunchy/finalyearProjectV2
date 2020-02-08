package com.cameron.finalyearprojectv2;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;

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

    public void addGoal(String goalTitle, String subGoal, Calendar deadline){
        goals.add(new Goal(goalTitle, subGoal, deadline));
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
