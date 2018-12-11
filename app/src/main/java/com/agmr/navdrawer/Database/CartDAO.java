package com.agmr.navdrawer.Database;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.agmr.navdrawer.Model.CartItem;

import java.util.List;

@Dao
public interface CartDAO {
    @Insert
    Long insertCartItem(CartItem cartItem);

    @Query("SELECT * FROM CartItem")
    List<CartItem> fetchCartItems();

    @Query("SELECT * FROM CartItem WHERE model = (:model)")
    List<CartItem> fetchItemByModel(String model);

    @Update
    void updateCartItem(CartItem cartItem);

    @Delete
    void deleteCartItem(CartItem cartItem);

}
