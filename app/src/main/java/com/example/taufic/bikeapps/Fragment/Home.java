package com.example.taufic.bikeapps.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.taufic.bikeapps.Adapter.ListCommunityAdapter;
import com.example.taufic.bikeapps.Adapter.ListEventAdapter;
import com.example.taufic.bikeapps.AddCommunity;
import com.example.taufic.bikeapps.Community;
import com.example.taufic.bikeapps.Event;
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
    private ArrayList<Event> listEvent;
    private ListView listevent;
    private ListView list_Community;
    private View home;

    public FirebaseDatabase database;
    public DatabaseReference ref2;
    public DatabaseReference ref3;

    private boolean mDescribe;

    //Filtered community in the same city
    ArrayList<Community> getFilteredCityCommunity (ArrayList<Community> listOfCommunity, String cityName) {
        ArrayList<Community> listCommunity = new ArrayList<Community>();

        for (int i=0; i<listOfCommunity.size(); i++) {
            if (cityName.compareTo(listOfCommunity.get(i).getId()) == 0) {
                listCommunity.add(listOfCommunity.get(i));
            }
        }

        return listCommunity;
    }

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
        database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("User").child(UID);

        //List of Community
        listCommunity = new ArrayList<Community>();
        listEvent = new ArrayList<Event>();

        ref3 = FirebaseDatabase.getInstance().getReference("Event");
        ImageView btn = (ImageView) home.findViewById(R.id.addcommunity);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AddCommunity.class));
            }
        });
        ((TextView) home.findViewById(R.id.textView)).setText("Home");
        username = (TextView) home.findViewById(R.id.username);
        description = (TextView) home.findViewById(R.id.description);

        listevent = (ListView) home.findViewById(R.id.list_event);
        list_Community = (ListView) home.findViewById(R.id.list_community);

        ref3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    listEvent.add(childSnapshot.getValue(Event.class));
                }
                ListCommunityAdapter listCommunityAdapter = new ListCommunityAdapter(getContext(), R.layout.listview_community, listEvent);
                list_Community.setAdapter(listCommunityAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                user = dataSnapshot.getValue(User.class);
                username.setText(user.getUsername());
                description.setText(user.getDescription());

                //Already have community
                if (user.getCommunityID() != "null") {
                    ref2 = database.getReference("Community").child(user.getCommunityID());
                    ref2.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                                listCommunity.add(dataSnapshot.getValue(Community.class));

                            //listCommunity = getFilteredCommunity(listCommunity, user.getCommunityID());
                            ListEventAdapter listEventAdapter = new ListEventAdapter(getContext(), R.layout.listevent, listCommunity);
                            listevent.setAdapter(listEventAdapter);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }
                //Don't have community
                else {
                    ref2 = database.getReference("Community");
                    SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
                    final String city = sharedPref.getString("city", "null");
                    System.out.println(city);

                    ref2.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                                listCommunity.add(childSnapshot.getValue(Community.class));
                            }

                            //Filtered community in the same city as user
                            listCommunity = getFilteredCityCommunity(listCommunity, city);
                            //listCommunity = getFilteredCommunity(listCommunity, user.getCommunityID());
                            ListEventAdapter listEventAdapter = new ListEventAdapter(getContext(), R.layout.listevent, listCommunity);
                            listevent.setAdapter(listEventAdapter);

                            Log.d("Biji KUDA", listCommunity.get(0).getDescription());
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });
                }
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

    public void addCommuni(View view) {

    }
}
