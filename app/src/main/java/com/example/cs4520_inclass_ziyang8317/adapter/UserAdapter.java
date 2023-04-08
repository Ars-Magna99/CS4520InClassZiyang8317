/*
 * CS4520 InClass08
 * Name: Ziyang Wang
 * Date: 2023-03-27
 * */

package com.example.cs4520_inclass_ziyang8317.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cs4520_inclass_ziyang8317.Activity.InClass09ChatActivity;
import com.example.cs4520_inclass_ziyang8317.Fragments.ChatFragment;
import com.example.cs4520_inclass_ziyang8317.Inclass07RegisterFragment;
import com.example.cs4520_inclass_ziyang8317.R;
import com.example.cs4520_inclass_ziyang8317.ReadWriteUserDetails;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
    private Context mcontext;
    private ArrayList<ReadWriteUserDetails> users;

    private Activity activity;

    public UserAdapter(Context mcontext, ArrayList<ReadWriteUserDetails> users) {
        this.mcontext = mcontext;
        this.users = users;
    }

    public void setActivity(Activity t) {
        this.activity = t;
    }


    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mcontext).inflate(R.layout.inclass08_user_cardview,parent,false);

        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        ReadWriteUserDetails currUser = users.get(position);

        holder.user_id.setText(currUser.display_name);
        holder.user_name.setText(currUser.first_name+" "+currUser.last_name);
        holder.user_email.setText(currUser.email);
        holder.uid = currUser.uid;

    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder{

        public TextView user_id,user_name,user_email;
        public String uid;
        public Button chat_button;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            user_id = itemView.findViewById(R.id.InClass08_chat_user_id);
            user_name = itemView.findViewById(R.id.InClass08_chat_name);
            user_email = itemView.findViewById(R.id.InClass08_chat_user_email);
            chat_button = itemView.findViewById(R.id.InClass08_user_chat_button);

            chat_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AppCompatActivity curr_act = (AppCompatActivity) view.getContext();
                    FragmentManager fm = curr_act.getSupportFragmentManager();
                    Bundle bundle = new Bundle();
                    bundle.putString("user_to_talk_display_name", String.valueOf(user_id.getText()));
                    bundle.putString("user_to_talk_email", String.valueOf(user_email.getText()));
                    bundle.putString("user_to_talk_uid", String.valueOf(uid));


                    //ChatFragment to_chat = new ChatFragment();
                    //to_chat.setArguments(bundle);

                    //fm.beginTransaction().replace(R.id.fragmentContainerView_InClass8,to_chat,"starting chat").addToBackStack(null).commit();

                    Intent i = new Intent(mcontext, InClass09ChatActivity.class);
                    i.putExtras(bundle);
                    mcontext.startActivity(i);


                }
            });

        }
    }


}
