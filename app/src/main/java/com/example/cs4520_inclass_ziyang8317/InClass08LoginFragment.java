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

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InClass08LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InClass08LoginFragment extends Fragment implements View.OnClickListener {
    private String TAG = "final";
    private EditText editTextEmail, editTextPassword;
    private Button buttonLogin, buttonOpenRegister;
    private String userEmail;
    private String password;
    private FirebaseAuth mAuth;
    private IloginFragmentAction mListener;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public InClass08LoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InClass08LoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static InClass08LoginFragment newInstance(String param1, String param2) {
        InClass08LoginFragment fragment = new InClass08LoginFragment();
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

        mAuth = FirebaseAuth.getInstance();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater
                .inflate(R.layout.fragment_in_class08_login, container, false);

        editTextEmail = rootView.findViewById(R.id.InClass08_enter_email);
        editTextPassword = rootView.findViewById(R.id.InClass08_enter_password);
        buttonLogin =rootView.findViewById(R.id.buttonLogin);
        buttonOpenRegister = rootView.findViewById(R.id.buttonOpenRegister);

        buttonLogin.setOnClickListener(this);
        buttonOpenRegister.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof IloginFragmentAction){
            this.mListener = (IloginFragmentAction) context;
        }else{
            throw new RuntimeException(context.toString()+ "must implement PopulateMainFragment");
        }
    }


    public static InClass08LoginFragment newInstance() {
        InClass08LoginFragment fragment = new InClass08LoginFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.buttonLogin){
            userEmail = editTextEmail.getText().toString().trim();
            password = editTextPassword.getText().toString().trim();
            if(userEmail.equals("")){
                editTextEmail.setError("Must input email!");
            }
            if(password.equals("")){
                editTextPassword.setError("Password must not be empty!");
            }
            if(!userEmail.equals("") && !password.equals("")){
//                    Sign in to the account....
                mAuth.signInWithEmailAndPassword(userEmail,password)
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                Toast.makeText(getContext(), "Login Successful!", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getContext(), "Login Failed!"+e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        })
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    mListener.populateMainFragment(mAuth.getCurrentUser());
                                }
                            }
                        })
                ;
            }

        }else if(view.getId()== R.id.buttonOpenRegister){
            mListener.populateRegisterFragment();
        }
    }

    public interface IloginFragmentAction {
        void populateMainFragment(FirebaseUser mUser);
        void populateRegisterFragment();
    }
}