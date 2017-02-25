package com.nexuslink.mydiary.Fragments;


import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nexuslink.mydiary.Activities.MainActivity;
import com.nexuslink.mydiary.Custom.Diary;
import com.nexuslink.mydiary.R;
import com.nexuslink.mydiary.Tools.DataTools;
import com.nexuslink.mydiary.Tools.EditDiaryTool;
import com.nexuslink.mydiary.Tools.MyDiaryDatabaseHelper;
import com.nexuslink.mydiary.Tools.TimeManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class DiaryFragment extends Fragment {
    private static DiaryFragment instance;

    @BindView(R.id.diary_viewpager)
    ViewPager viewPager;

    @OnClick(R.id.clear) void showMoreDialog(){

    }
    @OnClick(R.id.add_pic) void addPic(){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent,0);
    }
    @OnClick(R.id.finish) void finishEdit(){
        /*MyNotifyDialog finish = new MyNotifyDialog(getContext());
        finish.show();
        finish.getTextView().setText(R.string.finish);*/
        DiaryPage1Fragment diaryPage1Fragment = DiaryPage1Fragment.getInstance();
        DiaryPage2Fragment diaryPage2Fragment = DiaryPage2Fragment.getInstance();
        Diary diary = new Diary(
                DataTools.getDiaryCount(getContext()),
                TimeManager.getDate(),
                TimeManager.getSimpleWeekday(), Calendar.getInstance().get(Calendar.HOUR_OF_DAY)+":"+ Calendar.getInstance().get(Calendar.MINUTE),
                diaryPage1Fragment.getTitleView().getText().toString(),
                diaryPage1Fragment.editDiary.getText().toString(),
                1,1,"日常",
                diaryPage2Fragment.editText.getText().toString(),
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH)+ 1
                );
        addDiary(getContext(), diary);
        commitDiary();
    }

    private void commitDiary() {
        DiaryPage1Fragment.getInstance().getEditDiary().setText("");
        DiaryPage1Fragment.getInstance().setCurrentTitle("");
        //d2
        MainActivity main = (MainActivity) getActivity();
        main.changeFragment(EntriesFragment.getInstance());
        main.setEntiesStatus(true);
    }
    /*@OnClick(R.id.super_line) void addLink(){
        EditDiaryTool.insertLink("123","www.baidu.com",DiaryPage1Fragment.getInstance().getEditDiary());
    }*/

    public static DiaryFragment getInstance(){
        if(instance == null){
            instance = new DiaryFragment();
        }
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_diary, container, false);
        ButterKnife.bind(this,view);
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(DiaryPage1Fragment.getInstance());
        fragments.add(DiaryPage2Fragment.getInstance());
        viewPager.setAdapter(new DiaryViewPager(getChildFragmentManager(),fragments));


        return view;
    }

    private class DiaryViewPager extends FragmentPagerAdapter {
        private List<Fragment> fragments;

        public DiaryViewPager(FragmentManager fm,List<Fragment> fragments) {
            super(fm);
            this.fragments = fragments;
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            switch (requestCode){
                case 0:
                    EditDiaryTool.insertImage(DiaryPage1Fragment.getInstance().getEditDiary(),data.getData(),getContext());
                    break;
            }
        }
    }

    public void addDiary(Context context, Diary diary) {
        MyDiaryDatabaseHelper myDiaryDatabaseHelper = DataTools.getSQLiteDatabase(context,"Diary",null,1);
        SQLiteDatabase sqLiteDatabase = myDiaryDatabaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("content", diary.getContent());
        contentValues.put("time", diary.getTime());
        contentValues.put("title", diary.getTitle());
        contentValues.put("weekday", diary.getWeekday());
        contentValues.put("date", diary.getDate());
        contentValues.put("id", diary.getId());
        contentValues.put("year",diary.getYear());
        contentValues.put("month",diary.getMonth());
        sqLiteDatabase.insert("Diary",null,contentValues);
    }
}
