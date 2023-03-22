/*
 * CS5520 In class assignment 07
 * Name: Ziyang Wang
 * Date: 2023-03-20
 * */


package com.example.cs4520_inclass_ziyang8317;

import java.util.ArrayList;

public class Notes {
    private ArrayList<Note> notes = new ArrayList<Note>();

    public ArrayList<Note> getNotes() {
        return notes;
    }

    public void setNotes(ArrayList<Note> notes) {
        this.notes = notes;
    }

    public void add_note(Note note){
        this.notes.add(note);
    }


    @Override
    public String toString() {
        return "Notes{" +
                "notes=" + notes +
                '}';
    }
}
