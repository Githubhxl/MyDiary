package com.nexuslink.mydiary.model;

/**
 * Created by Rye on 2016/12/7.
 */

public class DiaryModel {
    private int id;
    private int date;
    private String weekday;
    private String time;
    private String title;
    private String content;

    public DiaryModel(int id, int date, String weekday,String time, String title,String content) {
        this.id = id;
        this.content = content;
        this.title = title;
        this.time = time;
        this.weekday = weekday;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public int getDate() {
        return date;
    }

    public String getContent() {
        return content;
    }

    public String getTime() {
        return time;
    }

    public String getTitle() {
        return title;
    }

    public String getWeekday() {
        return weekday;
    }

    @Override
    public String toString() {
        return "Data:"+date+"weekday"+weekday+"time"+time+"title"+title+"content"+content;
    }
}
