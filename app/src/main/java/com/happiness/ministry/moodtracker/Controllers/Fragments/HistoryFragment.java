package com.happiness.ministry.moodtracker.Controllers.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.happiness.ministry.moodtracker.Models.Mood;
import com.happiness.ministry.moodtracker.Models.MoodPreferences;
import com.happiness.ministry.moodtracker.R;
import com.happiness.ministry.moodtracker.Utilities.DateUtilities;

import java.util.Date;

import static com.happiness.ministry.moodtracker.Utilities.DateUtilities.getDaysBetweenTwoDates;

/**
 * History Fragment
 */
public class HistoryFragment extends Fragment implements View.OnClickListener{

    private int mMoodIndex;             // Defined a Mood index
    private Date mDate;                 // Defined a Date
    private String mComment;            // Defined a comment String

    // Define the widgets variables of the Fragment
    private LinearLayout fragmentHistoryLinear;
    private TextView fragmentHistoryText;
    private Button fragmentHistoryButton;
    private LinearLayout fragmentHistoryBlank;

    // Create the key Mood
    public static final String KEY_MOOD_INDEX = "KEY_MOOD_INDEX";
    public static final String KEY_COMMENT = "KEY_COMMENT";
    public static final String KEY_DATE = "KEY_DATE";

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
        final Gson gson = new GsonBuilder()
                .serializeNulls()
                .disableHtmlEscaping()
                .create();
        String json = gson.toJson(mood.getDate());
        args.putString(KEY_DATE, json);
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
        // Restoring the Date with a Gson Object
        Gson gson = new Gson();
        mDate = gson.fromJson(getArguments().getString(KEY_DATE, ""),Date.class);

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
                .getColor(mMoodIndex, 1));

        // Adjust the size of the Fragment
        LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) fragmentHistoryBlank.getLayoutParams();
        linearParams.weight = mMoodIndex + 1;
        LinearLayout.LayoutParams frameParams = (LinearLayout.LayoutParams) fragmentHistoryLinear.getLayoutParams();
        frameParams.weight = 4 - mMoodIndex;


        // If no comment, hidden the button comment
        if (mComment.equals("")) {
            Log.i("MOOD", "HIDDEN BUTTON COMMENT");
            fragmentHistoryButton.setVisibility(View.GONE);
        }

        // Calculate the difference between the current date and the save date in number of Days
        int deltaDate = getDaysBetweenTwoDates(new Date(),mDate);
        Log.i("MOOD", "deltaDate = "+deltaDate);

        switch (deltaDate) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
                // Get back the history comment which must be put on the fragment
                String historyComment = getResources().getStringArray(R.array.array_history_comment)[deltaDate];
                // Put the history comment on the fragment
                fragmentHistoryText.setText(historyComment);
                break;
            default:
                // More than a week
                if (deltaDate > 7 && deltaDate < 30) {
                    // Get back the history comment which must be put on the fragment
                    // and put it on the fragment
                    fragmentHistoryText.setText(getResources().getStringArray(R.array.array_history_comment)[8]);
                }
                // More than a month
                if (deltaDate > 30) {
                    // Get back the history comment which must be put on the fragment
                    // and put it on the fragment
                    fragmentHistoryText.setText(getResources().getStringArray(R.array.array_history_comment)[9]);
                }
        }
    }

    @Override
    public void onClick(View view) {

        // Display the comment
        Toast.makeText(getActivity(), mComment, Toast.LENGTH_LONG).show();
    }
}

