package com.happiness.ministry.moodtracker.Controllers.Activities;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.happiness.ministry.moodtracker.Adapters.PageAdapter;
import com.happiness.ministry.moodtracker.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Configure ViewPager
        this.configureViewPager();
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
