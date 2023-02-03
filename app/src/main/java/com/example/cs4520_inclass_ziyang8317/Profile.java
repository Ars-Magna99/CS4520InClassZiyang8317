/*
 * CS5520 In-class Assignment 2
 * Name: Ziyang Wang
 * Date: 2023-02-01
 */

package com.example.cs4520_inclass_ziyang8317;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Profile implements Parcelable {
    String name;
    String email;
    String phone_type;
    String mood;
    int avatar_id;

    public Profile(String name,String email,String phone_type,String mood,int avatar_id){
        this.name = name;
        this.email = email;
        this.phone_type = phone_type;
        this.mood = mood;
        this.avatar_id = avatar_id;
    }

    protected Profile(Parcel in) {
        name = in.readString();
        email = in.readString();
        phone_type = in.readString();
        mood = in.readString();
        avatar_id = in.readInt();
    }

    public static final Creator<Profile> CREATOR = new Creator<Profile>() {
        @Override
        public Profile createFromParcel(Parcel in) {
            return new Profile(in);
        }

        @Override
        public Profile[] newArray(int size) {
            return new Profile[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(email);
        parcel.writeString(phone_type);
        parcel.writeString(mood);
        parcel.writeInt(avatar_id);

    }
}
