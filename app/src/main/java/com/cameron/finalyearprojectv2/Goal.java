package com.cameron.finalyearprojectv2;

import java.util.HashMap;

public class Goal {
    private HashMap<String, HashMap<String, String> > goals = new HashMap<>();

    public void addGoal(String goalTitle, String goal, String deadline) {
        HashMap<String, String> temp = new HashMap<>();
        if (goals.containsKey(goalTitle)){
            temp = goals.get(goalTitle);
        }
        temp.put(goal, deadline);
        System.out.println(temp);
        System.out.println(goal + " " + deadline);
        goals.put(goalTitle, temp);
       // temp.clear();
        System.out.println(goals);
    }

    public void removeGoal(String goal){
        goals.remove(goal);
    }



    public HashMap<String, HashMap<String, String>> getAllGoals(){
        return goals;
    }
}
