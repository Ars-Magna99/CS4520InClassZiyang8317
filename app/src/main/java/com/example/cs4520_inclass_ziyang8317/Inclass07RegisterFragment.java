/*
 * CS5520 In class assignment 07
 * Name: Ziyang Wang
 * Date: 2023-03-20
 * */
package com.example.cs4520_inclass_ziyang8317;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Inclass07RegisterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Inclass07RegisterFragment extends Fragment {
    private String TAG = "final";
    private EditText enterEmail;
    private EditText enterName;
    private EditText enterPassword;

    private Button submitRegister;
    private OkHttpClient client;
    private TextView registerStatus;




    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Inclass07RegisterFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Inclass07RegisterFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Inclass07RegisterFragment newInstance(String param1, String param2) {
        Inclass07RegisterFragment fragment = new Inclass07RegisterFragment();
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
        client = new OkHttpClient();

        View rootView = inflater.inflate(R.layout.fragment_inclass07_register, container, false);
        enterEmail = rootView.findViewById(R.id.RegisterEnterEmailAddress);
        enterName = rootView.findViewById(R.id.RegisterEnterName);
        enterPassword = rootView.findViewById(R.id.RegisterEnterPassword);
        submitRegister = rootView.findViewById(R.id.button_submitt_register);
        registerStatus = rootView.findViewById(R.id.RegisterStatus);

        //set the onclick view for the button to collect info and submit the request.
        submitRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = enterEmail.getText().toString();
                String name = enterName.getText().toString();
                String password = enterPassword.getText().toString();

                //check if any one is empty.
                if(email.trim().length() == 0 || name.trim().length() == 0 || password.trim().length() == 0){
                    Toast.makeText(getActivity(), "Please do not leave any empty field!", Toast.LENGTH_SHORT).show();
                }
                //send the request

                try {
                    String postData = null;
                    postData = "name=" + URLEncoder.encode(name, "UTF-8") +
                            "&email=" + URLEncoder.encode(email, "UTF-8") +
                            "&password=" + URLEncoder.encode(password, "UTF-8");
                    RequestBody requestBody = RequestBody.create(postData, MediaType.parse("application/x-www-form-urlencoded"));
                    Request req = new Request.Builder()
                            .url("http://ec2-54-164-201-39.compute-1.amazonaws.com:3000/api/auth/register")
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
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        registerStatus.setText("Registration for email: "+email +" is successful!You can go back to log in!");

                                    }
                                });
                            }else{
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        registerStatus.setText("Registration failed! Please try again!");

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