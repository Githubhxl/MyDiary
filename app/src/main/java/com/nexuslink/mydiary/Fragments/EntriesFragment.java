package com.nexuslink.mydiary.Fragments;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nexuslink.mydiary.Custom.Diary;
import com.nexuslink.mydiary.Custom.DiaryDialogActivity;
import com.nexuslink.mydiary.R;
import com.nexuslink.mydiary.Tools.DataTools;
import com.nexuslink.mydiary.Tools.MyDiaryDatabaseHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class EntriesFragment extends Fragment {
    private static EntriesFragment instance;
    @BindView(R.id.entries_recycle) RecyclerView entriesecyclerView;
    @BindView(R.id.count_entries) TextView count;
    @BindView(R.id.no_entries_card) CardView cardView;
    private EntriesAdapter entriesAdapter;

    public static EntriesFragment getInstance(){
        if(instance == null){
            instance = new EntriesFragment();
        }
        return instance;
    }

    public EntriesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_entries, container, false);
        ButterKnife.bind(this,view);
        int num = DataTools.getDiaryCount(getContext());
        count.setText(num+"  Entries");
        if(num == 0){
            cardView.setVisibility(View.VISIBLE);
        }
        entriesecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        List<Diary> entriesList = getEntriesList();
        entriesecyclerView.setAdapter(entriesAdapter = new EntriesAdapter(entriesList));
        return view;
    }

    private List<Diary> getEntriesList() {
        MyDiaryDatabaseHelper myDiaryDatabaseHelper = DataTools.getSQLiteDatabase(getContext(),"Diary",null,1);
        SQLiteDatabase sqLiteDatabase = myDiaryDatabaseHelper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.query("Diary",null,null,null,null,null,null);
        List<Diary> list = new ArrayList<>();
        if(cursor.moveToLast()){
            do{
                Diary diary = new Diary(
                        cursor.getInt(cursor.getColumnIndex("id")),
                        cursor.getInt(cursor.getColumnIndex("date")),
                        cursor.getString(cursor.getColumnIndex("weekday")),
                        cursor.getString(cursor.getColumnIndex("time")),
                        cursor.getString(cursor.getColumnIndex("title")),
                        cursor.getString(cursor.getColumnIndex("content")),
                        cursor.getInt(cursor.getColumnIndex("weather")),
                        cursor.getInt(cursor.getColumnIndex("mood")),
                        cursor.getString(cursor.getColumnIndex("tag")),
                        cursor.getString(cursor.getColumnIndex("position")),
                        cursor.getInt(cursor.getColumnIndex("year")),
                        cursor.getInt(cursor.getColumnIndex("month")));
                list.add(diary);
            }while (cursor.moveToPrevious());
        }
        return list;
    }

    private class EntriesAdapter extends RecyclerView.Adapter<EntriesViewHolder> implements View.OnClickListener {
        private List<Diary> entriesList;
        public EntriesAdapter(List<Diary> entriesList) {
            this.entriesList = entriesList;
        }



        @Override
        public EntriesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.item_entries,parent,false);
            view.setOnClickListener(this);
            return new EntriesViewHolder(view);
        }

        @Override
        public void onBindViewHolder(EntriesViewHolder holder, final int position) {
            if(isShowMonth(position)){
                holder.month.setVisibility(View.VISIBLE);
                holder.month.setText(entriesList.get(position).getMonth()+"");
            }else{
                holder.month.setVisibility(View.GONE);
            }
            Diary diary = entriesList.get(position);
            holder.title.setText(diary.getTitle());
            holder.content.setText(diary.getContent());
            holder.date.setText(diary.getDate()+"");
            holder.weekday.setText(diary.getWeekday());
            holder.time.setText(diary.getTime());

            holder.cardview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getContext(),DiaryDialogActivity.class);
                    startActivity(intent);
                    Log.d("TAG", String.valueOf(position));
                }
            });
        }

        private boolean isShowMonth(int position) {
            if(position == 0){
                return true;
            }else if (entriesList.get(position).getYear() != entriesList.get(position - 1).getYear()){
                return true;
            }else if(entriesList.get(position).getMonth() != entriesList.get(position - 1).getMonth()){
                return true;
            }
            return false;
        }

        @Override
        public int getItemCount() {
            return entriesList.size();
        }

        @Override
        public void onClick(View view) {

        }

    }

    private class EntriesViewHolder extends RecyclerView.ViewHolder{
        private TextView month;

        private TextView date;
        private TextView weekday;
        private TextView time;
        private TextView title;
        private TextView content;

        private CardView cardview;
        public EntriesViewHolder(View itemView) {
            super(itemView);
            month = (TextView) itemView.findViewById(R.id.month);
            date = (TextView) itemView.findViewById(R.id.diary_date);
            weekday = (TextView) itemView.findViewById(R.id.diary_weekday);
            time = (TextView) itemView.findViewById(R.id.diary_time);
            title = (TextView) itemView.findViewById(R.id.diary_title);
            content = (TextView) itemView.findViewById(R.id.diary_content);

            cardview = (CardView) itemView.findViewById(R.id.card_diary);
        }
    }
}
