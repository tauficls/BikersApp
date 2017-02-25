package com.example.taufic.bikeapps;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;

public class AddCommunity extends AppCompatActivity {
    AppCompatButton _addButton;
    private static final String TAG = "SignUpActivity";

    EditText nameText;
    EditText locationText;
    EditText descriptionText;

    private DatabaseReference mDatabase;
    private FirebaseAuth firebaseAuth;
    private Community community;
    private String name;
    private String description;
    private String location;
    private String idCommunity;

    //Generate random id
    private String generateRandomString() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 18) {
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_community);

        _addButton = (AppCompatButton) findViewById(R.id.btn_signUp);
        nameText = (EditText) findViewById(R.id.input_name);
        locationText = (EditText) findViewById(R.id.input_location);
        descriptionText = (EditText) findViewById(R.id.input_description);
    }

    public void addCommunity(View view) {
        Log.d(TAG, "SignUp");

        _addButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(AddCommunity.this,
                R.style.AppTheme);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Community...");
        progressDialog.show();

        name = nameText.getText().toString();
        description = descriptionText.getText().toString();
        location = locationText.getText().toString();
        idCommunity = generateRandomString();

        community = new Community(name, description, location, FirebaseAuth.getInstance().getCurrentUser().getUid(), idCommunity);
        mDatabase = FirebaseDatabase.getInstance().getReference("Community").child(idCommunity);
        mDatabase.setValue(community);

        onAddCommunitySuccess();
    }


    public void onAddCommunitySuccess() {
        startActivity(new Intent(this, MainActivity.class));
    }

    public void onAddCommunityFailed() {
        Toast.makeText(getBaseContext(), "Add Community FAiled", Toast.LENGTH_LONG).show();

        _addButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String name = nameText.getText().toString();
        String location = locationText.getText().toString();
        String description = descriptionText.getText().toString();

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

        return valid;
    }
}
