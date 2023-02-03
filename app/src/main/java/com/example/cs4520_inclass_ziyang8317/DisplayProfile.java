/*
 * CS5520 In-class Assignment 2
 * Name: Ziyang Wang
 * Date: 2023-02-01
 */
package com.example.cs4520_inclass_ziyang8317;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class DisplayProfile extends AppCompatActivity {
    private TextView user_name;
    private TextView name_tag;
    private ImageView avatar_top;
    private TextView email;
    private  TextView email_tag;
    private TextView phone_type;
    private TextView mood_type;

    private ImageView emoji;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_profile);

        //set the title
        setTitle("Display Activity");


        // match elements
        user_name = findViewById(R.id.Actual_Name);
        avatar_top = findViewById(R.id.avatar_image);
        email = findViewById(R.id.Actual_Email);
        phone_type = findViewById(R.id.Phone_Type);
        mood_type = findViewById(R.id.Mood_Type);
        emoji = findViewById(R.id.mood_emoji);
        name_tag = findViewById(R.id.Name_Tag);
        email_tag = findViewById(R.id.Email_Tag);
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




        //get data from last activity
        if(getIntent() != null && getIntent().getExtras() != null){
            Profile new_profile = getIntent().getParcelableExtra(InClass02.profile_key);
            user_name.setText(new_profile.name);
            avatar_top.setImageResource(new_profile.avatar_id);
            email.setText(new_profile.email);

            String phone_sentence = String.format("I use %s!",new_profile.phone_type);
            phone_type.setText(phone_sentence);

            String mood_sentence = String.format("I am %s!",new_profile.mood);
            mood_type.setText(mood_sentence);

            switch(new_profile.mood){
                case "Angry": emoji.setImageResource(R.drawable.angry);
                break;
                case "Sad": emoji.setImageResource(R.drawable.sad);
                break;
                case "Happy": emoji.setImageResource(R.drawable.happy);
                break;
                case "Awesome":emoji.setImageResource(R.drawable.awesome);
                break;
            }

        }

    }
}