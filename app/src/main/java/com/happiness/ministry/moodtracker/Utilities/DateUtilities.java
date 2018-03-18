package com.happiness.ministry.moodtracker.Utilities;

import android.util.Log;

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
    public static int getIntDateOfDaySSAAMMJJ() {

        // Utilities formatted as : %1$tY = SSAA , %1$tm = MM , %1$td = JJ
        String formatString = "%1$tY%1$tm%1$td";

        // Day of year in Format String SSAAMMJJ
        String dateStringSSAAMMJ = String.format(formatString,new Date());

        // Day of year in Format int SSAAMMJJ
        int dateIntSSAAMMJJ = Integer.parseInt(dateStringSSAAMMJ);

        return dateIntSSAAMMJJ;
    }

}
