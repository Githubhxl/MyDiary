package com.nexuslink.mydiary.view;

import android.os.Bundle;
import android.provider.Settings;
import android.renderscript.Sampler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nexuslink.mydiary.R;

import java.sql.Time;
import java.util.Calendar;

/**
 * Created by Rye on 2016/12/3.
 */

public class Item2Fragment extends Fragment{
    private TextView month;
    private TextView data;
    private TextView weekday;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_item2,container,false);
        month = (TextView) view.findViewById(R.id.calendar_month);
        data = (TextView) view.findViewById(R.id.calendar_date);
        weekday = (TextView) view.findViewById(R.id.calendar_weekday);
        setCalendar();
        return view;
    }

    private void setCalendar() {
        Calendar c = Calendar.getInstance();
        int month_now = c.get(Calendar.MONTH);
        int date_now = c.get(Calendar.DATE);
        int weekend_now = c.get(Calendar.DAY_OF_WEEK);

        month.setText(getMonth(month_now));
        data.setText(String.valueOf(date_now+""));
        weekday.setText(getWeekday(weekend_now));
    }

    private String getWeekday(int weekend_now) {
        switch (weekend_now) {
            case 1:
                return "Sunday";
            case 2:
                return "Monday";
            case 3:
                return "Tuesday";
            case 4:
                return "Wednesday";
            case 5:
                return "Thursday";
            case 6:
                return "Friday";
            case 7:
                return "Saturday";
        }
        return "null";
    }

    private String getMonth(int month_now) {
        switch (month_now){
            case 1:
                return "January";
            case 2:
                return "February";
            case 3:
                return "march";
            case 4:
                return "April";
            case 5:
                return "May";
            case 6:
                return "June";
            case 7:
                return "July";
            case 8:
                return "August";
            case 9:
                return "September";
            case 10:
                return "October";
            case 11:
                return "December";
            case 12:
                return "November";
        }
        return "null";
    }
}
