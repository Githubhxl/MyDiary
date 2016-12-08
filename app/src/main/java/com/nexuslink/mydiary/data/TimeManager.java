package com.nexuslink.mydiary.data;

import java.util.Calendar;

/**
 * Created by Rye on 2016/12/8.
 */

public class TimeManager {
    public static String getWeekday() {
        switch (Calendar.getInstance().get(Calendar.DAY_OF_WEEK)) {
            case 1:
                return "Sunday";
            case 2:
                return "Monday";
            case 3:
                return "Tuesday";
            case 4:
                return "Wednesday";
            case 5:
                return "Thursday";
            case 6:
                return "Friday";
            case 7:
                return "Saturday";
        }
        return "null";
    }

    public static String getSimpleWeekday() {
        switch (Calendar.getInstance().get(Calendar.DAY_OF_WEEK)) {
            case 1:
                return "Sun.";
            case 2:
                return "Mon.";
            case 3:
                return "Tues.";
            case 4:
                return "Wed.";
            case 5:
                return "Thur.";
            case 6:
                return "Fri.";
            case 7:
                return "Sat.";
        }
        return "null";
    }

    public static String getMonth() {
        switch (Calendar.getInstance().get(Calendar.MONTH)){
            case 1:
                return "January";
            case 2:
                return "February";
            case 3:
                return "march";
            case 4:
                return "April";
            case 5:
                return "May";
            case 6:
                return "June";
            case 7:
                return "July";
            case 8:
                return "August";
            case 9:
                return "September";
            case 10:
                return "October";
            case 11:
                return "December";
            case 12:
                return "November";
        }
        return "null";
    }


    public static int getDate(){
        return Calendar.getInstance().get(Calendar.DATE);
    }
}
