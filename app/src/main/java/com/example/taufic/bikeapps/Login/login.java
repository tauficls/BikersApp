package com.example.taufic.bikeapps.Login;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.taufic.bikeapps.MainActivity;
import com.example.taufic.bikeapps.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class login extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    Button _signinButton;

    private static final String TAG = "SignInActivity";

    EditText emailText;
    EditText passwordText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences sharedPref2 = getPreferences(Context.MODE_PRIVATE);
        String UID = sharedPref2.getString("UID", "null");
        Log.d("UID ", UID);

        super.onCreate(savedInstanceState);
        if (UID == "null") {
            setContentView(R.layout.activity_login);
            mAuth = FirebaseAuth.getInstance();
            _signinButton = (Button) findViewById(R.id.btn_signIn);
            emailText = (EditText) findViewById(R.id.input_email);
            passwordText = (EditText) findViewById(R.id.input_password);
        }
        else {
            onSignInSuccess();
        }
    }

    public void signIn(View view) {
        Log.d(TAG, "SignIn");

        if (!validate()) {
            onSignInFailed();
            return;
        }

        _signinButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(login.this,
                R.style.AppTheme);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Signing in...");
        progressDialog.show();

        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();

        /* sign up new users */
        final String TAG = "ini adalah cobaan";

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithEmail", task.getException());
                            Toast.makeText(login.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPref.edit();
                            editor.putString("UID", FirebaseAuth.getInstance().getCurrentUser().getUid());

                            editor.commit();
                            onSignInSuccess();
                            finish();
                        }
                    }
                });
    }

    public void onSignInSuccess() {
        startActivity(new Intent(this, MainActivity.class));
    }


    public void onSignInFailed() {
        Toast.makeText(getBaseContext(), "login failed", Toast.LENGTH_LONG).show();
    }

    public boolean validate() {
        boolean valid = true;

        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();


        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailText.setError("enter a valid email address");
            valid = false;
        } else {
            emailText.setError(null);
        }

        return valid;
    }

    public void changeSignUp(View view) {
        startActivity(new Intent(this, SignUp.class));
    }
}
