/*
 * CS4520 InClass08
 * Name: Ziyang Wang
 * Date: 2023-03-27
 * */

package com.example.cs4520_inclass_ziyang8317;

public class ReadWriteUserDetails {
    public String uid,first_name,last_name,display_name,email;

    public ReadWriteUserDetails(String uid,String first_name, String last_name, String display_name, String email) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.display_name = display_name;
        this.email = email;
        this.uid = uid;
    }

    public ReadWriteUserDetails(){

    }
}
