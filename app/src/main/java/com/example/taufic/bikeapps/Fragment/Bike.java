package com.example.taufic.bikeapps.Fragment;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.taufic.bikeapps.R;

public class Bike extends Fragment {
    public Bike() {

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View bike = inflater.inflate(R.layout.activity_bike, container, false);
        ((TextView) bike.findViewById(R.id.textView)).setText("Bike");
        return bike;
    }
}
