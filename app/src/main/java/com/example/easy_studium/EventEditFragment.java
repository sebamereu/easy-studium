package com.example.easy_studium;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.sql.Time;
import java.time.LocalTime;
import java.util.Locale;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EventEditFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EventEditFragment extends DialogFragment {

    private EditText eventNameET;
    private TextView eventDateTV;
    private Button saveEventAction, eventTimeTV, eventTimeFinish;
    private LocalTime  time;
    private TimePicker eventTimeInizio, eventTimeFine;
    public static int hour, minute;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public EventEditFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EventEditFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EventEditFragment newInstance(String param1, String param2) {
        EventEditFragment fragment = new EventEditFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view;
       view = inflater.inflate(R.layout.fragment_event_edit, container, false);

        saveEventAction= view.findViewById(R.id.saveEventAction);
        eventNameET = view.findViewById(R.id.eventNameET);
        eventDateTV = view.findViewById(R.id.eventDateTV);
        eventTimeTV = view.findViewById(R.id.eventTimeTV);
        eventTimeFinish=view.findViewById(R.id.eventTimeFinish);

        time = LocalTime.now();
        eventDateTV.setText("Date: " + CalendarUtils.formattedDate(CalendarUtils.selectedDate));
        //eventTimeTV.setText("Time: " + CalendarUtils.formattedTime(time));

        eventTimeTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    //eventTimeTV.setRawInputType(InputType.TYPE_DATETIME_VARIATION_TIME);
                    TimePickerDialog.OnTimeSetListener onTimeSetListener=
                            new TimePickerDialog.OnTimeSetListener() {
                                @Override
                                public void onTimeSet(TimePicker eventTime, int hour, int minute) {

                                        eventTimeTV.setText("Time: " + eventTime.getHour() + ":" + eventTime.getMinute());


                                    eventTime.setHour(eventTime.getHour());
                                    eventTime.setMinute(eventTime.getMinute());
                                    Log.d("EventEditFragment", ""+ eventTime.getHour());
                                    eventTimeInizio=eventTime;
                                }
                            };
                    int style = AlertDialog.THEME_DEVICE_DEFAULT_DARK;

                    TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), style, onTimeSetListener, hour, minute, true );

                    timePickerDialog.setTitle("Select time");
                    timePickerDialog.show();

            }
        });

        eventTimeFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //eventTimeTV.setRawInputType(InputType.TYPE_DATETIME_VARIATION_TIME);
                TimePickerDialog.OnTimeSetListener onTimeSetListener1=
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker eventTime, int hour, int minute) {

                                eventTimeFinish.setText("Time: " + eventTime.getHour() + ":" + eventTime.getMinute());


                                eventTime.setHour(eventTime.getHour());
                                eventTime.setMinute(eventTime.getMinute());
                                Log.d("EventEditFragment", ""+ eventTime.getHour());
                                eventTimeFine=eventTime;
                            }
                        };
                int style = AlertDialog.THEME_DEVICE_DEFAULT_DARK;

                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), style, onTimeSetListener1, hour, minute, true );

                timePickerDialog.setTitle("Select time");
                timePickerDialog.show();

            }
        });





        saveEventAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String eventName = eventNameET.getText().toString();
                TimePicker timePickerInizio=eventTimeInizio;
                TimePicker timePickerFine=eventTimeFine;
                int hourInizio=timePickerInizio.getHour();
                int hourFine=timePickerFine.getHour();
                int minuteInizio=timePickerInizio.getMinute();
                int minuteFine=timePickerFine.getMinute();

                int cont=1;
                int max=(hourFine-hourInizio)*2;

                if (minuteFine>=30) max++;
                if (minuteInizio>=30) max--;
                if(hourFine-hourInizio==0) max=1;
                if (hourFine-hourInizio==0 && minuteFine<30) max=0;


                TimePicker[] timePickers = new TimePicker[max+1];
                for (int i=0;i<max;i++) {
                    timePickers[i] = new TimePicker(getContext());
                    //timePickers[i].setHour(hourInizio+i);
                    //timePickers[i].setMinute(minuteInizio);
                    if (i==0){
                        timePickers[i].setHour(hourInizio);
                        timePickers[i].setMinute(minuteInizio);
                    }else if (timePickers[i-1].getMinute() >= 30) {
                        timePickers[i].setHour(hourInizio + cont);
                        timePickers[i].setMinute(minuteInizio-30);
                        cont++;
                    } else {
                        timePickers[i].setHour(timePickers[i-1].getHour());
                        timePickers[i].setMinute(minuteInizio+30);
                    }
                    Log.d("EventEditFragment", "" + timePickers[i].getHour() + ":" + timePickers[i].getMinute());

                }

                Event[] events = new Event[max+1];

                //Event eventInizio = new Event(eventName, CalendarUtils.selectedDate, time, timePickerInizio);
                //Event.eventsList.add(eventInizio);
                //Log.d("EventEditFragment", "" + eventInizio.getTimePicker().getHour() + ":" + eventInizio.getTimePicker().getMinute());
                for (int i = 0;i<max;i++) {
                    events[i] = new Event(eventName, CalendarUtils.selectedDate, time, timePickers[i]);
                    Event.eventsList.add(events[i]);
                    //Log.d("EventEditFragment", "" + eventInizio.getTimePicker().getHour() + ":" + eventInizio.getTimePicker().getMinute());

                    //Log.d("EventEditFragment", "" + events[i].getTimePicker().getHour() + ":" + events[i].getTimePicker().getMinute());
                }



                Log.d("EventEditFragment", ""+ Event.eventsList.toString());

                replaceFragment(new DailyCalendarFragment());

           }
        });
        // Inflate the layout for this fragment
        return view;
    }

    void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }

    public static String TAG = "EventEditDialog";
}