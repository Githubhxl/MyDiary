package com.nexuslink.mydiary.Custom;

/**
 * Created by Rye on 2016/12/7.
 */

public class Diary {
    private int id;
    private int date;
    private String weekday;
    private String time;
    private String title;
    private String content;
    private int weather;
    private int mood;
    private String tag;
    private String position;
    private int year;
    private int month;

    public Diary(int id, int date, String weekday,
                 String time, String title, String content,
                 int weather, int mood, String tag, String position,
                 int year,int month) {
        this.id = id;
        this.content = content;
        this.title = title;
        this.time = time;
        this.weekday = weekday;
        this.date = date;
        this.weather = weather;
        this.mood = mood;
        this.tag = tag;
        this.position = position;
        this.year = year;
        this.month = month;
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

    public int getMood() {
        return mood;
    }

    public int getWeather() {
        return weather;
    }

    public String getPosition() {
        return position;
    }

    public String getTag() {
        return tag;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }
}
