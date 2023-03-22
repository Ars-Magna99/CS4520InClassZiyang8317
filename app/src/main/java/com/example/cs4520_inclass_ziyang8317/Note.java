package com.example.cs4520_inclass_ziyang8317;

public class Note {
    private String userId;
    private String _id;
    private String text;

    public Note(String user_id, String note_id, String note_text) {
        this.userId = user_id;
        this._id = note_id;
        this.text = note_text;
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "Note{" +
                "userId='" + userId + '\'' +
                ", _id='" + _id + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
