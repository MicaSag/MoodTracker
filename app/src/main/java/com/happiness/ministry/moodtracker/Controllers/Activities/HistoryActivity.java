package com.happiness.ministry.moodtracker.Controllers.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.happiness.ministry.moodtracker.Controllers.Fragments.HistoryFragment;
import com.happiness.ministry.moodtracker.Models.MoodPreferences;
import com.happiness.ministry.moodtracker.R;

public class HistoryActivity extends AppCompatActivity {


    // Defined String Preferences
    private String mPreferences;

    // Defined a MoodPreferences Object
    private MoodPreferences mMoodPreferences;

    // Create the key Preferences
    public static final String KEY_PREFERENCES = "KEY_PREFERENCES";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        // Get back Intent send to parameter by the MAinActivity
        Intent intent = getIntent();
        mPreferences = intent.getStringExtra(KEY_PREFERENCES);

        // Restoring the preferences with a Gson Object
        Gson gson = new Gson();
        mMoodPreferences = gson.fromJson(mPreferences,MoodPreferences.class);

        Log.i("MOOD", "____HISTORY____");
        Log.i("MOOD", "Index      = "+String.valueOf(mMoodPreferences.getLastSavedMoodIndex()));
        Log.i("MOOD", "Mood Index = "+mMoodPreferences.getLastMood().getMoodIndex());
        Log.i("MOOD", "Comment    = "+mMoodPreferences.getLastMood().getComment());
        Log.i("MOOD", "Date       = "+mMoodPreferences.getLastMood().getDateSSAAMMJJ());

        // If at least the mood precedent is present then Configure and show history fragments
        if (mMoodPreferences.getMoodHistorical().size()>1) this.configureAndShowMainFragment();
        else configureAnsShowNoHistory();

    }

    // --------------
    // FRAGMENTS
    // --------------

    // Declare history fragment reference
    private HistoryFragment historyFragment;

    // Method which configures and show no history message
    private void configureAnsShowNoHistory() {

    }

    // Method which configures and show history fragments
    private void configureAndShowMainFragment() {
        Log.i("MOOD", "____configureAndShowMainFragment()____");


        // Index of the previous Mood in the arrayList MoodHistorical
        int previousMoodIndex = mMoodPreferences.getMoodHistorical().size()-2;
        // Index of the "array_history" resource "arrays.xml"
        int displayIndex = 6;
        // Identifier of the FrameLayout to be update
        int resourceLayout;

        // Explore the previously registered list of moods
        for (int i = previousMoodIndex; i >= 0; i--) {

            // Get back the identifier of the FrameLayout to be update
            resourceLayout = getResources().obtainTypedArray(R.array.array_history)
                    .getResourceId(displayIndex,1);

            // Get back the identifier of the HistoryFragment
            // The FragmentManager (Support)
            historyFragment = (HistoryFragment) getSupportFragmentManager().findFragmentById(resourceLayout);
            // If no created HistoryFragment
            if (historyFragment == null) {
                // Create new history fragment and send Mood in parameter
                historyFragment = HistoryFragment.newInstance(mMoodPreferences
                                                                .getMoodHistorical().get(i));
                // Add it to FrameLayout container
                getSupportFragmentManager().beginTransaction()
                        .add(resourceLayout,historyFragment)
                        .commit();

                // Next Mood
                displayIndex--;
            }
        }
    }
}
