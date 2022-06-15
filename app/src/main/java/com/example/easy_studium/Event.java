package com.example.easy_studium;

import android.app.TimePickerDialog;
import android.os.Build;
import android.widget.TimePicker;

import androidx.annotation.RequiresApi;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class Event
{
    public static ArrayList<Event> eventsList = new ArrayList<>();

    public static ArrayList<Event> eventsForDate(LocalDate date)
    {
        ArrayList<Event> events = new ArrayList<>();

        for(Event event : eventsList)
        {
            if(event.getDate().equals(date))
                events.add(event);
        }

        return events;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static ArrayList<Event>  eventsForDateAndTime(LocalDate date, LocalTime time)
    {
        ArrayList<Event> events = new ArrayList<>();

        for(Event event : eventsList)
        {
            int eventHour = event.getTimePicker().getHour();
            int cellHour = time.getHour();
            if(event.getDate().equals(date) && eventHour == cellHour)
                events.add(event);
        }

        return events;
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static ArrayList<Event>  eventsForDateAndTimePicker(LocalDate date, TimePicker time)
    {
        ArrayList<Event> events = new ArrayList<>();

        for(Event event : eventsList)
        {
            int eventHour = event.time.getHour();
            int cellHour = time.getHour();
            if(event.getDate().equals(date) && eventHour == cellHour)
                events.add(event);
        }

        return events;
    }

    private String name;
    private LocalDate date;
    private LocalTime time;


    private TimePicker timePicker;

    public Event(String name, LocalDate date, LocalTime time, TimePicker timePicker)
    {
        this.name = name;
        this.date = date;
        this.time = time;
        this.timePicker=timePicker;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public LocalDate getDate()
    {
        return date;
    }

    public void setDate(LocalDate date)
    {
        this.date = date;
    }

    public LocalTime getTime()
    {
        return time;
    }

    public void setTime(LocalTime time)
    {
        this.time = time;
    }

    public TimePicker getTimePicker() {
        return timePicker;
    }

    public void setTimePicker(TimePicker timePicker) {
        this.timePicker = timePicker;
    }
}
