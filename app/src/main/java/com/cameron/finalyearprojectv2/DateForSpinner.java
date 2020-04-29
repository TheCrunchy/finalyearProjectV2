package com.cameron.finalyearprojectv2;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
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



    @Override
    public String toString() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate ld = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return ld.format(dateFormatter);
    }
}
