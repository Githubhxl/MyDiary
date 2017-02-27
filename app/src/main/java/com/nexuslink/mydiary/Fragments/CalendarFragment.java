package com.nexuslink.mydiary.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.nexuslink.mydiary.Custom.MyCalendarView;
import com.nexuslink.mydiary.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class CalendarFragment extends Fragment {
    private static CalendarFragment instance;
    private LinearLayout mLinearLayout;

    public static CalendarFragment getInstance(){
        if(instance == null){
            instance = new CalendarFragment();
        }
        return instance;
    }

    public CalendarFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_calendar, container, false);
        mLinearLayout= (LinearLayout) view.findViewById(R.id.calendar_parent);
        int wight=getActivity().getWindowManager().getDefaultDisplay().getWidth();
        MyCalendarView myCalendarView =new MyCalendarView(getContext());
        LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(wight,wight);
        mLinearLayout.addView(myCalendarView,lp);
        return view;
    }

}
