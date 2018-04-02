package com.happiness.ministry.moodtracker.Utilities;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Michaël SAGOT on 18/03/2018.
 *
 * Utilitarian library for the processing of dates
 */

public class DateUtilities {

    // Method allowing the conversion of an object Dates in the format int SSAAMMJJ
    // example : in => Sun Mar 18 16:52:01 GMT+00:00 2018
    //          out => 20180318
    public static int getIntDateSSAAMMJJ(Date date) {

        // Utilities formatted as : %1$tY = SSAA , %1$tm = MM , %1$td = JJ
        String formatString = "%1$tY%1$tm%1$td";

        // Date in Format String SSAAMMJJ
        String dateStringSSAAMMJ = String.format(formatString,date);

        // Day of year in Format int SSAAMMJJ
        int dateIntSSAAMMJJ = Integer.parseInt(dateStringSSAAMMJ);

        return dateIntSSAAMMJJ;
    }

    // Calculate the difference between two dates in number of Days
    public static int getDaysBetweenTwoDates(Date date1, Date date2){

        long period1hour = 1000*60*60;     // 1000 milliseconds * 60 seconds * 60 minutes = 1 hour
        long period1day  = period1hour*24; // 1 hour * 24 = 1 day

        // Calculate the difference between two dates in milliseconds
        //long difference = Math.abs(date1.getTime()-date2.getTime());
        long difference = date1.getTime()-date2.getTime();

        Log.i("MOOD", "difference    = "+difference);
        Log.i("MOOD", "period1day    = "+period1day);

        // Return the number of days between two dates
        return (int) (difference / period1day);
    }
}
