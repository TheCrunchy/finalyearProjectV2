package com.cameron.finalyearprojectv2;

import java.util.Calendar;

public class TimeTable implements Comparable<TimeTable>{
    private String title;
    private String details;
    private Calendar time;
    public TimeTable(String title, String details, Calendar time){
        this.title = title;
        this.details = details;
        this.time = time;
    }
    public Calendar getDateTime(){
        return time;
    }

    @Override
    public int compareTo(TimeTable o) {
        return getDateTime().compareTo(o.getDateTime());
    }

    public String getTitle(){
        return this.title;
    }
    public String getDetails() {
        return this.details;
    }

    @Override
    public String toString() {
        return title;
    }

}
