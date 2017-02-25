package com.nexuslink.mydiary.Tools;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Rye on 2016/12/7.
 */

public class MyDiaryDatabaseHelper extends SQLiteOpenHelper {
    private static MyDiaryDatabaseHelper myDiaryDatabaseHelper;
    private static final String CREATE_DIARY = "create table Diary("
            + "id integer primary key autoincrement,"
            + "date integer,"
            + "weekday text,"
            + "time text,"
            + "title text,"
            + "mood integer,"
            + "position text,"
            + "weather integer,"
            + "tag text,"
            + "year integer,"
            + "month integer,"
            + "content text)";

    private MyDiaryDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    static MyDiaryDatabaseHelper getInstance(Context context, String name, SQLiteDatabase.CursorFactory cursorFactory, int version){
        if(myDiaryDatabaseHelper == null){
            myDiaryDatabaseHelper = new MyDiaryDatabaseHelper(context,name,cursorFactory,version);
        }
        return myDiaryDatabaseHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_DIARY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
