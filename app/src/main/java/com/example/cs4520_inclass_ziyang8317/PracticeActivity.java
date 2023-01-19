package com.example.cs4520_inclass_ziyang8317;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class PracticeActivity extends AppCompatActivity {
    private Button button_log;
    private Button button_toast;
    public static String tag = "demo";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice);

        setTitle("Practice Activity");
        button_log = findViewById(R.id.button_log);
        button_toast = findViewById(R.id.button_toast);


        button_toast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(PracticeActivity.this, "Now push to GitHub and Submit!", Toast.LENGTH_LONG).show();
            }
        });

        button_log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(tag,"Practice!Practice!Practice!");
            }
        });
    }
}