package com.example.cs4520_inclass_ziyang8317;

import android.app.Activity;
import android.content.Context;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.logging.Handler;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class NoteAdapter extends RecyclerView.Adapter<NoteViewHolder>{
    private static final String TAG = "demo";

    public void setCurr_Token(String curr_Token) {
        this.curr_Token = curr_Token;
    }

    private String curr_Token;
    private OkHttpClient client;
    Context context;
    ArrayList<Note> notes = null;

    public void setT(Activity t) {
        this.t = t;
    }

    private Activity t;

    public NoteAdapter(Context context, ArrayList<Note> notes) {
        this.context = context;
        this.notes = notes;
    }




    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NoteViewHolder(LayoutInflater.from(context).inflate(R.layout.inclass07_note_cardview,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        client = new OkHttpClient();
        Note curNote = notes.get(position);
        holder.user_id.setText(curNote.getUserId());
        holder.note_id.setText(curNote.get_id());
        holder.note_text.setText(curNote.getText());
        holder.delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String postData = null;
                try {
                    postData = "id=" + URLEncoder.encode(curNote.get_id(), "UTF-8");
                    RequestBody requestBody = RequestBody.create(postData, MediaType.parse("application/x-www-form-urlencoded"));
                    Request req_del = new Request.Builder()
                            .url("http://ec2-54-164-201-39.compute-1.amazonaws.com:3000/api/note/delete")
                            .addHeader("x-access-token",curr_Token)
                            .post(requestBody)
                            .build();
                    client.newCall(req_del).enqueue(new Callback() {
                        @Override
                        public void onFailure(@NonNull Call call, @NonNull IOException e) {
                            e.printStackTrace();
                        }

                        @Override
                        public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                            if(response.isSuccessful()){
                                Log.d(TAG, "Delete clicked on: "+ notes.get(holder.getAdapterPosition()).toString());
                                notes.remove(holder.getAdapterPosition());
                                t.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(t,"Successfully deleted a note!",Toast.LENGTH_SHORT).show();
                                        notifyDataSetChanged();
                                    }
                                });

                            }

                        }
                    });
                } catch (UnsupportedEncodingException e) {
                    throw new RuntimeException(e);
                }
                notifyDataSetChanged();



            }
        });


    }

    @Override
    public int getItemCount() {
        return notes.size();
    }



}
