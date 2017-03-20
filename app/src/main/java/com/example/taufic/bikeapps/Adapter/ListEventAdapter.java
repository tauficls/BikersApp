package com.example.taufic.bikeapps.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.taufic.bikeapps.CommunityMember;
import com.example.taufic.bikeapps.Event;
import com.example.taufic.bikeapps.R;
import com.example.taufic.bikeapps.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by taufic on 2/25/2017.
 */

public class ListEventAdapter extends ArrayAdapter<Event> {
    ArrayList<Event> items;
    Context context;

    FirebaseDatabase database = FirebaseDatabase.getInstance();

    public CommunityMember member = new CommunityMember();
    public User user;
    public int id;
    public DatabaseReference ref2;

    public ListEventAdapter(Context context, int resources, ArrayList<Event> items) {
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
            view = layoutInflater.inflate(R.layout.listevent, null);
        }
        Event i = items.get(position);
        if (i != null) {

            TextView communityId_event = (TextView) view.findViewById(R.id.communityID);
            TextView name_event = (TextView) view.findViewById(R.id.name_event);
            TextView location_event = (TextView) view.findViewById(R.id.location_event);
            TextView description_event = (TextView) view.findViewById(R.id.description_event);
            TextView date_event = (TextView) view.findViewById(R.id.date_event);
//            ListView listView = (ListView) view.findViewById(R.id.list_view);
//            ImageView imageView_news = (ImageView) convertView.findViewById(R.id.imageview_news);
            communityId_event.setText(i.getCommunityId());
            name_event.setText(i.getName());
            location_event.setText(i.getLocation());
            description_event.setText(i.getDescription());
            date_event.setText(i.getDate());
        }
        return view;

    }
}
