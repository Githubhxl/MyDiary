package com.nexuslink.mydiary.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Rye on 2016/12/7.
 */

public class DataTools {
    public static MyDiaryDatabaseHelper getSQLiteDatabase(Context context, String name, SQLiteDatabase.CursorFactory cursorFactory, int version){
         return MyDiaryDatabaseHelper.getInstance(context,name,cursorFactory,version);
    }
}
