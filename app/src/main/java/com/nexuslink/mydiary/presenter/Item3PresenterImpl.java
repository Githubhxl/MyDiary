package com.nexuslink.mydiary.presenter;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.nexuslink.mydiary.data.DataTools;
import com.nexuslink.mydiary.data.MyDiaryDatabaseHelper;
import com.nexuslink.mydiary.data.TimeManager;
import com.nexuslink.mydiary.model.DiaryModel;
import com.nexuslink.mydiary.model.IModel;
import com.nexuslink.mydiary.model.ModelImple;
import com.nexuslink.mydiary.view.diary.IItem3View;

/**
 * Created by Rye on 2016/12/7.
 */

public class Item3PresenterImpl implements IItem3Presenter {
    private IModel iModel;
    private IItem3View iItem3View;

    public Item3PresenterImpl(IItem3View iItem3View) {
        iModel = new ModelImple();
        this.iItem3View = iItem3View;
    }

    @Override
    public void saveDiary(Context context,String title,String content) {
        DiaryModel diaryModel = new DiaryModel(getDiaryCount(context),TimeManager.getDate(), TimeManager.getSimpleWeekday(),"17:25",title,content);
        iModel.addDiary(context,diaryModel);
    }

    private int getDiaryCount(Context context) {
        int i = 0;
        MyDiaryDatabaseHelper myDiaryDatabaseHelper = DataTools.getSQLiteDatabase(context, "Diary", null, 1);
        SQLiteDatabase sqLiteDatabase = myDiaryDatabaseHelper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.query("Diary", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                    i++;
            } while (cursor.moveToNext());
        }
        return i;
    }
}
