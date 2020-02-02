package com.cameron.finalyearprojectv2;

public final class UserData {
    private  UserData(){
    }
    private Goal goals = new Goal();
    private static UserData instance;
    public static UserData getInstance(){
        if (instance == null){
            instance = new UserData();
        }
        return instance;
    }

    public void addGoal(String goalTitle, String subGoal, String deadline){
        String[]split = deadline.split("/");
        if (split[0].length() == 1){
            split[0] = "0" + split[0];
        }
        if (split[1].length() == 1){
            split[1] = "0" + split[1];
        }
        String newDeadline = split[0] + "/" +split[1] + "/" + split[2];
        goals.addGoal(goalTitle, subGoal, newDeadline);
    }
    public void removeGoal(String goal){
        goals.removeGoal(goal);
    }

    public Goal getGoals(){
        return goals;
    }

}
