/*
 * CS5520 In-class Assignment 2
 * Name: Ziyang Wang
 * Date: 2023-02-01
 */

package com.example.cs4520_inclass_ziyang8317;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class SelectAvatar extends AppCompatActivity {
    private TextView reminder;

    private ImageView avatar_f1;
    private ImageView avatar_f2;
    private ImageView avatar_f3;
    private ImageView avatar_m1;
    private ImageView avatar_m2;
    private ImageView avatar_m3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_avatar);

        // set the title of this activity
        setTitle("Select Avatar");

        // change the font of reminder
        reminder = findViewById(R.id.select_avatar_reminder_frag);
        reminder.setTypeface(null, Typeface.BOLD);
        reminder.setTextColor(Color.BLACK);
        reminder.setTextSize(20);

        // set the onclick listener for image buttons.
        avatar_f1 = findViewById(R.id.avatar_f1_frag);
        avatar_f1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SelectAvatar.this,InClass02.class);
                i.putExtra("resId",R.drawable.avatar_f_1);
                setResult(RESULT_OK,i);
                finish();

            }
        });

        avatar_f2 = findViewById(R.id.avatar_f2_frag);
        avatar_f2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SelectAvatar.this,InClass02.class);
                i.putExtra("resId",R.drawable.avatar_f_2);
                setResult(RESULT_OK,i);
                finish();

            }
        });

        avatar_f3 = findViewById(R.id.avatar_f3_frag);
        avatar_f3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SelectAvatar.this,InClass02.class);
                i.putExtra("resId",R.drawable.avatar_f_3);
                setResult(RESULT_OK,i);
                finish();

            }
        });

        avatar_m1 = findViewById(R.id.avatar_m1_frag);
        avatar_m1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SelectAvatar.this,InClass02.class);
                i.putExtra("resId",R.drawable.avatar_m_3);
                setResult(RESULT_OK,i);
                finish();
            }
        });

        avatar_m2 = findViewById(R.id.avatar_m2_frag);
        avatar_m2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SelectAvatar.this,InClass02.class);
                i.putExtra("resId",R.drawable.avatar_m_2);
                setResult(RESULT_OK,i);
                finish();

            }
        });

        avatar_m3 = findViewById(R.id.avatar_m3_frag);
        avatar_m3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SelectAvatar.this,InClass02.class);
                i.putExtra("resId",R.drawable.avatar_m_1);
                setResult(RESULT_OK,i);
                finish();

            }
        });




    }
}