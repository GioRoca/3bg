package com.agmr.navdrawer.Repository;


import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.agmr.navdrawer.Adapter.CartAdapter;
import com.agmr.navdrawer.Database.CartDatabase;
import com.agmr.navdrawer.Model.CartItem;

import java.util.List;

public class CartRepository {
    private String DB_NAME = "db_cart";
    private Context context;
    private CartDatabase cartDatabase;

    public CartRepository(Context context) {
        this.context = context;
        cartDatabase = Room.databaseBuilder(context, CartDatabase.class, DB_NAME).allowMainThreadQueries().build();
    }

    public void insertCartItem(String model, String key, String uid, String imageUrl, float price, int quantity) {
        CartItem cartItem = new CartItem();
        cartItem.setModel(model);
        cartItem.setKey(key);
        cartItem.setUid(uid);
        cartItem.setImageUrl(imageUrl);
        cartItem.setPrice(price);
        cartItem.setQuantity(quantity);
        insertCartItem(cartItem);
    }

    public void insertCartItem(final CartItem cartItem) {
        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... params) {
                cartDatabase.daoAccess().insertCartItem(cartItem);
                return null;
            }

            @Override
            protected void onPostExecute(String result) {
                Toast.makeText(context, "Added to Cart", Toast.LENGTH_LONG).show();
            }
        }.execute();

    }

    public void updateCartItem(final CartItem cartItem) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                cartDatabase.daoAccess().updateCartItem(cartItem);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);

            }
        }.execute();
    }

    public void deleteCartItem(final CartItem cartItem) {

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                cartDatabase.daoAccess().deleteCartItem(cartItem);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(context, "Item Removed", Toast.LENGTH_LONG).show();
            }
        }.execute();
    }

    public List<CartItem> fetchItemByModel(String model) {
        return cartDatabase.daoAccess().fetchItemByModel(model);
    }

    public List<CartItem> fetchCartItems() {
        return cartDatabase.daoAccess().fetchCartItems();
    }
}
