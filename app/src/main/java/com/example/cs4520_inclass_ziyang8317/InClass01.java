/*
 * CS5520 In-class Assignment 1
 * Name: Ziyang Wang
 */

package com.example.cs4520_inclass_ziyang8317;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class InClass01 extends AppCompatActivity {
    private Button button_calculate;
    private EditText weight_input;
    private EditText feet_input;
    private EditText inches_input;


    private TextView textResult;
    private TextView BMIStatus;
    public static String tag = "demo";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_class01);

        setTitle("BMI Calculator");
        // change the font of textview
        TextView BMI_title = (TextView) findViewById(R.id.BMI_Title);
        BMI_title.setTypeface(null, Typeface.BOLD);
        BMI_title.setTextColor(Color.BLACK);
        BMI_title.setTextSize(20);

        TextView weight_title = (TextView) findViewById(R.id.weight_Title);
        weight_title.setTypeface(null, Typeface.BOLD);
        weight_title.setTextColor(Color.BLACK);
        weight_title.setTextSize(20);

        TextView height_title = (TextView) findViewById(R.id.height_Title);
        height_title.setTypeface(null, Typeface.BOLD);
        height_title.setTextColor(Color.BLACK);
        height_title.setTextSize(20);

        TextView weight_unit = (TextView) findViewById(R.id.weight_unit);
        weight_unit.setTypeface(null, Typeface.BOLD);
        weight_unit.setTextColor(Color.BLACK);
        weight_unit.setTextSize(19);

        TextView feet_unit = (TextView) findViewById(R.id.feet_unit);
        feet_unit.setTypeface(null, Typeface.BOLD);
        feet_unit.setTextColor(Color.BLACK);
        feet_unit.setTextSize(19);

        TextView inches_unit = (TextView) findViewById(R.id.inches_unit);
        inches_unit.setTypeface(null, Typeface.BOLD);
        inches_unit.setTextColor(Color.BLACK);
        inches_unit.setTextSize(19);

        TextView textResult = (TextView) findViewById(R.id.textResult);
        textResult.setTypeface(null, Typeface.BOLD);
        textResult.setTextColor(Color.BLACK);
        textResult.setTextSize(12);

        TextView BMIStatus = (TextView) findViewById(R.id.BMIStatus);
        BMIStatus.setTypeface(null, Typeface.BOLD);
        BMIStatus.setTextColor(Color.BLACK);
        BMIStatus.setTextSize(12);

        // set onclicker to the button
        button_calculate = findViewById(R.id.button_calculate);
        button_calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // get the input values from the editText fields.
                weight_input = findViewById(R.id.weight_input);
                feet_input = findViewById(R.id.feet_input);
                inches_input = findViewById(R.id.inches_input);

                String weight_string = weight_input.getText().toString();
                String feet_string = feet_input.getText().toString();
                String inches_string = inches_input.getText().toString();

                Log.d(tag,weight_string.getClass().toString());
                if(weight_string.equals("")||feet_string .equals("")||inches_string.equals("")) {
                    Toast.makeText(InClass01.this, "Please do not leave any empty field!", Toast.LENGTH_LONG).show();
                    return;
                }

                // check if any input is empty.
                Float weight = Float.valueOf(weight_string);
                Float feet = Float.valueOf(feet_string);
                Float inches = Float.valueOf(inches_string);

                // calculate the BMI value.
                inches = feet * 12 + inches;
                if (weight == 0){
                    Toast.makeText(InClass01.this, "Please enter a valid weight!", Toast.LENGTH_LONG).show();

                }
                else if(inches <= 0){
                    Toast.makeText(InClass01.this, "Please enter a valid height!", Toast.LENGTH_LONG).show();
                } else {
                    Float BMI = (weight / (inches * inches)) * 703;
                    textResult.setText("Your BMI: " + String.format("%.1f", BMI));
                    if(BMI < 18.5){
                        BMIStatus.setText("You are Underweight.");
                    } else if(BMI >= 18.5 && BMI <= 24.9){
                        BMIStatus.setText("You are Normal weight.");
                    } else if (BMI >= 25 && BMI <= 29.9){
                        BMIStatus.setText("You are Overweight");
                    } else{
                        BMIStatus.setText("You are Obese");
                    }

                }

            }
        });




    }
}