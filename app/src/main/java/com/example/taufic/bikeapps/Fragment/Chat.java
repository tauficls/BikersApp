package com.example.taufic.bikeapps.Fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.taufic.bikeapps.User;
import com.example.taufic.bikeapps.UserDetails;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.example.taufic.bikeapps.R;

public class Chat extends Fragment {
    public Chat() {

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View chat = inflater.inflate(R.layout.activity_chat, container, false);
        ((TextView) chat.findViewById(R.id.textView)).setText("Chats");

        //Database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("Community_chat").child("tes_1");

        ref.addChildEventListener(new ChildEventListener(){
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey){
                //TODO Change to LIST VIEW, getKey itu user, getValue itu message
                Log.d("DATA-CHAT", dataSnapshot.getKey()+ " " + dataSnapshot.getValue().toString());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        //TODO ADD SEND BUTTON AND MESSAGEAREA
        /*sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText = messageArea.getText().toString();

                if(!messageText.equals("")){


                    Map<String,Object> map= new HashMap<String,Object>();
                    map.put(UserDetails.username,messageText);
                    ref.updateChildren(map);

                }
            }
        });*/

        return chat;
    }
}
