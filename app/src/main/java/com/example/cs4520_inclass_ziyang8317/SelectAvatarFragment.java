/*
 * CS5520 In-class Assignment 3
 * Name: Ziyang Wang
 * Date: 2023-02-12
 */
package com.example.cs4520_inclass_ziyang8317;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SelectAvatarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SelectAvatarFragment extends Fragment {
    private TextView reminder_frag;

    private ImageView avatar_f1;
    private ImageView avatar_f2;
    private ImageView avatar_f3;
    private ImageView avatar_m1;
    private ImageView avatar_m2;
    private ImageView avatar_m3;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SelectAvatarFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SelectAvatarFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SelectAvatarFragment newInstance(String param1, String param2) {
        SelectAvatarFragment fragment = new SelectAvatarFragment();
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

    // implement the interface
    IfromFragmentToActivity sendData;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof IfromFragmentToActivity) {
            sendData = (IfromFragmentToActivity) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement IfromFragmentToActivity!");
        }
    }


    public interface IfromFragmentToActivity{
        void fromFragment(int avatar_id);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_select_avatar, container, false);

        // Set onclick listeners for each image
        avatar_f1 = rootView.findViewById(R.id.avatar_f1_frag);
        avatar_f1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle result = new Bundle();
                result.putInt("bundleKey",R.drawable.avatar_f_1);
                getParentFragmentManager().setFragmentResult("requestKey", result);
                sendData.fromFragment(R.drawable.avatar_f_1);
                getActivity().onBackPressed();

            }
        });

        avatar_f2 = rootView.findViewById(R.id.avatar_f2_frag);
        avatar_f2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle result = new Bundle();
                result.putInt("bundleKey",R.drawable.avatar_f_2);
                getParentFragmentManager().setFragmentResult("requestKey", result);
                sendData.fromFragment(R.drawable.avatar_f_2);

                getActivity().onBackPressed();
            }
        });

        avatar_f3 = rootView.findViewById(R.id.avatar_f3_frag);
        avatar_f3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle result = new Bundle();
                result.putInt("bundleKey",R.drawable.avatar_f_3);
                getParentFragmentManager().setFragmentResult("requestKey", result);
                sendData.fromFragment(R.drawable.avatar_f_3);

                getActivity().onBackPressed();
            }
        });

        avatar_m1 = rootView.findViewById(R.id.avatar_m1_frag);
        avatar_m1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle result = new Bundle();
                result.putInt("bundleKey",R.drawable.avatar_m_3);
                getParentFragmentManager().setFragmentResult("requestKey", result);
                sendData.fromFragment(R.drawable.avatar_m_3);

                getActivity().onBackPressed();
            }
        });

        avatar_m2 = rootView.findViewById(R.id.avatar_m2_frag);
        avatar_m2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle result = new Bundle();
                result.putInt("bundleKey",R.drawable.avatar_m_2);
                getParentFragmentManager().setFragmentResult("requestKey", result);
                sendData.fromFragment(R.drawable.avatar_m_2);

                getActivity().onBackPressed();
            }
        });

        avatar_m3 = rootView.findViewById(R.id.avatar_m3_frag);
        avatar_m3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle result = new Bundle();
                result.putInt("bundleKey",R.drawable.avatar_m_1);
                getParentFragmentManager().setFragmentResult("requestKey", result);
                sendData.fromFragment(R.drawable.avatar_m_1);

                getActivity().onBackPressed();
            }
        });



        return rootView;
    }
}