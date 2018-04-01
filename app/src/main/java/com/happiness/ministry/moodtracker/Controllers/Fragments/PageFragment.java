package com.happiness.ministry.moodtracker.Controllers.Fragments;


import android.content.Context;
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
public class PageFragment extends Fragment  implements View.OnClickListener {

    // Create keys for the Bundle of the fragment
    private static final String KEY_POSITION="position";

    // ===>> CALL BACK <====
    //Declare a callback
    private OnButtonClickedListener mCallback;

    //Declare our interface that will be implemented by any container activity
    public interface OnButtonClickedListener {
        public void onButtonClicked(View view);
    }

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
        ImageView imageView = fragmentPageLayout.findViewById(R.id.fragment_page_smiley_img);

        // Get data from Bundle (created in method newInstance)
        int position = getArguments().getInt(KEY_POSITION, -1);

        // Update widgets with it
        // Set Background Color of the Mood
        rootView.setBackground(getResources().obtainTypedArray(R.array.colorPagesViewPager).getDrawable(position));
        // Set Image of the Mood
        imageView.setImageDrawable(getResources().obtainTypedArray(R.array.array_smileys).getDrawable(position));

        // set the listening of buttons "comment", "history" and "camembert" on the PageFragment
        fragmentPageLayout.findViewById(R.id.fragment_page_comment_btn).setOnClickListener(this);
        fragmentPageLayout.findViewById(R.id.fragment_page_history_btn).setOnClickListener(this);
        fragmentPageLayout.findViewById(R.id.fragment_page_camembert_btn).setOnClickListener(this);


        return fragmentPageLayout;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // Creating callback after being attached to parent activity
        try {
            //Parent activity will automatically subscribe to callback
            mCallback = (OnButtonClickedListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(e.toString()+ " must implement OnButtonClickedListener");
        }
    }

    @Override
    public void onClick(View view) {

        // Spreads the click to the parent activity
        mCallback.onButtonClicked(view);
    }
}
