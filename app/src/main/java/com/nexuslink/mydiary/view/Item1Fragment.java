package com.nexuslink.mydiary.view;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nexuslink.mydiary.R;
import com.nexuslink.mydiary.data.DataTools;
import com.nexuslink.mydiary.data.MyDiaryDatabaseHelper;

/**
 * Created by Rye on 2016/12/3.
 */

public class Item1Fragment extends Fragment implements View.OnClickListener {
    private LinearLayout diaryLinear;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_item1,container,false);
        ImageView menu = (ImageView) view.findViewById(R.id.menu);
        menu.setOnClickListener(this);

        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.month_diary_linear);

        View view1 = LayoutInflater.from(getContext()).inflate(R.layout.item_month,null);//月日历
        diaryLinear = (LinearLayout) view1.findViewById(R.id.item_diary_linear);//月日历中查找linear
        diaryLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),DialogActivity.class);
                startActivity(intent);
            }
        });
        linearLayout.addView(view1,0);


        linearLayout.addView(LayoutInflater.from(getContext()).inflate(R.layout.item_month,null));

        MyDiaryDatabaseHelper myDiaryDatabaseHelper = DataTools.getSQLiteDatabase(getContext(),"Diary",null,1);
        SQLiteDatabase sqLiteDatabase = myDiaryDatabaseHelper.getWritableDatabase();
        Log.d("TAG","SIZE"+sqLiteDatabase.getMaximumSize());
        Cursor cursor = sqLiteDatabase.query("Diary",null,null,null,null,null,null);
        if(cursor.moveToLast()){
            do{
                View view3 = LayoutInflater.from(getContext()).inflate(R.layout.item_diary,null);
                TextView title = (TextView) view3.findViewById(R.id.diary_title);
                TextView content = (TextView) view3.findViewById(R.id.diary_content);
                TextView date = (TextView) view3.findViewById(R.id.diary_date);
                TextView weekday = (TextView) view3.findViewById(R.id.diary_weekday);
                Log.d("TAG", String.valueOf(cursor.getColumnIndex("title")));
                title.setText(cursor.getString(cursor.getColumnIndex("title")));
                content.setText(cursor.getString(cursor.getColumnIndex("content")));
                date.setText(cursor.getString(cursor.getColumnIndex("date")));
                weekday.setText(cursor.getString(cursor.getColumnIndex("weekday")));

                diaryLinear.addView(view3);
                /*addDiary(diaryLinear);*/
            }while (cursor.moveToPrevious());
        }
        return view;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.menu:
                Intent intent = new Intent(getActivity(),MenuActivity.class);
                startActivity(intent);
               /* getActivity().overridePendingTransition(R.anim.myanim,R.anim.myanim);
                getActivity().finish();*/
        }
    }
}
