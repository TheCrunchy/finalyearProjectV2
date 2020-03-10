package com.cameron.finalyearprojectv2;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateForSpinner{
    private LocalDate date;

    public DateForSpinner(LocalDate cal){
        this.date = cal;
    }

    public LocalDate getDateTime(){
        return date;
    }

    //make this return the monday instead of making the calendar monday
    @Override
    public String toString() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return date.format(dateFormatter);
    }
}
