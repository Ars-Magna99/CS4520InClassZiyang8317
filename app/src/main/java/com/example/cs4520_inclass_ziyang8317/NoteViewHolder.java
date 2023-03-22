package com.example.cs4520_inclass_ziyang8317;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NoteViewHolder extends RecyclerView.ViewHolder {
    protected TextView user_id;
    protected TextView note_id;
    protected TextView note_text;
    protected Button delete_button;

    public NoteViewHolder(@NonNull View itemView) {
        super(itemView);
        user_id = itemView.findViewById(R.id.InClass07_notes_user_id);
        note_id = itemView.findViewById(R.id.InClass07_notes_note_id);
        note_text = itemView.findViewById(R.id.InClass07_notes_note_text);
        delete_button = itemView.findViewById(R.id.InClass07_notes_delete_button);
    }
}
