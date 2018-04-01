package com.happiness.ministry.moodtracker.Controllers.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.google.gson.Gson;
import com.happiness.ministry.moodtracker.Models.MoodPreferences;
import com.happiness.ministry.moodtracker.R;

import java.util.ArrayList;
import java.util.List;

public class CamembertActivity extends AppCompatActivity {

    private static String TAG = "MainActivity";

    // Defined String Preferences
    private String mPreferences;

    // Defined a MoodPreferences Object
    private MoodPreferences mMoodPreferences;

    PieChart pieChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camembert);

        // Get PieChart from layout
        pieChart = findViewById(R.id.activity_camembert_piechart);

        // Get back Intent send to parameter by the MainActivity
        Intent intent = getIntent();
        mPreferences = intent.getStringExtra(MainActivity.KEY_PREFERENCES);

        // Restoring the preferences with a Gson Object
        Gson gson = new Gson();
        mMoodPreferences = gson.fromJson(mPreferences,MoodPreferences.class);

        // Initialize the camembert and show it
        this.init();
    }

    private void init() {

        // Data definition
        List<PieEntry> entries = new ArrayList<>();

        int sortMood[] = {0,0,0,0,0};

        for (int i = 0; i < mMoodPreferences.getMoodHistorical().size()-1; i++) {
            sortMood[mMoodPreferences.getMoodHistorical().get(i).getMoodIndex()] +=1;
        }

        ArrayList<Integer> colors = new ArrayList<>();
        for (int i = 0; i < sortMood.length; i++) {
            if (sortMood[i] > 0){
                entries.add(new PieEntry(sortMood[i], getResources().getStringArray(R.array.array_mood_text)[i]));
                colors.add(getResources().obtainTypedArray(R.array.colorPagesViewPager).getColor(i,0));
            }
        }
        // Camembert Appearances
        camembertConfig(entries, colors);
    }

    //Method which configures the appearance of the camembert
    private void camembertConfig(List<PieEntry> entries, ArrayList<Integer> colors){

        // Appearance of the Camembert
        Description pieDescription = new Description();
        pieDescription.setText("");
        pieChart.setDescription(pieDescription);
        pieChart.setCenterText("Moods");
        pieChart.setCenterTextSize(15);
        pieChart.setTransparentCircleAlpha(0);
        pieChart.setHoleRadius(25f);
        pieChart.setDrawEntryLabels(true);
        PieDataSet pieDataSet = new PieDataSet(entries, "");
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(0);
        pieDataSet.setColors(colors);
        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.invalidate(); // refresh

        // Legend Configuration
        Legend legend = pieChart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE); // set what type of form/shape should be used
        legend.setXEntrySpace(10f); // set the space between the legend entries on the y-axis
        legend.setTextSize(12f);
    }
}

