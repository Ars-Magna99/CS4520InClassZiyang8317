/*
 * CS4520 InClass09
 * Name: Ziyang Wang
 * Date: 2023-04-06
 * */
package com.example.cs4520_inclass_ziyang8317.Activity;

import static android.app.PendingIntent.getActivity;

import static java.security.AccessController.getContext;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

import java.util.HashMap;

public class InClass09EditProfileActivity extends AppCompatActivity {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_class09_edit_profile);

        setTitle("Edit your Text");
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        read_profile();


        edit_display_name = findViewById(R.id.editprofile_display_name);
        edit_first_name = findViewById(R.id.editprofile_first_name);
        edit_last_name = findViewById(R.id.editprofile_last_name);
        uid_display = findViewById(R.id.editprofile_uid);
        submit_edit = findViewById(R.id.edit_profile_submit_button);
        email_display = findViewById(R.id.editprofile_email);
        edit_profile_pic = findViewById(R.id.edit_profile_img);

        storageReference = FirebaseStorage.getInstance().getReference("image_uploads");
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Registered Users").child(mUser.getUid());

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
                if (getContext() == null) {
                    return;
                }
                ReadWriteUserDetails user = dataSnapshot.getValue(ReadWriteUserDetails.class);
                Log.d("final",user.toString());
                if (user.getImageURL() == null){
                    edit_profile_pic.setImageResource(R.drawable.select_avatar);
                } else {
                    Glide.with(getApplicationContext()).load(user.getImageURL()).into(edit_profile_pic);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




    }

    private void openImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        //intent.putExtra("requestCode")
        startActivityForResult(intent, IMAGE_REQUEST);
        //someActivityResultLauncher.launch(intent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null){
            imageUri = data.getData();

            if (uploadTask != null && uploadTask.isInProgress()){
                Toast.makeText(this, "Upload in preogress", Toast.LENGTH_SHORT).show();
            } else {
                uploadImage();
            }
        }
    }


    private String getFileExtension(Uri uri){
        ContentResolver contentResolver = getApplicationContext().getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

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

    private void uploadImage(){
        final ProgressDialog pd = new ProgressDialog(InClass09EditProfileActivity.this);
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
                        Toast.makeText(getApplicationContext(), "Failed!", Toast.LENGTH_SHORT).show();
                        pd.dismiss();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            });
        } else {
            Toast.makeText(this, "No image selected", Toast.LENGTH_SHORT).show();
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
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), "Successfully updated profile!", Toast.LENGTH_LONG).show();
                //onBackPressed();
            }
        });


    }


}