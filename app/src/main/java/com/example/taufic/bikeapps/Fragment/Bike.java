package com.example.taufic.bikeapps.Fragment;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.taufic.bikeapps.R;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.Calendar;
import java.util.Random;

public class Bike extends Fragment implements LocationListener /*implements android.location.LocationListener*/ {


    private boolean firstfix;
    private static final Random RANDOM = new Random();
    private LineGraphSeries<DataPoint> series;
    private int lastX = 0;
    private long int_seconds;
    private Calendar c;
    View bike;

    public Bike() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        int_seconds = System.currentTimeMillis() / 1000;
        Log.d("Initial Second ", String.valueOf(int_seconds));

        bike = inflater.inflate(R.layout.activity_bike, container, false);
        // we get graph view instance
        GraphView graph = (GraphView) bike.findViewById(R.id.graph);
        // data
        series = new LineGraphSeries<DataPoint>();
        graph.addSeries(series);
        // customize a little bit viewport
        Viewport viewport = graph.getViewport();
        viewport.setYAxisBoundsManual(true);
        viewport.setMinY(0);
        viewport.setMaxY(5);
        viewport.setScrollable(true);

        LocationManager lm = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);


        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                getContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
//            return TODO;
        }
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 0, (android.location.LocationListener) this);

        this.onLocationChanged(null);

        return bike;
    }

    // add random data to graph
    private void addEntry() {
        // here, we choose to display max 10 points on the viewport and we scroll to end
        series.appendData(new DataPoint(lastX++, RANDOM.nextDouble() * 10d), true, 10);
    }

    @Override
    public void onLocationChanged(Location location) {
        if(bike!=null) {
            TextView txt = (TextView) bike.findViewById(R.id.TextView1);
            if (location == null) {
                txt.setText("-,- m/s");
            } else {
                float nCurrentSpeed = location.getSpeed();
                Log.d("Speed : ", String.valueOf(nCurrentSpeed));
                txt.setText(nCurrentSpeed + "m/s");
                long second = System.currentTimeMillis()/1000 -  int_seconds;


                Log.d("Graph : ", String.valueOf(second) + " " + String.valueOf(nCurrentSpeed));
                series.appendData(new DataPoint(second, nCurrentSpeed), true, 10);
                //0int_seconds = second;

            }
        }
    }
//
    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
