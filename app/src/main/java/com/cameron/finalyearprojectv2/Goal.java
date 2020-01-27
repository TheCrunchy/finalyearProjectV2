package com.cameron.finalyearprojectv2;

import java.util.HashMap;

public class Goal {
    private HashMap<String, String> goals = new HashMap<>();

    public void addGoal(String goal, String deadline) {
        goals.put(goal, deadline);
    }

    public void removeGoal(String goal, String deadline){
        goals.remove(goal, deadline);
    }

    public int calculateDailyGoals(){

        for (String s: goals.values()) {

        }
        return 0;
    }
}
