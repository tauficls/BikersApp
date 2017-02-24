package com.example.taufic.bikeapps.Login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
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
    AppCompatButton _signinButton;

    private static final String TAG = "SignInActivity";

    EditText emailText;
    EditText passwordText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        _signinButton = (AppCompatButton) findViewById(R.id.btn_signIn);
        emailText = (EditText) findViewById(R.id.input_email);
        passwordText = (EditText) findViewById(R.id.input_password);
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

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithEmail", task.getException());
                            Toast.makeText(login.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            onSignInSuccess();
                            finish();
                        }

                        // ...
                    }
                });

//        new android.os.Handler().postDelayed(
//                new Runnable() {
//                    public void run() {
//                        // On complete call either onSignupSuccess or onSignupFailed
//                        // depending on success
//                        Log.d("coba", "sucess");
//                        onSignInSuccess();
//
//                        // onSignupFailed();
//                        progressDialog.dismiss();
//                    }
//                }, 3000);
    }

    public void onSignInSuccess() {
        startActivity(new Intent(this, MainActivity.class));
    }


    public void onSignInFailed() {
        Toast.makeText(getBaseContext(), "login failed", Toast.LENGTH_LONG).show();

        //_signinButton.setEnabled(true);
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
