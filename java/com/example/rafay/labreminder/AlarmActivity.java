package com.example.rafay.labreminder;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class AlarmActivity extends AppCompatActivity  {

    private int timeValue , min;
    //private dataLoc locService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            timeValue = extras.getInt("timeValue");
        }

        Toast.makeText(this, "timeValue: " + timeValue, Toast.LENGTH_LONG).show();
        min = timeValue/60;

        //locService = new dataLoc(this);

        // locService.getLoc();

        Toast.makeText(this, "Lab Timings recieved", Toast.LENGTH_LONG).show();

        // get calendar
        Calendar cal = Calendar.getInstance();
        Uri EVENTS_URI = Uri.parse(getCalendarUriBase(this) + "events");
        ContentResolver cr = getContentResolver();
        TimeZone timeZone = TimeZone.getDefault();
        long startTime = getLongAsDate(2016, 4, 10, 55, 55);
        long endTime = getLongAsDate(2016, 4, 10, 56, 45);

// event insert
        ContentValues values = new ContentValues();
        values.put("calendar_id", 1);
        values.put("title", "Software Construction Lab");
        values.put("allDay", 0);
        values.put("dtstart", startTime+ 2*60*1000); // event starts at 11 minutes from now(cal.getTimeInMillis() + 11*60*1000)
        values.put("dtend", endTime+ 1*60*1000); // ends 60 minutes from now (cal.getTimeInMillis()+60*60*1000)
        values.put("description", "Hurry up you will be late");
        values.put(CalendarContract.Events.EVENT_TIMEZONE, timeZone.getID());
        values.put("hasAlarm", 1);
        Uri event = cr.insert(EVENTS_URI, values);

// reminder insert
        Uri REMINDERS_URI = Uri.parse(getCalendarUriBase(this) + "reminders");
        values = new ContentValues();
        values.put( "event_id", Long.parseLong(event.getLastPathSegment()));
        values.put( "method", 1 );
        values.put( "minutes",1); // (100)//min
        cr.insert( REMINDERS_URI, values );

    }

    public void setAlarm (int remTime) throws ParseException {


    }
    private String getCalendarUriBase(Activity act) {

        String calendarUriBase = null;
        Uri calendars = Uri.parse("content://calendar/calendars");
        Cursor managedCursor = null;
        try {
            managedCursor = act.managedQuery(calendars, null, null, null, null);
        } catch (Exception e) {
        }
        if (managedCursor != null) {
            calendarUriBase = "content://calendar/";
        } else {
            calendars = Uri.parse("content://com.android.calendar/calendars");
            try {
                managedCursor = act.managedQuery(calendars, null, null, null, null);
            } catch (Exception e) {
            }
            if (managedCursor != null) {
                calendarUriBase = "content://com.android.calendar/";
            }
        }
        return calendarUriBase;
    }

    public long getLongAsDate(int year, int month, int day, int hour, int min) {
        Calendar calendar = new GregorianCalendar();
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, min);
        return calendar.getTimeInMillis();
    }

    /*@Override
    public void locserviceSuccess(double lat, double lng) {

    }

    @Override
    public void locserviceFailure(Exception e) {

    }*/
}
