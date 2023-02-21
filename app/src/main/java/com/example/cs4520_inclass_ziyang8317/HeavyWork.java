package com.example.cs4520_inclass_ziyang8317;

import static com.example.cs4520_inclass_ziyang8317.DoHeavyWork.STATUS_PROGRESS;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
/*
 * CS5520 In-class Assignment 4
 * Name: Ziyang Wang
 * Date: 2023-02-20
 */
public class HeavyWork {
    static final int COUNT = 9000000;
    public final static String KEY_PROGRESS = "KEY_PROGRESS";
    private static String TAG = "final";

    static ArrayList<Double> getArrayNumbers(int n, Handler messageQueue){
        ArrayList<Double> returnArray = new ArrayList<>();

        for (int i=0; i<n; i++){
            //add the random number into the array.
            returnArray.add(getNumber());
            //send back the progress indicator.
            Message progressMessage = new Message();
            Bundle bundle = new Bundle();
            double double_complexity = (double)(n);
            double percentage = ((double)(i+1))/double_complexity;
            Log.d(TAG,"In thread:"+String.valueOf(percentage)+"%");
            bundle.putDouble(KEY_PROGRESS,percentage);
            progressMessage.what = DoHeavyWork.STATUS_PROGRESS;
            progressMessage.setData(bundle);
            messageQueue.sendMessage(progressMessage);
        }

        return returnArray;
    }

    static double getNumber(){
        double num = 0;
        Random ran = new Random();
        for(int i=0;i<COUNT; i++){
            num = num + (Math.random()*ran.nextDouble()*100+ran.nextInt(50))*1000;
        }
        return num / ((double) COUNT);
    }

    // public static void main(String[] args) {
    //     ArrayList<Double> arrayList = new ArrayList<>();
    //     arrayList = getArrayNumbers(200);
    //     for(double num: arrayList){
    //         System.out.println(num);
    //     }
    // }
}