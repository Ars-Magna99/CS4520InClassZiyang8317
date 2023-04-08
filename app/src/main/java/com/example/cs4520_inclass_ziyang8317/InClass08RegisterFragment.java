/*
 * CS4520 InClass08
 * Name: Ziyang Wang
 * Date: 2023-03-27
 * */


package com.example.cs4520_inclass_ziyang8317;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InClass08RegisterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;

import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.cs4520_inclass_ziyang8317.Fragments.CameraFragment;
import com.example.cs4520_inclass_ziyang8317.Fragments.FragmentDisplayImage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentRegister#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InClass08RegisterFragment extends Fragment implements View.OnClickListener,CameraFragment.DisplayTakenPhoto,FragmentDisplayImage.RetakePhoto {

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    private EditText editTextRegister_DisplayName, editTextEmail, editTextPassword, editTextRepPassword;

    private EditText editTextRegister_LName,editTextRegister_FName;
    private Button buttonRegister;
    private String last_name,first_name,display_name, email, password, rep_password;
    private IregisterFragmentAction mListener;
    private ImageView take_profile_pic;

    private static final int PERMISSIONS_CODE = 0x100;
    private FrameLayout containerRoot;
    private FirebaseStorage storage;


    public InClass08RegisterFragment() {
        // Required empty public constructor
    }

    public static InClass08RegisterFragment newInstance() {
        InClass08RegisterFragment fragment = new InClass08RegisterFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof IregisterFragmentAction){
            this.mListener = (IregisterFragmentAction) context;
        }else{
            throw new RuntimeException(context.toString()
                    + "must implement RegisterRquest");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_in_class08_register, container, false);

        editTextRegister_DisplayName = rootView.findViewById(R.id.editTextRegister_DisplayName);
        editTextEmail = rootView.findViewById(R.id.editTextRegister_Email);
        editTextPassword = rootView.findViewById(R.id.editTextRegister_Password);
        editTextRepPassword = rootView.findViewById(R.id.editTextRegister_Rep_Password);
        buttonRegister = rootView.findViewById(R.id.buttonRegister);
        buttonRegister.setOnClickListener(this);
        editTextRegister_FName = rootView.findViewById(R.id.editTextRegister_FName);
        editTextRegister_LName = rootView.findViewById(R.id.editTextRegister_LName);
        take_profile_pic = rootView.findViewById(R.id.register_profile_image);

        take_profile_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.beginTransaction().replace(R.id.fragmentContainerView_InClass8,new CameraFragment(),"take picture").addToBackStack(null).commit();
            }
        });


        return rootView;
    }

    @Override
    public void onClick(View view) {
        this.display_name = String.valueOf(editTextRegister_DisplayName.getText()).trim();
        this.email = String.valueOf(editTextEmail.getText()).trim();
        this.password = String.valueOf(editTextPassword.getText()).trim();
        this.rep_password = String.valueOf(editTextRepPassword.getText()).trim();
        this.last_name = String.valueOf(editTextRegister_LName.getText()).trim();
        this.first_name = String.valueOf(editTextRegister_FName.getText()).trim();


        if(view.getId()== R.id.buttonRegister){
//            Validations........
            if(display_name.equals("")){
                editTextRegister_DisplayName.setError("Must input email!");
            }
            if(email.equals("")){
                editTextEmail.setError("Must input email!");
            }
            if(password.equals("")){
                editTextPassword.setError("Password must not be empty!");
            }
            if(!rep_password.equals(password)){
                editTextRepPassword.setError("Passwords must match!");
            }
            if(first_name.equals("")){
                editTextRegister_FName.setError("Must input first name!");
            }
            if(last_name.equals("")){
                editTextRegister_LName.setError("Must input last name!");
            }


//            Validation complete.....
            if(!display_name.equals("") && !email.equals("")
                    && !password.equals("")
                    && rep_password.equals(password)){

                //              Firebase authentication: Create user.......
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    mUser = task.getResult().getUser();

//                                    Adding name to the FirebaseUser...
                                    UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder()
                                            .setDisplayName(display_name)
                                            .build();
                                    DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("Registered Users");
                                    String uid = mAuth.getUid();
                                    ReadWriteUserDetails new_user = new ReadWriteUserDetails(uid,first_name,last_name,display_name, email);
                                    referenceProfile.child(mUser.getUid()).setValue(new_user);//.addOnCompleteListener(new OnCompleteListener<Void>() {


                                    mUser.updateProfile(profileChangeRequest)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if(task.isSuccessful()){
                                                        mListener.registerDone(mUser);
                                                    }
                                                }
                                            });

                                }
                            }
                        });
            }
        }
    }

    @Override
    public void onTakePhoto(Uri imageUri) {
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainerView_InClass8,FragmentDisplayImage.newInstance(imageUri),"displayFragment")
                .commit();
    }

    ActivityResultLauncher<Intent> galleryLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode()==RESULT_OK){
                        Intent data = result.getData();
                        Uri selectedImageUri = data.getData();
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragmentContainerView_InClass8,FragmentDisplayImage.newInstance(selectedImageUri),"displayFragment")
                                .commit();
                    }
                }
            }
    );

    @Override
    public void onOpenGalleryPressed() {
        openGallery();
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        String[] mimeTypes = {"image/jpeg", "image/png"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES,mimeTypes);
        galleryLauncher.launch(intent);
    }

    @Override
    public void onRetakePressed() {
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainerView_InClass8, CameraFragment.newInstance(), "cameraFragment")
                .commit();
    }

    @Override
    public void onUploadButtonPressed(Uri imageUri, ProgressBar progressBar) {
        //        ProgressBar.......
        progressBar.setVisibility(View.VISIBLE);
//        Upload an image from local file....
        StorageReference storageReference = storage.getReference().child("images/"+imageUri.getLastPathSegment());
        UploadTask uploadImage = storageReference.putFile(imageUri);
        uploadImage.addOnFailureListener(new OnFailureListener() {

                    @Override
                    public void onFailure(@NonNull Exception e) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getContext(), "Upload Failed! Try again!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                })
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getContext(), "Upload successful! Check Firestore", Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                            }
                        });

                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                double progress = (100.0 * snapshot.getBytesTransferred()) / snapshot.getTotalByteCount();
                                Log.d("demo", "onProgress: "+progress);
                                progressBar.setProgress((int) progress);
                            }
                        });

                    }
                });

    }

    public interface IregisterFragmentAction {
        void registerDone(FirebaseUser mUser);
    }
}