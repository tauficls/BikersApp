package com.example.taufic.bikeapps.Location;

import android.app.IntentService;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by taufic on 2/24/2017.
 */

public class FetchAddressIntentService extends IntentService {
    private static String TAG = "FetchAddressIntentService";

    private ResultReceiver resultReceiver;
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public FetchAddressIntentService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        resultReceiver = intent.getParcelableExtra(Constants.RECEIVER);

        if (resultReceiver == null) {
            Log.wtf(TAG, "No receiver received.");
            return;
        }

        // Get the location passed to this service through an extra.
        Location location = intent.getParcelableExtra(Constants.LOCATION_DATA_EXTRA);

        if (location == null) {
            Log.wtf(TAG, "No location found");
            deliverResultToReceiver(Constants.FAILURE_RESULT, "No location found");
            return;
        }

        List<Address> addresses = null;
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(),1);

        } catch (IOException ioException){
            Log.e(TAG, "service not available", ioException);
        } catch (IllegalArgumentException illegalArgumentException) {
            Log.e(TAG, "invalid value latitude and longitude", illegalArgumentException);
        }

        // Condition no address was found
        if (addresses == null || addresses.size() == 0) {
            Log.e(TAG, "no address found");
            deliverResultToReceiver(Constants.FAILURE_RESULT, "no address found");
        } else {
            Address address = addresses.get(0);
            ArrayList<String> addressFragments = new ArrayList<>();

            for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
                addressFragments.add(address.getAddressLine(i));
            }
            Log.i(TAG, "Address found");
            deliverResultToReceiver(Constants.SUCCESS_RESULT, TextUtils.join(System.getProperty("line.seperator"), addressFragments));
        }
    }

    private void deliverResultToReceiver(int resultCode, String message) {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.RESULT_DATA_KEY, message);
        resultReceiver.send(resultCode, bundle);
    }
}