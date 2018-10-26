package com.agmr.navdrawer;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.fabiomsr.moneytextview.MoneyTextView;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class EyeglassAdapter extends RecyclerView.Adapter<EyeglassAdapter.EyeglassViewHolder> {
    private Context mContext;
    private List<Eyeglass> EyeglassesList;
    private AdapterView.OnItemClickListener mListener;
    private String imgUrl;
    private DatabaseReference mDatabaseRefProd;
    private FirebaseUser mUser;
    private FirebaseAuth mAuth;
    private Query userQ;
    private ValueEventListener mDBListener;

    //Constructor
    public EyeglassAdapter(Context mContext, List<Eyeglass> EyeglassesList) {
        this.mContext = mContext;
        this.EyeglassesList = EyeglassesList;
    }

    @Override
    public EyeglassViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.product_view, parent, false);
        return new EyeglassViewHolder(v);
    }

    @Override
    public void onBindViewHolder(EyeglassViewHolder holder, int position) {
        //local db

        //Placing to product view xml file
        final Eyeglass currentEyeglass = EyeglassesList.get(position);

        holder.brand.setText(currentEyeglass.getmBrand());
        holder.model.setText(currentEyeglass.getmModel());
        holder.price.setAmount(currentEyeglass.getmPrice());
        Picasso.get()
                .load(currentEyeglass.getmImageUrl())
                .placeholder(R.drawable.ic_launcher_background)
                .fit()
                .centerCrop()
                .into(holder.prod_img);

        //Add to cart Button Event

        holder.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUser = mAuth.getInstance().getCurrentUser();
                mDatabaseRefProd = FirebaseDatabase.getInstance().getReference("products/");

                String emailQ = mUser.getEmail();
                int specqty = +1;
                //Wait ka lang dyan... may problema pa to
                addToCart(emailQ, currentEyeglass.getmModel(), specqty, currentEyeglass.getmPrice());

            }
        });

    }

    @Override
    public int getItemCount() {
        return EyeglassesList.size();
    }

    private boolean addToCart(String email, String model, int qty, float price) {
        mDatabaseRefProd = FirebaseDatabase.getInstance().getReference("carts/");
        Cart cart = new Cart(email, model, price, qty);
        String id = mDatabaseRefProd.push().getKey();
        mDatabaseRefProd.child(email).child(id).setValue(cart);
        return true;
    }

    //View Holder Class
    public class EyeglassViewHolder extends RecyclerView.ViewHolder {

        public TextView brand, model;
        public MoneyTextView price;
        public ImageView prod_img;
        public Button addButton;

        public EyeglassViewHolder(View itemView) {
            super(itemView);

            brand = itemView.findViewById(R.id.txtViewBrand);
            model = itemView.findViewById(R.id.txtViewModel);
            price = itemView.findViewById(R.id.txtViewPrice);
            prod_img = itemView.findViewById(R.id.productImage);

            addButton = itemView.findViewById(R.id.btn_add);


        }


    }


}
