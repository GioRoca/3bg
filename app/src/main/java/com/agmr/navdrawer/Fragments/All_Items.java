package com.agmr.navdrawer.Fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.agmr.navdrawer.Adapter.EyeglassAdapter;
import com.agmr.navdrawer.Model.Eyeglass;
import com.agmr.navdrawer.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;

public class All_Items extends Fragment {
    LinearLayoutManager layoutManager;
    private RecyclerView mRecyclerView;
    private EyeglassAdapter mAdapter;
    private DatabaseReference mDatabaseRefProd;
    private List<Eyeglass> mEyeglasses;
    private FirebaseStorage mStorage;
    private ValueEventListener mDBListener;
    private Query menQuery;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_all_items, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("All Products");

        layoutManager = new LinearLayoutManager(getActivity().getBaseContext(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(layoutManager);

        mEyeglasses = new ArrayList<>();
        mAdapter = new EyeglassAdapter(getActivity().getBaseContext(), mEyeglasses);
        mRecyclerView.setAdapter(mAdapter);
        mStorage = FirebaseStorage.getInstance();
        mDatabaseRefProd = FirebaseDatabase.getInstance().getReference("products/");


        //menQuery = mDatabaseRefProd.orderByChild("mGender");

        mDBListener = mDatabaseRefProd.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mEyeglasses.clear();

                for (DataSnapshot postSnapShot : dataSnapshot.getChildren()) {
                    Eyeglass eyeglass = postSnapShot.getValue(Eyeglass.class);
                    eyeglass.setmKey(postSnapShot.getKey());
                    mEyeglasses.add(eyeglass);
                }

                mAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getActivity(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
