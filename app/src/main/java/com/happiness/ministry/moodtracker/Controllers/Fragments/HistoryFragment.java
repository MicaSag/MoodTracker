package com.happiness.ministry.moodtracker.Controllers.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.happiness.ministry.moodtracker.Models.Mood;
import com.happiness.ministry.moodtracker.Models.MoodPreferences;
import com.happiness.ministry.moodtracker.R;

/**
 * History Fragment
 */
public class HistoryFragment extends Fragment implements View.OnClickListener{

    // Defined a Mood index
    int mMoodIndex;

    // Defined a comment String
    String mComment;

    // Create the key Mood
    public static final String KEY_MOOD_INDEX = "KEY_MOOD_INDEX";
    public static final String KEY_COMMENT = "KEY_COMMENT";

    // Define the widgets variables of the Fragment
    LinearLayout fragmentHistoryLinear;
    TextView fragmentHistoryText;
    Button fragmentHistoryButton;
    LinearLayout fragmentHistoryBlank;

    public HistoryFragment() {
        // Required empty public constructor
    }

    // Method that will create a new instance of PageFragment, and add data to its bundle.
    public static HistoryFragment newInstance(Mood mood) {

        // Create new fragment
        HistoryFragment frag = new HistoryFragment();

        // Create bundle and add it some data
        Bundle args = new Bundle();
        args.putInt(KEY_MOOD_INDEX, mood.getMoodIndex());
        args.putString(KEY_COMMENT, mood.getComment());

        frag.setArguments(args);

        return(frag);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Get data from Bundle (created in method newInstance)
        mMoodIndex = getArguments().getInt(KEY_MOOD_INDEX, -1);
        mComment = getArguments().getString(KEY_COMMENT, "");
        Log.i("MOOD", "KEY_MOOD_INDEX = "+mMoodIndex);
        Log.i("MOOD", "KEY_COMMENT    = "+mComment);

        // Inflate the layout for this fragment
        View fragmentHistoryLayout = inflater.inflate(R.layout.fragment_history, container, false);

        // Get widgets from layout
        fragmentHistoryLinear = fragmentHistoryLayout.findViewById(R.id.fragment_history_linear);
        fragmentHistoryText = fragmentHistoryLayout.findViewById(R.id.fragment_history_text);
        fragmentHistoryButton = fragmentHistoryLayout.findViewById(R.id.fragment_history_btn);
        fragmentHistoryBlank = fragmentHistoryLayout.findViewById(R.id.fragment_history_blank);

        // set the listening of buttons "comment" on HistoryFragment
        fragmentHistoryLinear.findViewById(R.id.fragment_history_btn).setOnClickListener(this);

        // Initialization of the fragment
        initHistoryFragment();

        return fragmentHistoryLayout;
    }

    // Initialization of the fragment
    private void initHistoryFragment() {
        Log.i("MOOD", "_____initHistoryFragment_____");
        // Set color of the Fragment
        fragmentHistoryLinear.setBackgroundColor(getResources()
                .obtainTypedArray(R.array.colorPagesViewPager)
                .getColor(mMoodIndex,1));

        // Adjust the size of the Fragment
        LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) fragmentHistoryBlank.getLayoutParams();
        linearParams.weight = mMoodIndex +1;
        LinearLayout.LayoutParams frameParams = (LinearLayout.LayoutParams) fragmentHistoryLinear.getLayoutParams();
        frameParams.weight = 4 - mMoodIndex;


        // If no comment, hidden the button comment
        if ( mComment.equals("") ) {
            Log.i("MOOD", "HIDDEN BUTTON COMMENT");
            fragmentHistoryButton.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View view) {

        // Display the comment
        Toast.makeText(getActivity(), mComment, Toast.LENGTH_LONG).show();
    }
}

