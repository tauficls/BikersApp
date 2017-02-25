package com.example.taufic.bikeapps;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.util.StringBuilderPrinter;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.taufic.bikeapps.Login.SignUp;
import com.example.taufic.bikeapps.Login.login;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
        String randomChar = "ABCDEFGHIJKLMOPQRSTUVWXYZ1234567890";
        StringBuilder random = new StringBuilder();
        Random rnd = new Random();

        while (randomChar.length() < 10) {
            int index = (int) (rnd.nextFloat() * randomChar.length());
            random.append(randomChar.charAt(index));
        }
        String result = random.toString();

        return  result;
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

        if (!validate()) {
            onAddCommunityFailed();
            return;
        }

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

        SharedPreferences sharedPref2 = getPreferences(Context.MODE_PRIVATE);
        String UID = sharedPref2.getString("UID", "null");

        community = new Community(name, description, location, UID, idCommunity);
        mDatabase = FirebaseDatabase.getInstance().getReference("Community").child(idCommunity);
        mDatabase.setValue(community);
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
