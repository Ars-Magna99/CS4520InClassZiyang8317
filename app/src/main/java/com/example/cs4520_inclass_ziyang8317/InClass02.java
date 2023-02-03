/*
 * CS5520 In-class Assignment 2
 * Name: Ziyang Wang
 * Date: 2023-02-01
 */

package com.example.cs4520_inclass_ziyang8317;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.Image;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InClass02 extends AppCompatActivity {
    private SeekBar seekBar_mood;
    private TextView mood_result;
    private ImageView image_mood;
    private ImageView select_avatar;

    private EditText name;
    private EditText email;
    private TextView mood_reminder;
    private TextView IUse;
    private RadioGroup phone_type;
    private Button submit;

    private int resid = -1;

    final static String profile_key = "fromInClass02ToDisplay";

    ActivityResultLauncher<Intent> startActivityForAvatar = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if(result.getResultCode() == RESULT_OK && result.getData() != null){
                resid = result.getData().getIntExtra("resId",R.drawable.avatar_f_1);
                select_avatar.setImageResource(resid);
            }
        }
    });

    /**
     * This function will check if the given string is a valid email address.
     * @param email a string.
     * @return boolean value indicating if the string is a valid email address.
     */
    public boolean isEmailValid(String email)
    {
        final String EMAIL_PATTERN =
                "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        final Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        final Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_class02);

        // set the title of the activity
        setTitle("Edit Profile Activity");

        //match the element of showing the mood.
        IUse = findViewById(R.id.IUse);
        IUse.setTypeface(null,Typeface.BOLD);
        IUse.setTextColor(Color.BLACK);
        IUse.setTextSize(20);


        mood_reminder = findViewById(R.id.mood_reminder);
        mood_reminder.setTypeface(null,Typeface.BOLD);
        mood_reminder.setTextColor(Color.BLACK);
        mood_reminder.setTextSize(20);

        mood_result = findViewById(R.id.mood_result);
        mood_result.setTypeface(null, Typeface.BOLD);
        mood_result.setTextColor(Color.BLACK);
        mood_result.setTextSize(20);


        // change the font of some textviews


        //match the element of selecting the avatar image
        select_avatar = findViewById(R.id.SelectAvatar);
        select_avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toAvatarActivity = new Intent(InClass02.this,SelectAvatar.class);
                //startActivity(toAvatarActivity);
                startActivityForAvatar.launch(toAvatarActivity);
            }
        });

        seekBar_mood = findViewById(R.id.seekBar_mood);
        image_mood = findViewById(R.id.image_mood);
        seekBar_mood.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if(i == 1){
                    mood_result.setText("Sad");
                    image_mood.setImageResource(R.drawable.sad);
                }else if(i == 2){
                    mood_result.setText("Happy");
                    image_mood.setImageResource(R.drawable.happy);

                }else if(i == 3){
                    mood_result.setText("Awesome");
                    image_mood.setImageResource(R.drawable.awesome);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        // receive image data from select avatar activity and update the image in the middle.
        /**
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            resid = bundle.getInt("resId");
            select_avatar.setImageResource(resid);
        }**/

        // receive the data from other fields
        name = findViewById(R.id.Name_enter);
        email = findViewById(R.id.Email_Enter);
        phone_type = findViewById(R.id.radioGroup_phone);
        submit = findViewById(R.id.button_submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user_name = name.getText().toString();
                String user_email = email.getText().toString();

                if(user_name.equals("") || user_email.equals("") ||phone_type.getCheckedRadioButtonId() == -1){
                    Toast.makeText(InClass02.this, "Please do not leave any empty field before submission!", Toast.LENGTH_LONG).show();
                } else if(resid == -1){
                    Toast.makeText(InClass02.this, "Please select your avatar before submission!", Toast.LENGTH_LONG).show();
                } else if(isEmailValid(user_email) != true){
                    Toast.makeText(InClass02.this, "Please enter a valid email address!", Toast.LENGTH_LONG).show();
                }
                else {
                    // get the text from the radio group.
                    int checked_id=phone_type.getCheckedRadioButtonId();
                    RadioButton rb =(RadioButton) findViewById(checked_id);
                    String radioText=rb.getText().toString();


                    // get the value from seek bar
                    int mood_level = seekBar_mood.getProgress();
                    String mood = "Angry";
                    switch (mood_level){
                        case 0: mood = "Angry";
                            break;
                        case 1: mood = "Sad";
                            break;
                        case 2: mood = "Happy";
                            break;
                        case 3: mood = "Awesome";
                            break;
                    }

                    Intent i = new Intent(InClass02.this,DisplayProfile.class);
                    Profile np = new Profile(user_name,user_email,radioText,mood,resid);
                    i.putExtra(profile_key,np);



                    // start intent
                    startActivity(i);

                }

            }
        });


    }

}