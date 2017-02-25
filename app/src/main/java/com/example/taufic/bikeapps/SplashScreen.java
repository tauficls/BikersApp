package com.example.taufic.bikeapps;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.taufic.bikeapps.Location.Geolocation;

public class SplashScreen extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        /** splashscreen setting **/
        new Handler().postDelayed(new Runnable() {

            // Using handler with postDelayed called runnable run method

            @Override
            public void run() {
                Intent i = new Intent(SplashScreen.this, Geolocation.class);
                startActivity(i);

                // close this activity
                finish();
            }
        }, 1*1000); // wait for 5 seconds
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
