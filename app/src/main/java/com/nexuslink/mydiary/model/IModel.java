package com.nexuslink.mydiary.model;

import android.content.Context;

/**
 * Created by Rye on 2016/12/7.
 */

public interface IModel {
    void addDiary(Context context, DiaryModel diaryModel);
    void queryDiary();
}
