package com.megavak.flashchatnewfirebase.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.megavak.flashchatnewfirebase.R;


public class LoginActivity extends AppCompatActivity {

    // TODO: Add member variables here:
    FirebaseAuth auth;
    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmailView = (AutoCompleteTextView) findViewById(R.id.login_email);
        mPasswordView = (EditText) findViewById(R.id.login_password);

        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        // TODO: Grab an instance of FirebaseAuth
            auth = FirebaseAuth.getInstance();
    }

    // Executed when Sign in button pressed
    public void signInExistingUser(View v)   {
        // TODO: Call attemptLogin() here
        attemptLogin();

    }

    // Executed when Register button pressed
    public void registerNewUser(View v) {
        Intent intent = new Intent(this, RegisterActivity.class);
        finish();
        startActivity(intent);
    }

    // TODO: Complete the attemptLogin() method
    private void attemptLogin() {
         String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
            return;
        } else {
            makeToast("Login in progress");
        }

        // TODO: Use FirebaseAuth to sign in with email & password
         auth.signInWithEmailAndPassword(email,password)
                 .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                     @Override
                     public void onComplete(@NonNull Task<AuthResult> task) {
                         Log.d("FlashChat","signInWithEmail onComplete: " + task.isSuccessful());

                         if (!task.isSuccessful()){
                             Log.d("FlashChat","Problem signing in: " +task.getException());
                            // showErrorDialog("There was a problem signing in");
                         }else {
                             Intent intent = new Intent(LoginActivity.this,MainChatActivity.class);
                             finish();
                             startActivity(intent);
                         }
                     }
                 });


    }

    private void makeToast(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    // TODO: Show error on screen with an alert dialog
    /*private void showErrorDialog(String msg){
        AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
        builder.setTitle("Oops!")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setMessage(msg)
                .setPositiveButton(android.R.string.ok,null);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();



    }*/



}