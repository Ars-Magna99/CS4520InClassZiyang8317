/*
 * CS5520 In-class Assignment 3
 * Name: Ziyang Wang
 * Date: 2023-02-12
 */
package com.example.cs4520_inclass_ziyang8317;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DisplayProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DisplayProfileFragment extends Fragment {
    private TextView user_name;
    private TextView name_tag;
    private ImageView avatar_top;
    private TextView email;
    private  TextView email_tag;
    private TextView phone_type;
    private TextView mood_type;

    private ImageView emoji;


    private Profile user_profile;
    private final String TAG = "final";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DisplayProfileFragment() {
        // Required empty public constructor
    }

    public DisplayProfileFragment(Profile profile){
        this.user_profile = profile;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DisplayProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DisplayProfileFragment newInstance(String param1, String param2) {
        DisplayProfileFragment fragment = new DisplayProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    public void update_profile(Profile profile){
        this.user_profile = profile;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_display_profile, container, false);
        getActivity().setTitle("Display Profile");

        // match elements
        user_name = rootView.findViewById(R.id.Actual_Name_frag);
        avatar_top = rootView.findViewById(R.id.avatar_image_frag);
        email = rootView.findViewById(R.id.Actual_Email_frag);
        phone_type = rootView.findViewById(R.id.Phone_Type_frag);
        mood_type = rootView.findViewById(R.id.Mood_Type_frag);
        emoji = rootView.findViewById(R.id.mood_emoji_frag);
        name_tag = rootView.findViewById(R.id.Name_Tag_frag);
        email_tag = rootView.findViewById(R.id.Email_Tag_frag);
        // change the font
        name_tag.setTypeface(null, Typeface.BOLD);
        name_tag.setTextColor(Color.BLACK);
        name_tag.setTextSize(20);

        email_tag.setTypeface(null, Typeface.BOLD);
        email_tag.setTextColor(Color.BLACK);
        email_tag.setTextSize(20);

        phone_type.setTypeface(null, Typeface.BOLD);
        phone_type.setTextColor(Color.BLACK);
        phone_type.setTextSize(20);

        mood_type.setTypeface(null, Typeface.BOLD);
        mood_type.setTextColor(Color.BLACK);
        mood_type.setTextSize(20);

        user_name.setText(this.user_profile.name);
        avatar_top.setImageResource(this.user_profile.avatar_id);
        email.setText(this.user_profile.email);

        String phone_sentence = String.format("I use %s!",this.user_profile.phone_type);
        phone_type.setText(phone_sentence);

        String mood_sentence = String.format("I am %s!",this.user_profile.mood);
        mood_type.setText(mood_sentence);

        switch(this.user_profile.mood){
            case "Angry": emoji.setImageResource(R.drawable.angry);
                break;
            case "Sad": emoji.setImageResource(R.drawable.sad);
                break;
            case "Happy": emoji.setImageResource(R.drawable.happy);
                break;
            case "Awesome":emoji.setImageResource(R.drawable.awesome);
                break;
        }


        return rootView;
    }
}