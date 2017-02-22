package com.example.taufic.bikeapps.Fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.taufic.bikeapps.R;

public class Home extends Fragment {
    public Home() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View home = inflater.inflate(R.layout.activity_home, container, false);
        ((TextView) home.findViewById(R.id.textView)).setText("Home");
        return home;
    }
}
