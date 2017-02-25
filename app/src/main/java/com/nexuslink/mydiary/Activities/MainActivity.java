package com.nexuslink.mydiary.Activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.nexuslink.mydiary.Fragments.CalendarFragment;
import com.nexuslink.mydiary.Fragments.DiaryFragment;
import com.nexuslink.mydiary.Fragments.EntriesFragment;
import com.nexuslink.mydiary.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.navi_title)
    TextView naviTitle;
    @BindView(R.id.content)
    FrameLayout content;
    @BindView(R.id.dairy)
    RadioButton buttonDiary;
    @BindView(R.id.calendar)
    RadioButton buttonCalendar;
    @BindView(R.id.entries)
    RadioButton buttonEntries;


    @OnClick(R.id.entries) void naviEntriesClick(){
        naviTitle.setText(R.string.entries);
        changeFragment(EntriesFragment.getInstance());
    }

    @OnClick(R.id.calendar) void naviCalendarClick(){
        naviTitle.setText(R.string.calendar);
        changeFragment(CalendarFragment.getInstance());
    }


    @OnClick(R.id.dairy) void naviDairyClick(){
        naviTitle.setText(R.string.dairy);
        changeFragment(DiaryFragment.getInstance());
    }

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        changeFragment(new EntriesFragment());
    }

    public void changeFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.content,fragment).commit();
    }

    @Override
    public void onBackPressed() {

    }
    public void setEntiesStatus(boolean b){
        buttonEntries.setChecked(b);
    }
}
