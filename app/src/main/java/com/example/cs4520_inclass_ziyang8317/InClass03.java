/*
 * CS5520 In-class Assignment 3
 * Name: Ziyang Wang
 * Date: 2023-02-12
 */

package com.example.cs4520_inclass_ziyang8317;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class InClass03 extends AppCompatActivity implements SelectAvatarFragment.IfromFragmentToActivity,EditProfileFragment.IfromEditProfileToActivity {
    private String TAG = "final";
    private int avatar_id = R.drawable.select_avatar;
    private Profile user_profile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_class03);

        // send the id of the avatar into the EditProfileFragment
        EditProfileFragment editProfile = (EditProfileFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView);
        if(editProfile != null){
            editProfile.update_avatar_id(avatar_id);
        } else {

        }

        DisplayProfileFragment displayProfile = (DisplayProfileFragment) getSupportFragmentManager().findFragmentByTag("display_profile");
        if(displayProfile != null){
            Log.d(TAG,"displayProfile is found!");
            displayProfile.update_profile(this.user_profile);
        }else{
        }

    }

    @Override
    public void fromFragment(int avatar_id) {
        this.avatar_id = avatar_id;
    }

    // receive the Profile object passed from the EditProfile Fragment
    @Override
    public void fromFragment(Profile user_profile) {
        this.user_profile = user_profile;
    }
}