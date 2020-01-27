package com.cameron.finalyearprojectv2;

import java.util.List;

public final class UserData {
    private  UserData(){
    }
    private List<Goal> goals;
    private static UserData instance;
    public static UserData getInstance(){
        if (instance == null){
            instance = new UserData();
        }
        return instance;
    }

    public void addGoal(String goal, String deadline){
        Goal newGoal = new Goal();
        newGoal.addGoal(goal, deadline);
        goals.add(newGoal);
    }
    public void removeGoal(Goal goal){
        goals.remove(goal);
    }

    public List<Goal> getGoals(){
        return goals;
    }

}
