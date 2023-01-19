package com.example.cs4520_inclass_ziyang8317;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button buttonPractice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Match the variable with the button and set OnClick listener.
        buttonPractice = findViewById(R.id.PracticeButton);
        buttonPractice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toPracticeActivity = new Intent(MainActivity.this,PracticeActivity.class);
                startActivity(toPracticeActivity);
            }
        });
    }
}