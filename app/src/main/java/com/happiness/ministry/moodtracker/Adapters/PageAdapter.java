package com.happiness.ministry.moodtracker.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.happiness.ministry.moodtracker.Controllers.Fragments.PageFragment;

/**
 * Created by Michaël SAGOT on 18/03/2018.
 */

public class PageAdapter extends FragmentPagerAdapter {

    // Default Constructor
    public PageAdapter(FragmentManager mgr) {
        super(mgr);
    }

    @Override
    public int getCount() {
        return(5); // Number of page to show
    }

    @Override
    public Fragment getItem(int position) {
        // Page to return
        return(PageFragment.newInstance(position));
    }
}
