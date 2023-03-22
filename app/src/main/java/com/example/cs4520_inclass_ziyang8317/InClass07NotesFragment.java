package com.example.cs4520_inclass_ziyang8317;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InClass07NotesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InClass07NotesFragment extends Fragment {
    private String TAG = "final";
    private OkHttpClient client;
    private String token;
    private RecyclerView recyclerView_notes;
    private NoteAdapter noteAdapter;
    private Button log_out_button;

    private Notes note_list = new Notes();

    private RecyclerView.LayoutManager recyclerViewLayoutManager;
    private TextView profile_name;
    private TextView profile_email;
    private TextView profile_userID;

    private EditText enter_note;

    private Button post_note;




    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public InClass07NotesFragment() {
        // Required empty public constructor
    }



    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InClass07NotesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static InClass07NotesFragment newInstance(String param1, String param2) {
        InClass07NotesFragment fragment = new InClass07NotesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_in_class07_notes, container, false);

        recyclerView_notes = rootview.findViewById(R.id.InClass07_notes_recycler);
        recyclerViewLayoutManager = new LinearLayoutManager(getContext());
        recyclerView_notes.setLayoutManager(recyclerViewLayoutManager);
        recyclerView_notes.setAdapter(noteAdapter);
        log_out_button = rootview.findViewById(R.id.InClass07_Logout_button);

        //set onclick listener for log out
        log_out_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                token = "";
                getActivity().onBackPressed();

            }
        });

        token = getArguments().getString("token");

        client = new OkHttpClient();


        // load the user info
        profile_email = rootview.findViewById(R.id.InClass07_note_email);
        profile_name = rootview.findViewById(R.id.InClass07_note_name);
        profile_userID = rootview.findViewById(R.id.InClass07_note_userId);
        Request req_profile = new Request.Builder()
                .url("http://ec2-54-164-201-39.compute-1.amazonaws.com:3000/api/auth/me")
                .addHeader("x-access-token",token)
                .build();
        client.newCall(req_profile).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()){
                    String stringResponse = response.body().string();
                    JSONObject jj = null;
                    try {
                        jj = new JSONObject(stringResponse);
                        String user_name = String.valueOf(jj.getString("name"));
                        String user_email = String.valueOf(jj.getString("email"));
                        String user_id = String.valueOf(jj.getString("_id"));
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                profile_email.setText(user_email);
                                profile_name.setText(user_name);
                                profile_userID.setText(user_id);
                            }
                        });

                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }


                }

            }
        });

        // set onclicker for the post button
        post_note = rootview.findViewById(R.id.InClass07_postNote_button);
        post_note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enter_note = rootview.findViewById(R.id.InClass07_notes_enter);
                String note_content = String.valueOf(enter_note.getText());
                try {
                    String postData = "text=" + URLEncoder.encode(note_content, "UTF-8");
                    RequestBody requestBody = RequestBody.create(postData, MediaType.parse("application/x-www-form-urlencoded"));

                    Request req_add = new Request.Builder()
                            .url("http://ec2-54-164-201-39.compute-1.amazonaws.com:3000/api/note/post")
                            .addHeader("x-access-token",token)
                            .post(requestBody)
                            .build();
                    client.newCall(req_add).enqueue(new Callback() {
                        @Override
                        public void onFailure(@NonNull Call call, @NonNull IOException e) {
                            e.printStackTrace();
                        }

                        @Override
                        public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                            if(response.isSuccessful()){
                                String stringResponse = response.body().string();
                                JSONObject jj = null;

                                try {
                                    jj = new JSONObject(stringResponse);
                                    String note_data = String.valueOf(jj.getString("note"));
                                    JSONObject note = new JSONObject(note_data);
                                    String userId = null;
                                    userId = note.getString("userId");
                                    String text = note.getString("text");
                                    String _id = note.getString("_id");
                                    Note newnote = new Note(userId,_id,text);
                                    addButtonClicked(newnote);

                                } catch (JSONException e) {
                                    throw new RuntimeException(e);
                                }

                            }

                        }
                    });

                } catch (UnsupportedEncodingException e) {
                    throw new RuntimeException(e);
                }


            }
        });





        //load all notes;
        Request req = new Request.Builder()
                .url("http://ec2-54-164-201-39.compute-1.amazonaws.com:3000/api/note/getall")
                .addHeader("x-access-token",token)
                .build();

        client.newCall(req).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()){
                    Gson gsonData = new Gson();
                    String stringResponse = response.body().string();
                    JSONObject jj = null;

                    try {
                        jj = new JSONObject(stringResponse);
                        String notes = String.valueOf(jj.getJSONArray("notes"));
                        JSONObject notesArray = new JSONObject();
                        notesArray.put("notes",jj.getJSONArray("notes"));
                        Notes notesData = gsonData.fromJson(notesArray.toString(), Notes.class);
                        note_list.setNotes(notesData.getNotes());
                        Log.d(TAG, "onResponse: "+note_list);
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                noteAdapter = new NoteAdapter(getContext(),note_list.getNotes());
                                noteAdapter.setCurr_Token(token);
                                noteAdapter.setT(getActivity());
                                recyclerView_notes.setAdapter(noteAdapter);


                            }
                        });

                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }


                }

            }

        });












        return rootview;
    }

    public void addButtonClicked(Note new_note) {
        note_list.add_note(new_note);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // show a message that the note is successfully added!
                Toast.makeText(getContext(),"New Note Added!",Toast.LENGTH_SHORT).show();
                enter_note.getText().clear();

                noteAdapter.notifyDataSetChanged();
            }
        });
    }
}