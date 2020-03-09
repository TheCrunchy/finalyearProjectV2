package com.cameron.finalyearprojectv2;

import java.util.Calendar;

public class DateForSpinner implements Comparable<TimeTable>{
    private Calendar date;

    public DateForSpinner(Calendar cal){
        this.date = cal;
    }

    public Calendar getDateTime(){
        return date;
    }

    @Override
    public int compareTo(TimeTable o) {
        return getDateTime().compareTo(o.getDateTime());
    }


    //make this return the monday instead of making the calendar monday
    @Override
    public String toString() {

     //return String.valueOf(date.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY));

    //return "Week Beginning " + date.getTime().getDate() +  "/" + (date.getTime().getMonth() + 1) +  "/" + (date.getTime().getYear() + 1900);
        return date.getTime().getDate() +  "/" + (date.getTime().getMonth() + 1) +  "/" + (date.getTime().getYear() + 1900);
    }
}
