package com.example.taufic.bikeapps.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.taufic.bikeapps.R;
import com.example.taufic.bikeapps.User;
import com.example.taufic.bikeapps.UserDetails;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Home extends Fragment {
    public Home() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //UID
        String UID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        UserDetails.UID = UID;
        Log.d("uid user adalah : ", UID);

        //Database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("User").child(UID);

        View home = inflater.inflate(R.layout.activity_home, container, false);
        ((TextView) home.findViewById(R.id.textView)).setText("Home");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                UserDetails.username = user.getUsername();
                UserDetails.Community = "tes_1";
                Log.d("Biji KUDA", user.getUsername());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




        return home;
    }
}
