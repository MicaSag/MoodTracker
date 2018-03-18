package com.happiness.ministry.moodtracker.Models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Michaël SAGOT on 18/03/2018.
 *
 * This Objet which will saved as preferences
 */

public class MoodPreferences {

    private int mLastMoodIndex;         // Index of the list corresponding to the last Mood
    private List<Mood> mMoodHistory;    // List containing 7 last Mood saved
    /**
     * Constructor by default
     */
    public MoodPreferences () {

        this.mLastMoodIndex = 0;                                // The first index 0
        List<Mood> moodList = new ArrayList<Mood>(7); // ArrayList of 7 posts
        moodList.add(new Mood());                               // Mood by default
        // Put Default Mood in the List : post (0)
        mMoodHistory = moodList;
    }

    public MoodPreferences(int lastMoodIndex, List<Mood> moodHistorical) {
        this.mLastMoodIndex = lastMoodIndex;
        this.mMoodHistory = moodHistorical;
    }

    public int getLastMoodIndex() {
        return mLastMoodIndex;
    }

    public void setLastMoodIndex(int lastMoodIndex) {
        mLastMoodIndex = lastMoodIndex;
    }

    public List<Mood> getMoodHistorical() {
        return mMoodHistory;
    }

    public void setMoodHistorical(List<Mood> moodHistorical) {
        mMoodHistory = moodHistorical;
    }

    /**
     * Method which turns the last saved Mood
     */
    public Mood getLastMood () {
        return mMoodHistory.get(mLastMoodIndex);
    }

    /**
     * Method which adds a new mood to the historical list
     */
    public void addMoodToPreferences (int lastMoodIndex, Mood mood) {
        this.mLastMoodIndex = lastMoodIndex;
        this.getMoodHistorical().set(lastMoodIndex,mood);
    }
}
