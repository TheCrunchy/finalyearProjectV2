package com.cameron.finalyearprojectv2.ui;

import com.cameron.finalyearprojectv2.TimeTable;

import java.util.Calendar;

public class DateForSpinner implements Comparable<TimeTable>{
    private Calendar date;

    public DateForSpinner(Calendar cal){
        this.date = cal;
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
    }

    public Calendar getDateTime(){
        return date;
    }

    @Override
    public int compareTo(TimeTable o) {
        return getDateTime().compareTo(o.getDateTime());
    }

    @Override
    public String toString() {
    return "Week Beginning " + date.getTime().getDate() +  "/" + (date.getTime().getMonth() + 1) +  "/" + (date.getTime().getYear() + 1900);
    }
}
