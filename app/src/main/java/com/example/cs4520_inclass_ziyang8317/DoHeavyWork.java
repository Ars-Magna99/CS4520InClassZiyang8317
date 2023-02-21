/*
 * CS5520 In-class Assignment 4
 * Name: Ziyang Wang
 * Date: 2023-02-20
 */
package com.example.cs4520_inclass_ziyang8317;

import static com.example.cs4520_inclass_ziyang8317.HeavyWork.getNumber;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;

public class DoHeavyWork implements Runnable {
    private int task_complexity;
    private final String TAG = "final";

    // initialize a Handler in this class
    private Handler messageQueue;

    // declare a public parameter to indicate Message
    public final static int STATUS_START = 0x001;
    public final static int STATUS_PROGRESS = 0x002;
    public final static int STATUS_STOP = 0x003;
    public final static String KEY_PROGRESS = "KEY_PROGRESS";


    public DoHeavyWork(int task_complexity,Handler messageQueue) {
        this.task_complexity = task_complexity;
        Log.d(TAG, "DoHeavyWork:" + String.valueOf(this.task_complexity));
        this.messageQueue = messageQueue;
    }

    @Override
    public void run() {
        Message startMessage = new Message();
        startMessage.what = STATUS_START;
        //when the run starts, send the start message.
        messageQueue.sendMessage(startMessage);


        ArrayList<Double> returnArray = HeavyWork.getArrayNumbers(this.task_complexity,messageQueue);
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Message stopMessage = new Message();
        //when the run stops, send the stop message.
        stopMessage.what = STATUS_STOP;
        Bundle bundleToActivity = new Bundle();
        bundleToActivity.putSerializable("array",returnArray);
        stopMessage.setData(bundleToActivity);
        messageQueue.sendMessage(stopMessage);

    }
}
