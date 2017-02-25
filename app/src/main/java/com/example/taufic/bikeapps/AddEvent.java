package com.example.taufic.bikeapps;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

public class AddEvent extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    AppCompatButton _addButton;
    private static final String TAG = "SignUpActivity";

    EditText nameText;
    EditText locationText;
    EditText descriptionText;
    EditText dateText;

    private DatabaseReference mDatabase;
    private FirebaseAuth firebaseAuth;
    private Community community;
    private String name;
    private String description;
    private String location;
    private String date;
    private String idCommunity;

    //Generate random id
    private String generateRandomString() {
        String randomChar = "ABCDEFGHIJKLMOPQRSTUVWXYZ1234567890";
        StringBuilder random = new StringBuilder();
        Random rnd = new Random();

        while (randomChar.length() < 5) {
            int index = (int) (rnd.nextFloat() * randomChar.length());
            random.append(randomChar.charAt(index));
        }
        String result = random.toString();

        return  result;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
    }

    public void addEvent(View view) {
        Log.d(TAG, "SignUp");

        if (!validate()) {
            onAddEventFailed();
            return;
        }

        _addButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(AddEvent.this,
                R.style.AppTheme);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Community...");
        progressDialog.show();

        //Get value from text box
        name = nameText.getText().toString();
        description = descriptionText.getText().toString();
        location = locationText.getText().toString();
        date = dateText.getText().toString();

        //Get UID
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        String UID = sharedPref.getString("UID", "null");

        //Get user community
        mDatabase = FirebaseDatabase.getInstance().getReference("User").child(UID);
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                idCommunity  = user.getCommunityID();

                Event event = new Event(name, idCommunity, location, description, date);

                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Event").child(generateRandomString());
                ref.setValue(event);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    public void onAddEventSuccess() {
        startActivity(new Intent(this, MainActivity.class));
    }

    public void onAddEventFailed() {
        Toast.makeText(getBaseContext(), "Add Event Failed", Toast.LENGTH_LONG).show();

        _addButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String name = nameText.getText().toString();
        String location = locationText.getText().toString();
        String description = descriptionText.getText().toString();
        String date = dateText.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            nameText.setError("at least 3 characters");
            valid = false;
        } else {
            nameText.setError(null);
        }

        if (description.isEmpty()) {
            descriptionText.setError("do not empty");
            valid = false;
        } else {
            descriptionText.setError(null);
        }

        if (location.isEmpty()) {
            locationText.setError("do not empty");
            valid = false;
        } else {
            locationText.setError(null);
        }

        if (date.isEmpty()) {
            locationText.setError("do not empty");
            valid = false;
        } else {
            locationText.setError(null);
        }

        return valid;
    }
}
