package com.happiness.ministry.moodtracker.Models;

import com.happiness.ministry.moodtracker.Utilities.DateUtilities;

import java.util.Date;

/**
 * Created by Michaël SAGOT on 18/03/2018.
 */

public class Mood {

    private int mMoodIndex;         // Mood index (0 - 4) of the "array_smileys" (res/values/arrays.xml)
    private String mComment;        // Comment of the Mood
    private Date mDate;             // Backup date of the Mood ( current date )

    /**
     * Constructor by default
     *
     * return the instance of the new Mood
     */
    public Mood() {

        mMoodIndex = 3;        // Happy Mood by default
        mComment = "";         // Comment empty by default
        mDate = new Date();    // Current date
    }

    public int getMoodIndex() {
        return mMoodIndex;
    }

    public void setMoodIndex(int moodIndex) {
        mMoodIndex = moodIndex;
    }

    public String getComment() {
        return mComment;
    }

    public void setComment(String comment) {
        mComment = comment;
    }

    public Date getDate() {
        return mDate;
    }
}
