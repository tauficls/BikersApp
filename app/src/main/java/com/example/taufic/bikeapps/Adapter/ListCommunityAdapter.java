package com.example.taufic.bikeapps.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.taufic.bikeapps.Community;
import com.example.taufic.bikeapps.CommunityMember;
import com.example.taufic.bikeapps.R;
import com.example.taufic.bikeapps.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by taufic on 2/25/2017.
 */

public class ListCommunityAdapter extends ArrayAdapter<Community> {

    ArrayList<Community> items;
    Context context;

    FirebaseDatabase database = FirebaseDatabase.getInstance();

    public CommunityMember member = new CommunityMember();
    public User user;
    public int id;
    public DatabaseReference ref2;

    public ListCommunityAdapter(Context context, int resources, ArrayList<Community> items) {
        super(context, resources, items);
        this.context = context;
        this.items = items;
    }
    private void goToUrl(String url) {
        Uri uriUrl = Uri.parse(url);
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        context.startActivity(launchBrowser);
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view ==  null) {
            LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.listcommunity, null);
        }
        Community i = items.get(position);
        if (i != null) {

            TextView name_community = (TextView) view.findViewById(R.id.name_community);
            TextView description_community = (TextView) view.findViewById(R.id.description_community);
            TextView location_community = (TextView) view.findViewById(R.id.location_community);
            TextView owner_community = (TextView) view.findViewById(R.id.owner_community);

//            ListView listView = (ListView) view.findViewById(R.id.list_view);
//            ImageView imageView_news = (ImageView) convertView.findViewById(R.id.imageview_news);
            name_community.setText(i.getName());
            description_community.setText(i.getDescription());
            location_community.setText(i.getLocation());
            owner_community.setText(i.getOwner());

            final Button button = (Button) view.findViewById(R.id.join_button);
            button.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    ref2 = database.getReference("User").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                    ref2.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            user = dataSnapshot.getValue(User.class);
                            Log.d("KUDA ", user.getCommunityID());
                            if (user.getCommunityID().compareTo("null") == 0) {
                                DatabaseReference ref = database.getReference("CommunityMember").child(items.get(position).getId());
                                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        member = dataSnapshot.getValue(CommunityMember.class);
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });

                                //Update user value
                                user.setCommunityID(items.get(position).getId());
                                ref2.setValue(user);

                                //Update member value
                                member.addUID(FirebaseAuth.getInstance().getCurrentUser().getUid());
                                ref.setValue(member);
                            }
                            else {
                                Toast.makeText(getContext(), "You can only join one community", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                    button.setEnabled(false);
                }
            });


        }
        return view;

    }
}
