package com.example.easy_studium;

import android.app.TimePickerDialog;
import android.os.Build;
import android.util.Log;
import android.widget.Spinner;
import android.widget.TimePicker;

import androidx.annotation.RequiresApi;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

@RequiresApi(api = Build.VERSION_CODES.O)
public class Event {
    public static ArrayList<Event> eventsList = new ArrayList<>();

    public static ArrayList<Event> eventsForDate(LocalDate date) {
        ArrayList<Event> events = new ArrayList<>();


        for (int i=0; i<eventsList.size();i++){
            int eventDate = eventsList.get(i).getDate().getDayOfYear();
            int cellDate = date.getDayOfYear();
            if (eventDate==cellDate &&i==0)
                events.add(eventsList.get(i));

        }

        return events;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static ArrayList<Event> eventsForDateAndTime(LocalDate date, LocalTime time) {
        ArrayList<Event> events = new ArrayList<>();


        for (Event event : eventsList) {

            int eventHour = event.getTimePicker().getHour();
            int cellHour = time.getHour();
            int eventMinute = event.getTimePicker().getMinute();
            int cellMinute = time.getMinute();


                if (event.getDate().equals(date) && eventHour == cellHour ) {
                        if (eventMinute < 30 && cellMinute == 0) {
                            //event.getTimePicker().setHour(i);
                            events.add(event);
                        }
                        if (eventMinute >= 30 && cellMinute == 30) {
                            events.add(event);
                        }




/*
                    if (eventMinute >= 30) {
                        eventHour++;
                        eventMinute -= 30;
                    } else {
                        eventMinute += 30;
                    }

                    event.getTimePicker().setHour(eventHour);

                    event.getTimePicker().setMinute(eventMinute);
*/
                    Log.d("Event", "" + event.getTimePicker().getHour() + ":" + event.getTimePicker().getMinute());


                }
                Log.d("Event", "" + event.getTimePicker().getHour() + ":" + event.getTimePicker().getMinute());
        }

        return events;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static ArrayList<Event> eventsForDateAndTimePicker(LocalDate date, TimePicker time) {
        ArrayList<Event> events = new ArrayList<>();

        for (Event event : eventsList) {
            int eventHour = event.time.getHour();
            int cellHour = time.getHour();
            if (event.getDate().equals(date) && eventHour == cellHour)
                events.add(event);
        }

        return events;
    }

    private String name;
    private LocalDate date;
    private LocalTime time;
    private Object exam;
    private Object examMode;

    private TimePicker timePicker;





    public Event(String name, LocalDate date, LocalTime time, Object exam, Object examMode, TimePicker timePicker) {
        this.name = name;
        this.date = date;
        this.exam=exam;
        this.examMode=examMode;
        this.time = time;
        this.timePicker = timePicker;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public TimePicker getTimePicker() {
        return timePicker;
    }

    public void setTimePicker(TimePicker timePicker) {
        this.timePicker = timePicker;
    }

    public Object getExam() {
        return exam;
    }

    public void setExam(Object exam) {
        this.exam = exam;
    }

    public Object getExamMode() {
        return examMode;
    }

    public void setExamMode(Object examMode) {
        this.examMode = examMode;
    }
}