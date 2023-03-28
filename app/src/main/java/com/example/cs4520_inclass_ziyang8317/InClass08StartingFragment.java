/*
* CS4520 InClass08
* Name: Ziyang Wang
* Date: 2023-03-27
* */

package com.example.cs4520_inclass_ziyang8317;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.cs4520_inclass_ziyang8317.adapter.UserAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InClass08StartingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InClass08StartingFragment extends Fragment {
    private static final String ARG_FRIENDS = "friendsarray";
    private Button log_out_button;
    private ImainFragmentButtonAction mListener;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    private String username;

    private RecyclerView user_list;
    private UserAdapter userAdapter;
    private ArrayList<ReadWriteUserDetails> users;

    private TextView curr_username_reminder;




    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public InClass08StartingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InClass08StartingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static InClass08StartingFragment newInstance(String param1, String param2) {
        InClass08StartingFragment fragment = new InClass08StartingFragment();
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
            Bundle args = getArguments();
            db = FirebaseFirestore.getInstance();
            mAuth = FirebaseAuth.getInstance();
            mUser = mAuth.getCurrentUser();


            readUsers();


        }


    }

    public static InClass08StartingFragment newInstance() {
        InClass08StartingFragment fragment = new InClass08StartingFragment();
        Bundle args = new Bundle();
        //args.putSerializable(ARG_FRIENDS, new ArrayList<Friend>());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof ImainFragmentButtonAction){
            mListener = (ImainFragmentButtonAction) context;
        }else{
            throw new RuntimeException(context.toString()+ "must implement IaddButtonAction");
        }
    }
    @Override
    public void onResume(){
        super.onResume();
        readUsers();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_in_class08_starting, container, false);

        curr_username_reminder = rootView.findViewById(R.id.username_reminder);

        log_out_button = rootView.findViewById(R.id.InClass08_logout_button);

        log_out_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.logoutPressed();
            }
        });


        user_list = rootView.findViewById(R.id.InClass08_user_list);
        user_list.setLayoutManager(new LinearLayoutManager(getContext()));
        users = new ArrayList<ReadWriteUserDetails>();
        curr_username_reminder.setText("Welcome to the chat room!");

        return rootView;
    }

    public void readUsers(){
        FirebaseUser logUser = mUser;
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Registered Users");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                users.clear();
                for(DataSnapshot snap: snapshot.getChildren()){
                    ReadWriteUserDetails user = snap.getValue(ReadWriteUserDetails.class);
                    assert user != null;
                    assert  logUser != null;
                    //exclude the current user himself.
                    if(!Objects.equals(logUser.getUid(), user.uid)){
                        users.add(user);
                    }
                }
                userAdapter = new UserAdapter(getContext(),users);
                userAdapter.setActivity(getActivity());
                user_list.setAdapter(userAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public interface ImainFragmentButtonAction {
        void logoutPressed();
    }
}