package com.happiness.ministry.moodtracker.Controllers.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.happiness.ministry.moodtracker.R;

/**
 * PageFragment class
 */
public class PageFragment extends Fragment {

    // Create keys for the Bundle of the fragment
    private static final String KEY_POSITION="position";

    public PageFragment() {
        // Required empty public constructor
    }

    // Method that will create a new instance of PageFragment, and add data to its bundle.
    public static PageFragment newInstance(int position) {

        // Create new fragment
        PageFragment frag = new PageFragment();

        // Create bundle and add it some data
        Bundle args = new Bundle();
        args.putInt(KEY_POSITION, position);

        frag.setArguments(args);

        return(frag);
    }

    @Override
    // Called as soon as a fragment asks to be shown
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View fragmentPageLayout = inflater.inflate(R.layout.fragment_page, container, false);

        // Get widgets from layout
        FrameLayout rootView = fragmentPageLayout.findViewById(R.id.fragment_page_rootview);
        ImageView imageView = fragmentPageLayout.findViewById(R.id.fragment_page_smiley);

        // Get data from Bundle (created in method newInstance)
        int position = getArguments().getInt(KEY_POSITION, -1);

        // Update widgets with it
        rootView.setBackground(getResources().obtainTypedArray(R.array.colorPagesViewPager).getDrawable(position));
        imageView.setImageDrawable(getResources().obtainTypedArray(R.array.array_smileys).getDrawable(position));

        return fragmentPageLayout;
    }

}
