package com.nexuslink.mydiary.Tools;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by Rye on 2016/12/7.
 */

public class DataTools {

    public static MyDiaryDatabaseHelper getSQLiteDatabase(Context context, String name, SQLiteDatabase.CursorFactory cursorFactory, int version){
         return MyDiaryDatabaseHelper.getInstance(context,name,cursorFactory,version);
    }
    public static int getDiaryCount(Context context) {
        MyDiaryDatabaseHelper myDiaryDatabaseHelper = DataTools.getSQLiteDatabase(context, "Diary", null, 1);
        SQLiteDatabase sqLiteDatabase = myDiaryDatabaseHelper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.query("Diary", null, null, null, null, null, null);
        Log.d("TAG",cursor.getCount()+"");
        return cursor.getCount();
    }
}
