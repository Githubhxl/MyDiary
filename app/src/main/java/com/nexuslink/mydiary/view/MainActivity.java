package com.nexuslink.mydiary.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import com.nexuslink.mydiary.presenter.IPresenter;
import com.nexuslink.mydiary.presenter.PresenterImpl;
import com.nexuslink.mydiary.R;
import com.nexuslink.mydiary.view.calendar.Item2Fragment;
import com.nexuslink.mydiary.view.diary.Item3Fragment;
import com.nexuslink.mydiary.view.entries.Item1Fragment;
import com.nexuslink.mydiary.view.main.IView;

import butterknife.BindView;
import butterknife.ButterKnife;
import info.hoang8f.android.segmented.SegmentedGroup;

/**
 * Created by Rye on 2016/12/4.
 */
//com
public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener,IView {
    @BindView(R.id.segmented)
    SegmentedGroup segmentedGroup;
    @BindView(R.id.button1)
    RadioButton button1;
    @BindView(R.id.button2)
    RadioButton button2;
    @BindView(R.id.button3)
    RadioButton button3;
    @BindView(R.id.title)
    TextView title;

    public IPresenter iPresenter;
    private Item1Fragment item1Fragment;
    private Item2Fragment item2Fragment;
    private Item3Fragment item3Fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //初始化
        init();
    }

    private void init() {
        button1.setChecked(true);
        iPresenter = new PresenterImpl(this);
        segmentedGroup.setOnCheckedChangeListener(this);

        //默认使用第一页
        setDefaultFragment();
    }

    private void setDefaultFragment() {
        item1Fragment = new Item1Fragment();
        item2Fragment = new Item2Fragment();
        item3Fragment = new Item3Fragment();
        iPresenter.changeFragment(item1Fragment);
    }

    @Override
    public void change(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.framlayout,fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void changeCheck(int i) {
        if (i == 1){
            button1.setChecked(true);
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId){
            case R.id.button1:
                iPresenter.changeFragment(item1Fragment);
                title.setText("Entries");
                break;
            case R.id.button2:
                iPresenter.changeFragment(item2Fragment);
                title.setText("Calendar");
                break;
            case R.id.button3:
                iPresenter.changeFragment(item3Fragment);
                title.setText("Diary");
                break;
        }
    }
}
