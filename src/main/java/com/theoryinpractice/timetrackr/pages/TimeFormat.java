package com.theoryinpractice.timetrackr.pages;/*
 * Created by IntelliJ IDEA.
 * User: amrk
 * Date: 5/02/2006
 * Time: 23:40:37
 */

public class TimeFormat {
    private static final int LENGTH_SECOND = 1024;
    private static final int LENGTH_MINUTE = (LENGTH_SECOND * 60);
    private static final int LENGTH_HOUR = (LENGTH_MINUTE * 60);

    public static String format(long length) {

        if (length > LENGTH_HOUR) {
            // longer than an hour
            long hours = length / LENGTH_HOUR;
            long minutes = (length % LENGTH_HOUR) / LENGTH_MINUTE;

            if (minutes > 0) {
                return hours + (hours == 1 ? " hour" : " hours") + ", " + minutes + " minutes";
            } else {
                return hours + (hours == 1 ? " hour" : " hours");
            }

        } else if (length > LENGTH_MINUTE) {
            // longer than a minute
            long minutes = length / LENGTH_MINUTE;
            long seconds = (length % LENGTH_MINUTE) / LENGTH_SECOND;

            if (seconds > 0) {
                return minutes + " minutes, " + seconds + " seconds";
            } else {
                return minutes + " minutes";
            }

        } else if (length > 0) {
            // less than a minute
            return length / LENGTH_SECOND + " seconds";

        } else {
            return "";
        }
    }
}