/*
 * CS5520 In-class Assignment 4
 * Name: Ziyang Wang
 * Date: 2023-02-20
 */

package com.example.cs4520_inclass_ziyang8317;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


public class InClass04 extends AppCompatActivity {
    private final String TAG = "final";
    private TextView selectComplexityReminder;
    private TextView complexitySelected;
    private TextView minimumTitle;
    private TextView minimumValue;
    private TextView maximumTitle;
    private TextView maximumValue;
    private TextView averageTitle;
    private TextView averageValue;
    private TextView progressPercent;

    private SeekBar seekBar;
    private Button generateNum;
    private ProgressBar progressBar;

    //declare the ExecutorService object
    private ExecutorService threadPool;
    //use handler to process message from threads indiating status of the thread
    private Handler messageQueue;

    public void updateWithSeekbarComplexity(int complexity){
        complexitySelected.setText(String.valueOf(complexity)+" times");

    }

    public double findMin(ArrayList<Double> array){
        return Collections.min(array);
    }

    public double findMax(ArrayList<Double> array){
        return Collections.max(array);
    }

    public double findAvg(ArrayList<Double> input){
        double total = 0;
        double avg = 0;
        for(int i = 0; i < input.size(); i++)
            total += input.get(i);
        avg = total / input.size(); // finding ther average value
        return avg;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_class04);

        //Set title of the activity
        setTitle("Number Generator");

        /** match the elements on the screen and change fonts **/

        generateNum = findViewById(R.id.buttonGenerateNum);
        generateNum.setBackgroundColor(Color.GRAY);
        generateNum.setTextColor(Color.WHITE);

        seekBar = findViewById(R.id.seekBarComplexity);

        //set listeners for the seek bar
        complexitySelected = findViewById(R.id.complexitySelected);
        complexitySelected.setText("1 times");



        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                int trueComplexity = i + 1;
                updateWithSeekbarComplexity(trueComplexity);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        generateNum = findViewById(R.id.buttonGenerateNum);
        // create a thread pool that has a limit of 1 thread.
        threadPool = Executors.newFixedThreadPool(1);

        progressPercent = findViewById(R.id.progressPercent);
        progressBar = findViewById(R.id.progressBar);
        averageValue = findViewById(R.id.averageValue);
        minimumValue = findViewById(R.id.minimumValue);
        maximumValue = findViewById(R.id.maximumValue);


        messageQueue = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message message) {
                switch (message.what){
                    // this case means the thread started normally.
                    case DoHeavyWork.STATUS_START:
                        progressBar.setProgress(0);
                        progressBar.setVisibility(View.VISIBLE);
                        progressPercent.setText("0%");

                        break;
                    // this case means that the thread is done and the array is ready.
                    case DoHeavyWork.STATUS_STOP:
                        Bundle receivedArray = message.getData();
                        ArrayList<Double> result = (ArrayList<Double>) receivedArray.getSerializable("array");
                        double min = findMin(result);
                        double max = findMax(result);
                        double avg = findAvg(result);
                        Log.d(TAG,"Min is "+String.valueOf(min));
                        minimumValue.setText(String.valueOf(min));
                        maximumValue.setText(String.valueOf(max));
                        averageValue.setText(String.valueOf(avg));
                        //hide the seek bar after ended
                        progressBar.setVisibility(View.INVISIBLE);
                        progressPercent.setText("");
                        break;
                    // this case means the thread is moving in progress.
                    case DoHeavyWork.STATUS_PROGRESS:
                        Bundle receivedData = message.getData();
                        int donePercent = (int)(receivedData.getDouble(DoHeavyWork.KEY_PROGRESS) * 100.0);
                        Log.d(TAG,"Percent: "+String.valueOf(donePercent));
                        progressBar.setProgress(donePercent);
                        progressPercent.setText(String.valueOf(donePercent) + "%");
                        break;
                }
                return false;
            }
        });



        generateNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // store the complexity value in an integer.
                int complexity = seekBar.getProgress() + 1;
                threadPool.execute(new DoHeavyWork(complexity,messageQueue));

            }
        });



    }
}