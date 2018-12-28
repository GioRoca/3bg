package com.agmr.navdrawer.Adapter;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.agmr.navdrawer.Model.CartItem;
import com.agmr.navdrawer.R;
import com.agmr.navdrawer.Repository.CartRepository;
import com.squareup.picasso.Picasso;

import org.fabiomsr.moneytextview.MoneyTextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    private Context mContext;
    private List<CartItem> CartList;
    private double grandTotal;
    private AdapterView.OnItemClickListener mListener;

    public CartAdapter(Context mContext, List<CartItem> cartItemList) {
        this.mContext = mContext;
        this.CartList = cartItemList;
    }

    @Override
    public CartAdapter.CartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.prod_card, parent, false);
        return new CartViewHolder(v);
    }

    //Insertion of data goes here.....

    @Override
    public void onBindViewHolder(final CartAdapter.CartViewHolder holder, final int position) {

        final CartItem currentItem = CartList.get(position);

        // holder.brand.setText(currentItem.getBrand);
        holder.model.setText(currentItem.getModel());
        holder.quantity.setText(Integer.toString(currentItem.getQuantity()));
        holder.price.setText(Double.toString(currentItem.getPrice()));
        Picasso.get()
                .load(currentItem.getImageUrl())
                .placeholder(R.drawable.ic_launcher_background)
                .fit()
                .centerCrop()
                .into(holder.prod_img);

        //Increase Quantity






        holder.itemView.findViewById(R.id.inc).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //update quantity here
                CartRepository cR = new CartRepository(mContext);
                int currentQty = Integer.parseInt(String.valueOf(holder.quantity.getText()));

                if (currentQty < 10) {
                    int increasedQty = currentQty + 1;
                    currentItem.setQuantity(increasedQty);
                    holder.quantity.setText(Integer.toString(currentItem.getQuantity()));
                    currentItem.setSubtotal(currentItem.getPrice() * increasedQty);
                    cR.updateCartItem(currentItem);
                    notifyDataSetChanged();
                    Toast.makeText(mContext, "Subtotal:" + currentItem.getSubtotal(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mContext, "You have reached maximum quantity per item.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Decrease Quantity
        holder.itemView.findViewById(R.id.dec).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //update quantity here
                CartRepository cR = new CartRepository(mContext);
                int currentQty = Integer.parseInt(String.valueOf(holder.quantity.getText()));
                if (currentQty > 1) {
                    int decreasedQty = currentQty - 1;
                    currentItem.setQuantity(decreasedQty);
                    holder.quantity.setText(Integer.toString(currentItem.getQuantity()));
                    currentItem.setSubtotal(currentItem.getPrice() * decreasedQty);
                    cR.updateCartItem(currentItem);
                    notifyDataSetChanged();
                    Toast.makeText(mContext, "Subtotal:" + currentItem.getSubtotal(), Toast.LENGTH_SHORT).show();
                }

            }
        });

        //Confirm Dialog for deletion
        holder.itemView.findViewById(R.id.remove).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("Are you sure?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deleteItem(currentItem, holder.getAdapterPosition());
                    }

                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                AlertDialog ad = builder.create();
                ad.show();

            }

        });
        grandTotal = computeGrandTotal(CartList);
        holder.gTotal.setAmount(Float.parseFloat(String.valueOf(grandTotal)));
        Toast.makeText(mContext, "Grand Total:" + grandTotal, Toast.LENGTH_SHORT).show();

    }

    @Override
    public int getItemCount() {
        return CartList.size();
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {
        public MoneyTextView gTotal;
        public TextView model, brand, price;
        public ImageView increase, decrease, remove, prod_img;
        public EditText quantity;

        public CartViewHolder(View itemView) {
            super(itemView);
            model = (TextView) itemView.findViewById(R.id.prodModel);
            brand = (TextView) itemView.findViewById(R.id.prodBrand);
            price = (TextView) itemView.findViewById(R.id.prodPrice);
            gTotal = (MoneyTextView) ((Activity)mContext).findViewById(R.id.txtTotalValue);
            increase = (ImageView) itemView.findViewById(R.id.inc);
            decrease = (ImageView) itemView.findViewById(R.id.dec);
            quantity = (EditText) itemView.findViewById(R.id.prodQty);
            remove = (ImageView) itemView.findViewById(R.id.remove);
            prod_img = (ImageView) itemView.findViewById(R.id.prodImg);
            quantity.setFocusable(false);

        }
    }

    //Delete Item method
    private void deleteItem(final CartItem cI, final int position) {
        class DeleteItem extends AsyncTask<Void, Void, List<CartItem>> {

            @Override
            protected List<CartItem> doInBackground(Void... voids) {
                CartRepository cR = new CartRepository(mContext);
                cR.deleteCartItem(cI);
                return null;
            }

            @Override
            protected void onPostExecute(List<CartItem> cartItems) {
                super.onPostExecute(cartItems);
                CartList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, CartList.size());
            }
        }

        DeleteItem itm = new DeleteItem();
        itm.execute();
    }

    //work section here...
    public double computeGrandTotal(List<CartItem> cartItems) {


       double gTotal= 0.00;
        for (CartItem currentItem : cartItems) {
              gTotal = gTotal+currentItem.getSubtotal();
             //  Toast.makeText(mContext,currentItem.getId()+": "+gTotal,Toast.LENGTH_SHORT).show();

        }
        Toast.makeText(mContext,"Grand:"+gTotal,Toast.LENGTH_SHORT).show();
  return  gTotal;
    }


}
