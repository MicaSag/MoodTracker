package com.happiness.ministry.moodtracker.Models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Michaël SAGOT on 18/03/2018.
 *
 * This Objet which will saved as preferences
 */

public class MoodPreferences {

    private int mLastSavedMoodIndex;    // Index of the list corresponding to the last Mood saved
    private List<Mood> mMoodHistory;    // List containing 7 last Mood saved
    /**
     * Constructor by default
     */
    public MoodPreferences () {

        this.mLastSavedMoodIndex = 0;                           // The first index 0
        List<Mood> moodList = new ArrayList<Mood>(7); // ArrayList of 7 Moods
        moodList.add(new Mood());                               // Mood by default
        // Put Default Mood in the List : post (0)
        mMoodHistory = moodList;
    }

    public MoodPreferences(int lastMoodIndex, List<Mood> moodHistorical) {
        this.mLastSavedMoodIndex = lastMoodIndex;
        this.mMoodHistory = moodHistorical;
    }

    public int getLastMoodIndex() {
        return mLastSavedMoodIndex;
    }

    public void setLastMoodIndex(int lastMoodIndex) {
        mLastSavedMoodIndex = lastMoodIndex;
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
        return mMoodHistory.get(mLastSavedMoodIndex);
    }

    /**
     * Method which adds a new mood to the historical list
     */
    public void addMoodToPreferences (int lastSavedMoodIndex, Mood mood) {
        this.mLastSavedMoodIndex = lastSavedMoodIndex;
        this.getMoodHistorical().add(mood);
    }
}
