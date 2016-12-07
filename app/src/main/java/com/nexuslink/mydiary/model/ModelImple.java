package com.nexuslink.mydiary.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.nexuslink.mydiary.data.DataTools;
import com.nexuslink.mydiary.data.MyDiaryDatabaseHelper;

/**
 * Created by Rye on 2016/12/7.
 */

public class ModelImple implements IModel {
    @Override
    public void addDiary(Context context, DiaryModel diaryModel) {
        MyDiaryDatabaseHelper myDiaryDatabaseHelper = DataTools.getSQLiteDatabase(context,"Diary",null,1);
        SQLiteDatabase sqLiteDatabase = myDiaryDatabaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("content",diaryModel.getContent());
        contentValues.put("time",diaryModel.getTime());
        contentValues.put("title",diaryModel.getTitle());
        contentValues.put("weekday",diaryModel.getWeekday());
        contentValues.put("date",diaryModel.getDate());
        contentValues.put("id",diaryModel.getId());
        sqLiteDatabase.insert("Diary",null,contentValues);

        /*Cursor cursor = sqLiteDatabase.query("Diary",null,null,null,null,null,null);
        if(cursor.moveToFirst()){
            do{
                Toast.makeText(context,cursor.getString(cursor.getColumnIndex("title")),Toast.LENGTH_SHORT).show();
            }while (cursor.moveToNext());
        }*/
    }

    @Override
    public void queryDiary() {

    }
}
