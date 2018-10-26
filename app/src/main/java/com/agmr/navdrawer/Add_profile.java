package com.agmr.navdrawer;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * This module is for the first time users this includes free coins worth of 10,000
 **/
public class Add_profile extends Fragment {
    FirebaseUser mUser;
    private EditText fname, lname, phone, address;
    private Button submit;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDb;
    private DatabaseReference dbRef;
    private String email;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        fname = getView().findViewById(R.id.fName);
        lname = getView().findViewById(R.id.lName);
        phone = getView().findViewById(R.id.phone_number);
        address = getView().findViewById(R.id.address);
        submit = getView().findViewById(R.id.btnSubmit);
        mDb = FirebaseDatabase.getInstance();
        dbRef = mDb.getReference("Users");

        mUser = mAuth.getInstance().getCurrentUser();
        if (mUser != null) {
            float free_coins = 10000;
            email = mUser.getEmail();
            User user = new User(fname.getText().toString().trim() + " " + lname.getText().toString().trim(), phone.getText().toString().trim(), address.getText().toString().trim(), email, free_coins);
            String id = dbRef.push().getKey();
            dbRef.child(id).setValue(user);
        }
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_profile, container, false);
    }


}
