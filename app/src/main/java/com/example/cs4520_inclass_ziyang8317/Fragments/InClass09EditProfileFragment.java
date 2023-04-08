package com.example.cs4520_inclass_ziyang8317.Fragments;

import static android.app.Activity.RESULT_OK;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.cs4520_inclass_ziyang8317.R;
import com.example.cs4520_inclass_ziyang8317.ReadWriteUserDetails;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import org.w3c.dom.Text;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InClass09EditProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InClass09EditProfileFragment extends Fragment {
    private Button submit_edit;
    private ImageView edit_profile_pic;
    private EditText edit_display_name;
    private EditText edit_first_name;
    private EditText edit_last_name;
    private TextView uid_display;
    private TextView email_display;

    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    StorageReference storageReference;
    private static final int IMAGE_REQUEST = 1;
    private Uri imageUri;
    private StorageTask uploadTask;
    DatabaseReference reference;
    ActivityResultLauncher<Intent> someActivityResultLauncher;




    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public InClass09EditProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InClass09EditProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static InClass09EditProfileFragment newInstance(String param1, String param2) {
        InClass09EditProfileFragment fragment = new InClass09EditProfileFragment();
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
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        read_profile();

        someActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {

                        if (result.getResultCode() == RESULT_OK
                                && result.getData() != null && result.getData().getData() != null){
                            imageUri = result.getData().getData();

                            if (uploadTask != null && uploadTask.isInProgress()){
                                Toast.makeText(getContext(), "Upload in preogress", Toast.LENGTH_SHORT).show();
                            } else {
                                uploadImage();
                            }
                        }
                    }
                });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_in_class09_edit_profile, container, false);

        //bind the components on this page;
        edit_display_name = rootView.findViewById(R.id.editprofile_display_name);
        edit_first_name = rootView.findViewById(R.id.editprofile_first_name);
        edit_last_name = rootView.findViewById(R.id.editprofile_last_name);
        uid_display = rootView.findViewById(R.id.editprofile_uid);
        submit_edit = rootView.findViewById(R.id.edit_profile_submit_button);
        email_display = rootView.findViewById(R.id.editprofile_email);
        edit_profile_pic = rootView.findViewById(R.id.edit_profile_img);

        storageReference = FirebaseStorage.getInstance().getReference("image_uploads");
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Registered Users").child(mUser.getUid());





        //set the texts of some fields with the data from database
        uid_display.setText("UID: "+ mUser.getUid());

        submit_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update_profile();
            }
        });

        edit_profile_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openImage();
            }
        });


        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (getActivity() == null) {
                    return;
                }
                ReadWriteUserDetails user = dataSnapshot.getValue(ReadWriteUserDetails.class);
                Log.d("final",user.toString());
                if (user.getImageURL().equals("default")){
                    edit_profile_pic.setImageResource(R.drawable.select_avatar);
                } else {
                    Glide.with(getContext()).load(user.getImageURL()).into(edit_profile_pic);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




        return rootView;
    }

    /**
    @Override
    public void onResume(){
        super.onResume();
        mUser = mAuth.getCurrentUser();
        read_profile();
    }**/

    //This method read the info about the current user, and set the texts in editText fields to be
    // the corresponding values.
    public void read_profile(){
        FirebaseUser logUser = mUser;
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Registered Users").child(mUser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ReadWriteUserDetails user = snapshot.getValue(ReadWriteUserDetails.class);
                String dis_name = user.display_name;
                String fname = user.first_name;
                String lname = user.last_name;
                String email = user.email;
                edit_display_name.setText(dis_name);
                edit_first_name.setText(fname);
                edit_last_name.setText(lname);
                email_display.setText("Email: "+email);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void openImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        //intent.putExtra("requestCode")
        //startActivityForResult(intent, IMAGE_REQUEST);
        someActivityResultLauncher.launch(intent);

    }


    private String getFileExtension(Uri uri){
        ContentResolver contentResolver = getContext().getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null){
            imageUri = data.getData();

            if (uploadTask != null && uploadTask.isInProgress()){
                Toast.makeText(getContext(), "Upload in preogress", Toast.LENGTH_SHORT).show();
            } else {
                uploadImage();
            }
        }
    }


    private void uploadImage(){
        final ProgressDialog pd = new ProgressDialog(getContext());
        pd.setMessage("Uploading");
        pd.show();

        if (imageUri != null){
            final  StorageReference fileReference = storageReference.child(System.currentTimeMillis()
                    +"."+getFileExtension(imageUri));

            uploadTask = fileReference.putFile(imageUri);
            uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()){
                        throw  task.getException();
                    }

                    return  fileReference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()){
                        Uri downloadUri = task.getResult();
                        String mUri = downloadUri.toString();

                        reference = FirebaseDatabase.getInstance().getReference("Registered Users").child(mUser.getUid());
                        HashMap<String, Object> map = new HashMap<>();
                        map.put("imageURL", ""+mUri);
                        reference.updateChildren(map);

                        pd.dismiss();
                    } else {
                        Toast.makeText(getContext(), "Failed!", Toast.LENGTH_SHORT).show();
                        pd.dismiss();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            });
        } else {
            Toast.makeText(getContext(), "No image selected", Toast.LENGTH_SHORT).show();
        }
    }




    public void update_profile(){
        FirebaseUser logUser = mUser;
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Registered Users").child(mUser.getUid());

        String new_display_name = String.valueOf(edit_display_name.getText());
        String new_first_name = String.valueOf(edit_first_name.getText());
        String new_last_name = String.valueOf(edit_last_name.getText());

        //set values.
        reference.child("display_name").setValue(new_display_name);
        reference.child("first_name").setValue(new_first_name);
        reference.child("last_name").setValue(new_last_name);

        //pop up
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getContext(), "Successfully updated profile!", Toast.LENGTH_LONG).show();
                getActivity().onBackPressed();
            }
        });


    }
}