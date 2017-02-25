package com.example.taufic.bikeapps.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.taufic.bikeapps.Adapter.ListEventAdapter;
import com.example.taufic.bikeapps.Community;
import com.example.taufic.bikeapps.R;
import com.example.taufic.bikeapps.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Home extends Fragment  {
    private TextView username;
    private ImageView userImage;
    private TextView description;

    private User user;
    private ArrayList<Community> listCommunity;
    private ListView listevent;
    private View home;

    public DatabaseReference ref2;

    private boolean mDescribe;

    //Filtered community if already join community
    ArrayList<Community> getFilteredCommunity (ArrayList<Community> listOfCommunity, String communityID) {
        ArrayList<Community> listCommunity = new ArrayList<Community>();

        for (int i=0; i<listOfCommunity.size(); i++) {
            if (communityID.compareTo(listOfCommunity.get(i).getId()) == 0) {
                listCommunity.add(listOfCommunity.get(i));
            }
        }

        return listCommunity;
    }

    public Home() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        home = inflater.inflate(R.layout.activity_home, container, false);

        //UID
        String UID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        //Database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("User").child(UID);
        ref2 = database.getReference("Community");

        //List of Community
        listCommunity = new ArrayList<Community>();

        ((TextView) home.findViewById(R.id.textView)).setText("Home");
        username = (TextView) home.findViewById(R.id.username);
        description = (TextView) home.findViewById(R.id.description);

        listevent = (ListView) home.findViewById(R.id.list_event);

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                user = dataSnapshot.getValue(User.class);
                Log.d("Biji KUDA", user.getUsername());
                username.setText(user.getUsername());
                description.setText(user.getDescription());

                ref2.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                            listCommunity.add(childSnapshot.getValue(Community.class));
                        }

                        listCommunity = getFilteredCommunity(listCommunity, user.getCommunityID());
                        ListEventAdapter listEventAdapter = new ListEventAdapter(getContext(), R.layout.listevent, listCommunity);
                        listevent.setAdapter(listEventAdapter);

                        Log.d("Biji KUDA", listCommunity.get(0).getDescription());
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mDescribe = false;

        return home;
    }

    public void joinCommunity(View view) {
    }

}
