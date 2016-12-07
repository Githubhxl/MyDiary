package com.nexuslink.mydiary.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by Rye on 2016/12/7.
 */

public class MyDiaryDatabaseHelper extends SQLiteOpenHelper{
    private static MyDiaryDatabaseHelper myDiaryDatabaseHelper;
    private static final String CREATE_DIARY = "create table Diary("
            + "id integer primary key autoincrement,"
            + "date integer,"
            + "weekday text,"
            + "time text,"
            + "title text,"
            + "content text)";
    private Context context;

    private MyDiaryDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
        Toast.makeText(context,"123",Toast.LENGTH_SHORT).show();

    }

    public static MyDiaryDatabaseHelper getInstance(Context context, String name, SQLiteDatabase.CursorFactory cursorFactory, int version){
        if(myDiaryDatabaseHelper == null){
            myDiaryDatabaseHelper = new MyDiaryDatabaseHelper(context,name,cursorFactory,version);
        }
        return myDiaryDatabaseHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_DIARY);
        Toast.makeText(context,"chuajian",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
