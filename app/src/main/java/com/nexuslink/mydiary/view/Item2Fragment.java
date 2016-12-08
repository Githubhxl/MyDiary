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
import com.nexuslink.mydiary.data.TimeManager;

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

        month.setText(TimeManager.getMonth());
        int date = TimeManager.getDate();
        data.setText(date+"");
        weekday.setText(TimeManager.getWeekday());
    }

}
