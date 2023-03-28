/*
 * CS4520 InClass08
 * Name: Ziyang Wang
 * Date: 2023-03-27
 * */

package com.example.cs4520_inclass_ziyang8317;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class InClass08 extends AppCompatActivity implements InClass08LoginFragment.IloginFragmentAction,InClass08RegisterFragment.IregisterFragmentAction,InClass08StartingFragment.ImainFragmentButtonAction {
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_class08);

        setTitle("InClass 08");
        mAuth = FirebaseAuth.getInstance();

    }

    @Override
    protected void onStart() {
        super.onStart();
        currentUser = mAuth.getCurrentUser();
        populateScreen();
    }

    private void populateScreen() {
        //      Check for Authenticated users ....
        Log.d("final", "populateScreen: "+mAuth.toString());
        if(currentUser != null){
            //The user is authenticated, Populating The Main Fragment....
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainerView_InClass8, InClass08StartingFragment.newInstance(),"mainFragment")
                    .commit();

        }else{
//            The user is not logged in, load the login Fragment....
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainerView_InClass8, InClass08LoginFragment.newInstance(),"loginFragment")
                    .commit();
        }
    }

    @Override
    public void populateMainFragment(FirebaseUser mUser) {
        this.currentUser = mUser;
        populateScreen();
    }
    @Override
    public void registerDone(FirebaseUser mUser) {
        this.currentUser = mUser;
        populateScreen();
    }


    @Override
    public void populateRegisterFragment() {
//            The user needs to create an account, load the register Fragment....
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainerView_InClass8, InClass08RegisterFragment.newInstance(),"registerFragment")
                .commit();
    }




    @Override
    public void logoutPressed() {
        mAuth.signOut();
        currentUser = null;
        populateScreen();
    }

}