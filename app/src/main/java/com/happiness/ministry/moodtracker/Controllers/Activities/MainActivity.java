package com.happiness.ministry.moodtracker.Controllers.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.happiness.ministry.moodtracker.Adapters.PageAdapter;
import com.happiness.ministry.moodtracker.Controllers.Fragments.PageFragment;
import com.happiness.ministry.moodtracker.Models.Mood;
import com.happiness.ministry.moodtracker.Models.MoodPreferences;
import com.happiness.ministry.moodtracker.R;
import com.happiness.ministry.moodtracker.Utilities.DateUtilities;

import java.util.Date;

public class MainActivity extends AppCompatActivity  implements PageFragment.OnButtonClickedListener{

    // Defined Preferences of the application
    SharedPreferences mPreferences;

    // Defined a MoodPreferences Object
    MoodPreferences mMoodPreferences;

    // Defined the ViewPager use by this activity
    ViewPager pager;

    // Create the key saving of the preferences of the application
    public static final String PREF_KEY_MOOD = "PREF_KEY_MOOD";

    // Create the key Preferences
    public static final String KEY_PREFERENCES = "KEY_PREFERENCES";

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
        //Log.i("MOOD","CLEAR COMMIT PREFERENCES");
        //mPreferences.edit().clear().commit();

        // Recovered PREF_KEY_MOOD in a String Object
        String moodPreferences = mPreferences.getString(PREF_KEY_MOOD,null);

        // if preferences PREF_KEY_MOOD is present
        if (moodPreferences != null) {
            Log.i("MOOD","PREFERENCES");

            // Restoring the preferences with a Gson Object
            Gson gson = new Gson();
            mMoodPreferences = gson.fromJson(moodPreferences,MoodPreferences.class);

            Log.i("MOOD", "____START____");
            Log.i("MOOD", "Index      = "+String.valueOf(mMoodPreferences.getLastSavedMoodIndex()));
            Log.i("MOOD", "Mood Index = "+mMoodPreferences.getLastMood().getMoodIndex());
            Log.i("MOOD", "Comment    = "+mMoodPreferences.getLastMood().getComment());
            Log.i("MOOD", "Date       = "+DateUtilities.getIntDateSSAAMMJJ(mMoodPreferences.getLastMood().getDate()));

            // If the backup date of the last mood is smaller than the current date
            // Then it is a new day
            if ( DateUtilities.getIntDateSSAAMMJJ(mMoodPreferences.getLastMood().getDate())
                    < DateUtilities.getIntDateSSAAMMJJ(new Date()) ){

                Log.i("MOOD","NEW DAY");
                // If the list is already full, then we remove the first element of the list
                // And we add a new Mood by default
                if ( mMoodPreferences.getMoodHistorical().size() == 8 ) {
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
        Log.i("MOOD", "____END____");
        Log.i("MOOD", "Index        = "+String.valueOf(mMoodPreferences.getLastSavedMoodIndex()));
        Log.i("MOOD", "Mood Index   = "+mMoodPreferences.getLastMood().getMoodIndex());
        Log.i("MOOD", "Comment      = "+mMoodPreferences.getLastMood().getComment());
        Log.i("MOOD", "Date         = "+DateUtilities.getIntDateSSAAMMJJ(mMoodPreferences.getLastMood().getDate()));
    }

    // Method allowing to configure the viewPager
    private void configureViewPager(){

        // Get ViewPager from layout
        pager = findViewById(R.id.activity_main_viewpager);

        // Set Adapter PageAdapter and glue it together

        pager.setAdapter(new PageAdapter(getSupportFragmentManager()) {
        });

        // Positioning the front page on Mood by default : smiley happy
        pager.setCurrentItem(mMoodPreferences.getLastMood().getMoodIndex());
    }

    @Override
    public void onButtonClicked(View view) {
        // Here, thanks to the callback implemented in the PageFragment
        // We are going to manage the click on the various buttons of PageFragment
        switch (view.getId()) {

            // History Button Clicked
            case R.id.fragment_page_history_btn :
            // Camembert Button Clicked
            case R.id.fragment_page_camembert_btn :

                // If a history is present
                Log.i("MOOD", "SIZE ="+mMoodPreferences.getMoodHistorical().size());
                if (mMoodPreferences.getMoodHistorical().size() > 1) {
                    Log.i("MOOD", "SIZE > 1");
                    // Create à PREF_KEY_MOOD String with a Gson Object
                    final Gson gson = new GsonBuilder()
                            .serializeNulls()
                            .disableHtmlEscaping()
                            .create();
                    String json = gson.toJson(mMoodPreferences);

                    Intent intentActivity = null;

                    // Call activity HistoryActivity
                    Log.i("MOOD", "Click = history");
                    if (view.getId() == R.id.fragment_page_history_btn) {
                        intentActivity = new Intent(MainActivity.this, HistoryActivity.class);
                    }

                    // Call activity CamembertActivity
                    Log.i("MOOD", "Click = camembert");
                    if (view.getId() == R.id.fragment_page_camembert_btn) {
                        intentActivity = new Intent(MainActivity.this, CamembertActivity.class);
                    }
                    intentActivity.putExtra(KEY_PREFERENCES, json);
                    startActivity(intentActivity);
                }
                // Not present history
                else{
                    Toast toast = Toast.makeText(this, "Not still of present history", Toast.LENGTH_SHORT);
                    toast.show();
                }
                break;

            // Comment Button Clicked : call comment dialog Box
            case R.id.fragment_page_comment_btn :
                Log.i("MOOD", "Click = comment");

                // Create, call and manage the Comment Dialog
                manageCommentDialog();
                break;
        }
    }

    // Create and manage the Comment Dialog
    private void manageCommentDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Commentaire");

        // Get the layout inflater of the MainActivity
        LayoutInflater inflater = this.getLayoutInflater();

        // Inflate the layout of the dialog comment
        View dialogCommentView = inflater.inflate(R.layout.dialog_comment, null);

        // Retrieves the input field of the dialog_comment_input
        final EditText input = dialogCommentView.findViewById(R.id.dialog_comment_input);

        // Link the AlertDialog at the view dialogCommentView
        builder.setView(dialogCommentView);

        // Set up the buttons
        // Validate Button
        builder.setPositiveButton("VALIDER", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                // Save comment into mood preferences
                mMoodPreferences.getLastMood().setComment(input.getText().toString());
            }
        });
        // Cancel Button
        builder.setNegativeButton("ANNULER", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("MOOD","status MainActivity = paused");

        // Change the day Mood in MoodPreference
        mMoodPreferences.getLastMood().setMoodIndex(pager.getCurrentItem());

        Log.i("MOOD","Tab Index = "+ String.valueOf(mMoodPreferences.getLastSavedMoodIndex()));
        Log.i("MOOD","Type Mood = "+ mMoodPreferences.getLastMood().getMoodIndex());
        Log.i("MOOD","Comment   = "+ mMoodPreferences.getLastMood().getComment());
        Log.i("MOOD","Date      = "+ String.valueOf(DateUtilities.getIntDateSSAAMMJJ(mMoodPreferences.getLastMood().getDate())));

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
