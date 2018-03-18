package com.happiness.ministry.moodtracker.Models;

import com.happiness.ministry.moodtracker.Utilities.DateUtilities;

/**
 * Created by Michaël SAGOT on 18/03/2018.
 */

public class Mood {

    private int mMood;              // Mood index of the "array_smileys" (res/values/arrays.xml)
    private String mComment;        // Comment of the Mood
    private int mDateSSAAMMJJ;      // Backup date of the Mood ( current date )

    /**
     * Constructor by default
     *
     * return the instance of the new Mood
     */
    public Mood() {

        mMood = 3;             // Happy Mood by default
        mComment = "";         // Comment empty by default
        // Current date in Format int SSAAMMJJ
        mDateSSAAMMJJ = DateUtilities.getIntDateOfDaySSAAMMJJ();
    }

    public int getMood() {
        return mMood;
    }

    public void setType(int mood) {
        mMood = mood;
    }

    public String getComment() {
        return mComment;
    }

    public void setComment(String comment) {
        mComment = comment;
    }

    public int getDateSSAAMMJJ() {
        return mDateSSAAMMJJ;
    }

    public void setDateSSAAMMJJ(int dateSSAAMMJJ) {
        mDateSSAAMMJJ = dateSSAAMMJJ;
    }
}