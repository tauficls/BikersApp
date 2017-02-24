package com.example.taufic.bikeapps.Location;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.taufic.bikeapps.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class Geolocation extends AppCompatActivity implements ConnectionCallbacks,
        OnConnectionFailedListener {

    private GoogleApiClient mGoogleApiClient;
    private static final String TAG = "Geolocation";
    private Location mLastLocation;
    private boolean mAddressRequested;
    private String mAddressOutput;
    private AddressResultReceiver addressResultReceiver;
    private TextView mLocationAddressTextView;
    ProgressBar progressBar;
    Button FetchAddressButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geolocation);

        addressResultReceiver = new AddressResultReceiver(new Handler());

        mLocationAddressTextView = (TextView) findViewById(R.id.location_address_text);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        FetchAddressButton = (Button) findViewById(R.id.fetch_address_button);

        mAddressRequested = false;
        mAddressOutput = "";
        updateValuesFromBundle(savedInstanceState);

        if (mAddressRequested) {
            progressBar.setVisibility(ProgressBar.VISIBLE);
            FetchAddressButton.setEnabled(false);
        } else {
            progressBar.setVisibility(ProgressBar.GONE);
            FetchAddressButton.setEnabled(true);
        }

        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }


    }
    /*
    update field base on datastore in the bundle
     */
    public void updateValuesFromBundle(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            if (savedInstanceState.keySet().contains("address requested pending")) {
                mAddressRequested = savedInstanceState.getBoolean("address requested pending");
            }
            if (savedInstanceState.keySet().contains("location address")) {
                mAddressOutput = savedInstanceState.getString("location address");
                displayAddressOutput();
            }
        }
    }
    /*
    Start services when clicked
     */
    public void fetchAddressButtonHandler(View view) {

        if (mGoogleApiClient.isConnected() && mLastLocation != null) {
            startIntentService();
        }
        mAddressRequested = true;
        updateUIWidgets();
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            Log.d(TAG, "Permission not Granted");
            finish();
        }
                mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation != null) {
            if (!Geocoder.isPresent()) {
                Toast.makeText(this, "no geocoder available", Toast.LENGTH_LONG).show();
                return;
            }
            if (mAddressRequested) {
                startIntentService();
            }
            List<Address> addresses = null;
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            try {
                addresses = geocoder.getFromLocation(mLastLocation.getLatitude(), mLastLocation.getLongitude(),1);



            } catch (IOException ioException){
                Log.e(TAG, "service not available", ioException);
            } catch (IllegalArgumentException illegalArgumentException) {
                Log.e(TAG, "invalid value latitude and longitude", illegalArgumentException);
            }

            // Condition no address was found
            if (addresses == null || addresses.size() == 0) {
                Log.e(TAG, "no address found");
//                deliverResultToReceiver(Constants.FAILURE_RESULT, "no address found");
            } else {
                String address = addresses.get(0).getSubAdminArea();
                TextView longitude = (TextView) findViewById(R.id.longitude_text);
                longitude.setText(address);
            }
        }
    }
    /*
    google service lost connection. do connect() again.
     */
    @Override
    public void onConnectionSuspended(int i) {
        Log.d(TAG, "Conenction Suspended");
        mGoogleApiClient.connect();
    }

    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    protected void onStop() {
        if (mGoogleApiClient.isConnected())
            mGoogleApiClient.disconnect();
        super.onStop();
    }

    protected void displayAddressOutput() {
        mLocationAddressTextView.setText(mAddressOutput);
    }
    /*
    make visible progress bar and enable or disable button.
     */
    private void updateUIWidgets() {
        if (mAddressRequested) {
            progressBar.setVisibility(ProgressBar.VISIBLE);
            FetchAddressButton.setEnabled(false);
        } else {
            progressBar.setVisibility(ProgressBar.GONE);
            FetchAddressButton.setEnabled(true);
        }
    }



    @Override
    public void onConnectionFailed(ConnectionResult result) {
        // An unresolvable error has occurred and a connection to Google APIs
        // could not be established. Display an error message, or handle
        // the failure silently
        Log.d(TAG, "Connection failed: ConnectionResult = " + result.getErrorMessage());
        finish();
        // ...
    }

    /*
    create intent services
     */
    public void startIntentService() {
        Intent intent = new Intent(this,FetchAddressIntentService.class);
        intent.putExtra(Constants.RECEIVER, addressResultReceiver);
        intent.putExtra(Constants.LOCATION_DATA_EXTRA, mLastLocation);
        startService(intent);
    }
    /*
    toast a text.
     */
    protected void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save whether the address has been requested.
        savedInstanceState.putBoolean("address-request-pending", mAddressRequested);

        // Save the address string.
        savedInstanceState.putString("location-address", mAddressOutput);
        super.onSaveInstanceState(savedInstanceState);
    }
    /*
    Receiver address data
     */
    class AddressResultReceiver extends ResultReceiver {
        public AddressResultReceiver(Handler handler) {
            super(handler);


        }

        /**
         *  Receives data sent from FetchAddressIntentService and updates the UI in MainActivity.
         */
        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            TextView longitude = (TextView) findViewById(R.id.longitude_text);
            longitude.setText("text");
            // Display the address string or an error message sent from the intent service.
            mAddressOutput = resultData.getString(Constants.RESULT_DATA_KEY);
            displayAddressOutput();

            // Show a toast message if an address was found.
            if (resultCode == Constants.SUCCESS_RESULT) {
                showToast("address found");
            }

            // Reset. Enable the Fetch Address button and stop showing the progress bar.
            mAddressRequested = false;
            updateUIWidgets();
        }
    }
}
