/*
 * CS5520 In class assignment 07
 * Name: Ziyang Wang
 * Date: 2023-03-20
 * */
package com.example.cs4520_inclass_ziyang8317;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InClass07LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InClass07LoginFragment extends Fragment {
    private String TAG = "final";
    private Button submit_login;
    private EditText login_email;
    private EditText login_password;
    private OkHttpClient client;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public InClass07LoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InClass07LoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static InClass07LoginFragment newInstance(String param1, String param2) {
        InClass07LoginFragment fragment = new InClass07LoginFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_in_class07_login, container, false);

        client = new OkHttpClient();
        login_email = rootView.findViewById(R.id.LoginEnterEmailAddress);
        login_password = rootView.findViewById(R.id.LoginEnterPassword);
        submit_login = rootView.findViewById(R.id.button_submitt_login);

        submit_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = login_email.getText().toString();
                String password = login_password.getText().toString();

                if(email.trim().length() == 0 || password.trim().length() == 0){
                    Toast.makeText(getActivity(), "Please do not leave any empty field!", Toast.LENGTH_SHORT).show();
                }

                String postData = null;
                try {
                    postData = "email=" + URLEncoder.encode(email, "UTF-8") +
                            "&password=" + URLEncoder.encode(password, "UTF-8");
                    RequestBody requestBody = RequestBody.create(postData, MediaType.parse("application/x-www-form-urlencoded"));
                    Request req = new Request.Builder()
                            .url("http://ec2-54-164-201-39.compute-1.amazonaws.com:3000/api/auth/login")
                            .post(requestBody)
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
                                String token;
                                try {
                                    jj = new JSONObject(stringResponse);
                                    token = String.valueOf(jj.getString("token"));

                                } catch (JSONException e) {
                                    throw new RuntimeException(e);
                                }

                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getActivity(),"Login successful!",Toast.LENGTH_SHORT).show();
                                    }
                                });
                                //send the token to note fragment
                                Bundle bundle = new Bundle();
                                bundle.putString("token",token);

                                InClass07NotesFragment notesScreen = new InClass07NotesFragment();
                                notesScreen.setArguments(bundle);

                                FragmentManager fm = getActivity().getSupportFragmentManager();
                                fm.beginTransaction().replace(R.id.fragmentContainerView_InClass7,notesScreen,"logged in").addToBackStack(null).commit();

                            } else{
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getActivity(),"Login failed! Please check your email and password!",Toast.LENGTH_SHORT).show();
                                    }
                                });

                            }

                        }
                    });
                } catch (UnsupportedEncodingException e) {
                    throw new RuntimeException(e);
                }



            }
        });


        return rootView;
    }
}