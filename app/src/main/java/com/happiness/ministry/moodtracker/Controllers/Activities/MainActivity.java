package com.happiness.ministry.moodtracker.Controllers.Activities;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.gson.Gson;
import com.happiness.ministry.moodtracker.Adapters.PageAdapter;
import com.happiness.ministry.moodtracker.Models.Mood;
import com.happiness.ministry.moodtracker.Models.MoodPreferences;
import com.happiness.ministry.moodtracker.R;
import com.happiness.ministry.moodtracker.Utilities.DateUtilities;

public class MainActivity extends AppCompatActivity {

    // Create the preferences object
    MoodPreferences mMoodPreferences;

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

        // Allows to erase all the preferences ( Useful for the test phase )
        //mPreferences.edit().clear().commit();

        // Restoring the preferences in a Gson Objet
        Gson gson = new Gson();
        String jsonMoodPreferences = getPreferences(MODE_PRIVATE).getString(PREF_KEY_MOOD,null);

        // If preferences are present
        if (jsonMoodPreferences != null) {
            mMoodPreferences = gson.fromJson(jsonMoodPreferences,MoodPreferences.class);

            // If the backup date of the last mood is smaller than the current date
            if ( mMoodPreferences.getLastMood().getDateSSAAMMJJ()
                    < DateUtilities.getIntDateOfDaySSAAMMJJ() ){
                // Then it is a new day
                // Calculation of the current new index
                // If we are at the end of list then the current index will be the first post of the list
                if ( ( mMoodPreferences.getLastMoodIndex() + 1 ) > 6 ) {
                    mMoodPreferences.setLastMoodIndex(0);
                }
                else {
                    mMoodPreferences.setLastMoodIndex(mMoodPreferences.getLastMoodIndex()+1);
                }

                // Create a new Mood
                Mood newMood = new Mood();

                // Adds the new Mood in the moodPreferences
                mMoodPreferences.addMoodToPreferences(mMoodPreferences.getLastMoodIndex(),newMood);
            }
        // If not preference
        // It's the first time when the application is opened because the preferences are absent
        } else {
            // Creation of a new MoodPreferences Object with one Mood by Default
            mMoodPreferences = new MoodPreferences();
        }
    }

    // Method allowing to configure the viewPager
    private void configureViewPager(){

        // Get ViewPager from layout
        ViewPager pager = findViewById(R.id.activity_main_viewpager);

        // Set Adapter PageAdapter and glue it together

        pager.setAdapter(new PageAdapter(getSupportFragmentManager()) {
        });

        // Positioning the front page on Mood by default : smiley happy
        pager.setCurrentItem(3);
    }
}
