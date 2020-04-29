package com.cameron.finalyearprojectv2;

import java.util.Calendar;

public class Goal  implements Comparable<Goal>{
    private String title;
    private String subGoal;
    private Calendar deadline;
    private boolean status = false;
    public Goal(String goalTitle, String subGoal, Boolean isComplete, Calendar deadline){
        this.title = goalTitle;
        this.subGoal = subGoal;
        this.deadline = deadline;
        this.status = isComplete;
    }

    //Set the status to complete
    public void setCompletionStatus(Boolean status){
        this.status = status;
    }

    public Calendar getDateTime(){
        return deadline;
    }

    @Override
    public int compareTo(Goal o) {
        return getDateTime().compareTo(o.getDateTime());
    }

    public String getTitle(){
        return this.title;
    }
    public String getSubGoal() {
        return this.subGoal;
    }

    public boolean isComplete(){
        return status;
    }

    @Override
    public String toString() {
        return title;
    }

}
