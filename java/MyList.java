package com.example.cdsteer.memoapp;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.view.View;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by cdsteer on 03/04/15.
 */
public class MyList extends SimpleCursorAdapter {

    public MyList(Context context, int layout, Cursor c, String[] from, int[] to) {
        super(context, layout, c, from, to);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor){
        TextView bodyText = (TextView) view.findViewById(R.id.content);
        bodyText.setText(cursor.getString(cursor.getColumnIndex(DbHelper.KEY_CONTENT)));

        TextView typeText = (TextView) view.findViewById(R.id.urgentLvl);
        typeText.setText(cursor.getString(cursor.getColumnIndex(DbHelper.KEY_URGENT)));
        String isUrgent = cursor.getString(cursor.getColumnIndex(DbHelper.KEY_DATE));
        if (!isUrgent.equals("")) {
            setTime(view, cursor);
        }
        String priority = cursor.getString(cursor.getColumnIndex(DbHelper.KEY_URGENT));
        setColor(view, priority);
    }

    private void setTime(View view, Cursor cursor) {
        TextView dateText = (TextView) view.findViewById(R.id.date);
        dateText.setText(cursor.getString(cursor.getColumnIndex(DbHelper.KEY_DATE)));

        TextView timeText = (TextView) view.findViewById(R.id.alarm);
        timeText.setText(cursor.getString(cursor.getColumnIndex(DbHelper.KEY_ALARM)));

        long timeLeft = timeLeft(cursor);
        TextView timeLeftText = (TextView) view.findViewById(R.id.time_left);
        if (timeLeft > 0) {
            timeLeftText.setText("Days Until: " + timeLeft);
        } else {
            timeLeftText.setText("Alarm Passed");
        }

        TextView hiding = (TextView) view.findViewById(R.id.time_left);
        hiding.setVisibility(View.VISIBLE);
        hiding = (TextView) view.findViewById(R.id.date);
        hiding.setVisibility(View.VISIBLE);
        hiding = (TextView) view.findViewById(R.id.alarm);
        hiding.setVisibility(View.VISIBLE);
    }

    private void setColor(View v, String priority){
        if (priority.equals("Normal")){
            v.setBackgroundColor(Color.parseColor("#00b200"));
        } else if (priority.equals("Important")){
            v.setBackgroundColor(Color.parseColor("#ffc500"));
        } else if (priority.equals("Urgent")){
            v.setBackgroundColor(Color.parseColor("#ff0000"));
        }

    }

    private long timeLeft(Cursor cursor) {
        String myDate = "";
        Calendar cal = Calendar.getInstance();
        Calendar rightNow = Calendar.getInstance();
        myDate = (cursor.getString(cursor.getColumnIndex(DbHelper.KEY_DATE))+" "+cursor.getString(cursor.getColumnIndex(DbHelper.KEY_ALARM)));
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        try {
            Date date = df.parse(myDate);
            cal.setTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Long timeLeft = daysBetween(rightNow, cal);
        return timeLeft;
    }

    private long daysBetween(Calendar startDate, Calendar endDate) {
        Calendar date = (Calendar) startDate.clone();
        long daysBetween = 0;
        while (date.before(endDate)) {
            date.add(Calendar.DAY_OF_MONTH, 1);
            daysBetween++;
        }
        return daysBetween;
    }
}
