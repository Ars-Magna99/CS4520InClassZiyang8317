/*
 * CS5520 In-class Assignment 3
 * Name: Ziyang Wang
 * Date: 2023-02-12
 */

package com.example.cs4520_inclass_ziyang8317;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditProfileFragment extends Fragment {
    final String TAG = "final";
    private int avatar_id = R.drawable.select_avatar;
    private SeekBar seekBar_mood_Fragment;
    private TextView mood_result_Fragment;
    private ImageView image_mood_Fragment;
    private ImageView select_avatar_Fragment;

    private EditText name_Fragment;
    private EditText email_Fragment;
    private TextView mood_reminder_Fragment;
    private TextView IUse_Fragment;
    private RadioGroup phone_type_Fragment;
    private Button submit_Fragment;




    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public EditProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EditProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EditProfileFragment newInstance(String param1, String param2) {
        EditProfileFragment fragment = new EditProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**
        getParentFragmentManager().setFragmentResultListener("requestKey", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle bundle) {
                // We use a String here, but any type that can be put in a Bundle is supported
                int result = bundle.getInt("bundleKey");
                // Do something with the result
            }
        });**/
    }

    public boolean isEmailValid(String email)
    {
        final String EMAIL_PATTERN =
                "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        final Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        final Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public void update_avatar_id(int avatar_id){
        this.avatar_id = avatar_id;
    }

    //This part of the code prepares the fragment to pass a Profile object to the activity
    EditProfileFragment.IfromEditProfileToActivity sendProfile;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof IfromEditProfileToActivity) {
            sendProfile = (IfromEditProfileToActivity) context;
        } else {
            throw new RuntimeException(context.toString() + " IfromEditProfileToActivity");
        }
    }


    // define the interface to send data from EditProfileFragment to InClass03 activity
    public interface IfromEditProfileToActivity{
        void fromFragment(Profile user_profile);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_edit_profile, container, false);
        getActivity().setTitle("Edit Profile");
        mood_result_Fragment = rootView.findViewById(R.id.mood_result_Fragment);
        mood_result_Fragment.setTypeface(null, Typeface.BOLD);
        mood_result_Fragment.setTextColor(Color.BLACK);
        mood_result_Fragment.setTextSize(20);

        seekBar_mood_Fragment = rootView.findViewById(R.id.seekBar_mood_Fragment);
        image_mood_Fragment = rootView.findViewById(R.id.image_mood_Fragment);

        //change the font of some elements
        IUse_Fragment = rootView.findViewById(R.id.IUse_Fragment);
        IUse_Fragment.setTypeface(null,Typeface.BOLD);
        IUse_Fragment.setTextColor(Color.BLACK);
        IUse_Fragment.setTextSize(20);


        mood_reminder_Fragment = rootView.findViewById(R.id.mood_reminder_Fragment);
        mood_reminder_Fragment.setTypeface(null,Typeface.BOLD);
        mood_reminder_Fragment.setTextColor(Color.BLACK);
        mood_reminder_Fragment.setTextSize(20);

        mood_result_Fragment = rootView.findViewById(R.id.mood_result_Fragment);
        mood_result_Fragment.setTypeface(null, Typeface.BOLD);
        mood_result_Fragment.setTextColor(Color.BLACK);
        mood_result_Fragment.setTextSize(20);


        // link the select avatar image button with the next fragment.
        select_avatar_Fragment = rootView.findViewById(R.id.SelectAvatar_Fragment);
        select_avatar_Fragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.beginTransaction().replace(R.id.fragmentContainerView,new SelectAvatarFragment(),"select_avatar").addToBackStack(null).commit();
            }
        });
        // set listener on the image in the middle
        select_avatar_Fragment.setImageResource(this.avatar_id);
        getParentFragmentManager().setFragmentResultListener("requestKey", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle bundle) {
                // We use a String here, but any type that can be put in a Bundle is supported
                int result = bundle.getInt("bundleKey");
                avatar_id = result;
                // Do something with the result
                select_avatar_Fragment = rootView.findViewById(R.id.SelectAvatar_Fragment);
                select_avatar_Fragment.setImageResource(result);
            }
        });

        // set listener for seekbar so that the mood and emoji changes with its value
        seekBar_mood_Fragment.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if(i == 1){
                    mood_result_Fragment.setText("Sad");
                    image_mood_Fragment.setImageResource(R.drawable.sad);
                }else if(i == 2){
                    mood_result_Fragment.setText("Happy");
                    image_mood_Fragment.setImageResource(R.drawable.happy);

                }else if(i == 3){
                    mood_result_Fragment.setText("Awesome");
                    image_mood_Fragment.setImageResource(R.drawable.awesome);
                } else if(i == 0){
                    mood_result_Fragment.setText("Angry");
                    image_mood_Fragment.setImageResource(R.drawable.angry);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        name_Fragment = rootView.findViewById(R.id.Name_enter_Fragment);
        email_Fragment = rootView.findViewById(R.id.Email_Enter_Fragment);
        phone_type_Fragment = rootView.findViewById(R.id.radioGroup_phone_Fragment);
        submit_Fragment = rootView.findViewById(R.id.button_submit_Fragment);


        //set the on click listener for the submit button
        submit_Fragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user_name = name_Fragment.getText().toString();
                String user_email = email_Fragment.getText().toString();

                if(user_name.equals("") || user_email.equals("") ||phone_type_Fragment.getCheckedRadioButtonId() == -1){
                    Toast.makeText(getActivity(), "Please do not leave any empty field before submission!", Toast.LENGTH_LONG).show();
                } else if(avatar_id == R.drawable.select_avatar){
                    Toast.makeText(getActivity(), "Please select your avatar before submission!", Toast.LENGTH_LONG).show();
                } else if(isEmailValid(user_email) != true){
                    Toast.makeText(getActivity(), "Please enter a valid email address!", Toast.LENGTH_LONG).show();
                }
                else {
                    // get the text from the radio group.
                    int checked_id=phone_type_Fragment.getCheckedRadioButtonId();
                    RadioButton rb =(RadioButton)rootView.findViewById(checked_id);
                    String radioText=rb.getText().toString();


                    // get the value from seek bar
                    int mood_level = seekBar_mood_Fragment.getProgress();
                    String mood = "Angry";
                    switch (mood_level){
                        case 0: mood = "Angry";
                            break;
                        case 1: mood = "Sad";
                            break;
                        case 2: mood = "Happy";
                            break;
                        case 3: mood = "Awesome";
                            break;
                    }
                    /**
                    Intent i = new Intent(InClass02.this,DisplayProfile.class);
                    Profile np = new Profile(user_name,user_email,radioText,mood,resid);
                    i.putExtra(profile_key,np);


                    // start intent
                    startActivity(i);
                     **/
                    Profile np = new Profile(user_name,user_email,radioText,mood,avatar_id);

                    sendProfile.fromFragment(np);

                    // go to the DisplayProfile Fragment
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    fm.beginTransaction().replace(R.id.fragmentContainerView,new DisplayProfileFragment(np),"display_profile").addToBackStack(null).commit();


                }

            }
        });




        return rootView;
    }
}