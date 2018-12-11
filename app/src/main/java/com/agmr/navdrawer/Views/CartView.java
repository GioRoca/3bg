package com.agmr.navdrawer.Views;


import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.agmr.navdrawer.Adapter.CartAdapter;
import com.agmr.navdrawer.Model.CartItem;
import com.agmr.navdrawer.R;
import com.agmr.navdrawer.Repository.CartRepository;
import com.agmr.navdrawer.ViewDisabler;

import java.net.InterfaceAddress;
import java.util.ArrayList;
import java.util.List;

public class CartView extends AppCompatActivity {
    private RecyclerView rView;
    private CartAdapter mAdapter;
    private List<CartItem> mCartItems;
    private List<CartItem> cartList;
    private Context mContext;
    private View emptyView , v;

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_view);
        mContext = getApplicationContext();
        rView = (RecyclerView)findViewById(R.id.recyclerView);
        mCartItems = new ArrayList<>();
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rView.setHasFixedSize(true);
        rView.setLayoutManager(layoutManager);
        getItems();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    public void getItems() {
        class GetItems extends AsyncTask<Void, Void, List<CartItem>> {
            @Override
            protected List<CartItem> doInBackground(Void... voids) {
                CartRepository cR = new CartRepository(mContext);
                mCartItems = cR.fetchCartItems();
                return cR.fetchCartItems();
            }

            @Override
            protected void onPostExecute(List<CartItem> cartItems) {
                super.onPostExecute(cartItems);
                mCartItems.clear();
                CartAdapter adapter = new CartAdapter(CartView.this, cartItems);
                if (cartItems.size() == 0){
                    findViewById(R.id.empty).setVisibility(View.VISIBLE);
                    findViewById(R.id.totalLayout).setVisibility(View.GONE);
                }else{
                    findViewById(R.id.empty).setVisibility(View.GONE);
                    findViewById(R.id.totalLayout).setVisibility(View.VISIBLE);
                }
                rView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        }
        GetItems gI = new GetItems();
        gI.execute();


    }


}
