package com.agmr.navdrawer;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Signup extends AppCompatActivity {

    private EditText editTextEmail, editTextPassword, edFname, edLname, editTextContact, editTextAddress;
    private Button buttonSignup;
    private ProgressDialog progressDialog;
    private TextView textViewSignin;
    private FirebaseAuth firebaseAuth;
    private String email, password, userId;
    private FirebaseDatabase database;
    private DatabaseReference dbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        database = FirebaseDatabase.getInstance();
        // Initializing View Objects
        edFname = (EditText) findViewById(R.id.editTextFirstName);
        edLname = (EditText) findViewById(R.id.editTextLastName);
        editTextContact = (EditText) findViewById(R.id.editTextPhone);
        editTextAddress = (EditText) findViewById(R.id.editTextAddress);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);

        buttonSignup = (Button) findViewById(R.id.buttonSignup);
        textViewSignin = (TextView) findViewById(R.id.textViewSignin);
        progressDialog = new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();

        //if getCurrentUser does not returns null
        if (firebaseAuth.getCurrentUser() != null) {
            //that means user is already logged in
            //so close this activity
            finish();

            //and open profile activity
            startActivity(new Intent(getApplicationContext(), Profile.class));
        }

        //Button event...
        buttonSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //calling register method on click
                registerUser();
            }
        });

        //TextView event..
        textViewSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Login.class));
            }
        });
    }


    private void registerUser() {
        //getting email and password from edit texts
        email = editTextEmail.getText().toString().trim();
        password = editTextPassword.getText().toString().trim();

        //checking if email and passwords are empty
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please enter email", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter password", Toast.LENGTH_LONG).show();
            return;
        }

        //if the email and password are not empty
        //displaying a progress dialog

        progressDialog.setMessage("Registering Please Wait...");
        progressDialog.show();

        //CREATING USER EVENT...
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(Signup.this, "Successfully registered", Toast.LENGTH_LONG).show();

                    firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {


                            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                            userId = currentUser.getUid();
                            float freecoins = 10000;
                            dbRef = database.getReference("Users");
                            String name = edFname.getText().toString() + " " + edLname.getText().toString();
                            User user = new User(name, editTextContact.getText().toString(), editTextAddress.getText().toString().trim(),
                                    editTextEmail.getText().toString(), freecoins);
                            dbRef.child(userId).setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Snackbar snackbar = Snackbar
                                            .make(findViewById(R.id.RL), "Successfully Registered", Snackbar.LENGTH_LONG);
                                    progressDialog.dismiss();
                                    snackbar.show();
                                    startActivity(new Intent(getApplicationContext(), Profile.class));
                                }
                            });


                        }
                    });
                } else {
                    Toast.makeText(Signup.this, "Registration failed.Error occurred", Toast.LENGTH_LONG).show();
                }
                progressDialog.dismiss();
            }
        });
    }


}

