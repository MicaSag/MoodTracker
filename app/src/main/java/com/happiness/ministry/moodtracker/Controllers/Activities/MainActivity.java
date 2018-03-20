package com.happiness.ministry.moodtracker.Controllers.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.happiness.ministry.moodtracker.Adapters.PageAdapter;
import com.happiness.ministry.moodtracker.Controllers.Fragments.PageFragment;
import com.happiness.ministry.moodtracker.Models.Mood;
import com.happiness.ministry.moodtracker.Models.MoodPreferences;
import com.happiness.ministry.moodtracker.R;
import com.happiness.ministry.moodtracker.Utilities.DateUtilities;

public class MainActivity extends AppCompatActivity  implements PageFragment.OnButtonClickedListener{

    // Defined Preferences of the application
    SharedPreferences mPreferences;

    // Defined a MoodPreferences Object
    MoodPreferences mMoodPreferences;

    // Defined the ViewPager use by this activity
    ViewPager pager;

    // Create the key saving of the preferences of the application
    public static final String PREF_KEY_MOOD = "PREF_KEY_MOOD";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the application
        this.init();

        // Configure ViewPager
        this.configureViewPager();
    }

    // Method allowing to restore the Preferences
    // and save the mood of the previous day if it's a new day
    private void init() {

        // Read Preferences
        mPreferences = getPreferences(MODE_PRIVATE);
        // TEST == >>> Allows to erase all the preferences ( Useful for the test phase )
        // preferences.edit().clear().commit();

        // Recovered PREF_KEY_MOOD in a String Object
        String moodPreferences = mPreferences.getString(PREF_KEY_MOOD,null);

        // if preferences PREF_KEY_MOOD is present
        if (moodPreferences != null) {
            Log.i("MOOD","PREFERENCES");

            // Restoring the preferences with a Gson Object
            Gson gson = new Gson();
            mMoodPreferences = gson.fromJson(moodPreferences,MoodPreferences.class);

            // If the backup date of the last mood is smaller than the current date
            // Then it is a new day
            if ( mMoodPreferences.getLastMood().getDateSSAAMMJJ()
                    < DateUtilities.getIntDateOfDaySSAAMMJJ() ){

                // If the list is already full, then we remove the first element of the list
                // And we add a new Mood by default
                if ( mMoodPreferences.getMoodHistorical().size() == 7 ) {
                    mMoodPreferences.getMoodHistorical().remove(0);
                }

                // Create a new Mood
                Mood newMood = new Mood();

                // Adds the new Mood in the moodPreferences
                mMoodPreferences.addMoodToPreferences(newMood);
            }
        // If not preference
        // It's the first time when the application is opened because the preferences are absent
        } else {
            Log.i("MOOD","NO PREFERENCES");
            // Creation of a new MoodPreferences Object with one Mood by Default
            mMoodPreferences = new MoodPreferences();
        }
        Log.i("MOOD", "Index   = "+String.valueOf(mMoodPreferences.getLastSavedMoodIndex()));
        Log.i("MOOD", "Mood    = "+mMoodPreferences.getLastMood().getMood());
        Log.i("MOOD", "Comment = "+mMoodPreferences.getLastMood().getComment());
        Log.i("MOOD", "Date    = "+mMoodPreferences.getLastMood().getDateSSAAMMJJ());
    }

    // Method allowing to configure the viewPager
    private void configureViewPager(){

        // Get ViewPager from layout
        pager = findViewById(R.id.activity_main_viewpager);

        // Set Adapter PageAdapter and glue it together

        pager.setAdapter(new PageAdapter(getSupportFragmentManager()) {
        });

        // Positioning the front page on Mood by default : smiley happy
        pager.setCurrentItem(mMoodPreferences.getLastMood().getMood());
    }

    @Override
    public void onButtonClicked(View view) {
        // Here, thanks to the callback implemented in the PageFragment
        // We are going to manage the click on the various buttons of PageFragment
        switch (view.getId()) {
            case R.id.fragment_page_history :
                Log.i("MOOD", "Click = history");
                mMoodPreferences.getLastMood().setComment("I'have clicked on HISTORY");
                Intent historyActivity = new Intent(MainActivity.this, HistoryActivity.class);
                startActivity(historyActivity);
                break;

            case R.id.fragment_page_comment :
                Log.i("MOOD", "Click = comment");
                mMoodPreferences.getLastMood().setComment("I'have clicked on COMMENT");
                break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("MOOD","status MainActivity = paused");

        // Change the day Mood in MoodPreference
        mMoodPreferences.getLastMood().setType(pager.getCurrentItem());

        // Create à PREF_KEY_MOOD String with a Gson Object
        final Gson gson = new GsonBuilder()
                .serializeNulls()
                .disableHtmlEscaping()
                .create();
        String json = gson.toJson(mMoodPreferences);

        // Add the new Mood in Preferences
        mPreferences.edit().putString(PREF_KEY_MOOD, json).apply();
    }
}
