/*
 * CS5520 In-class Assignment 1
 * Name: Ziyang Wang
 */
package com.example.cs4520_inclass_ziyang8317;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button buttonPractice;
    private Button buttonInClass01;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Match the variable with the button and set OnClick listener.
        buttonPractice = findViewById(R.id.PracticeButton);
        buttonInClass01 = findViewById(R.id.button_class1);
        buttonPractice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toPracticeActivity = new Intent(MainActivity.this,PracticeActivity.class);
                startActivity(toPracticeActivity);
            }
        });

        buttonInClass01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toInClass01 = new Intent(MainActivity.this,InClass01.class);
                startActivity(toInClass01);
            }
        });
    }
}