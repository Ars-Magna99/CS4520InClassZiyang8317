package com.example.cs4520_inclass_ziyang8317;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class InClass07 extends AppCompatActivity {
    private String TAG = "final";
    private String login_token;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_class07);

        setTitle("InClass07");

        InClass07StartingFragment startScreen = (InClass07StartingFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView_InClass7);

    }
}