package com.agmr.navdrawer.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.agmr.navdrawer.Model.CartItem;
import com.agmr.navdrawer.Model.Eyeglass;
import com.agmr.navdrawer.R;
import com.agmr.navdrawer.Repository.CartRepository;
import com.agmr.navdrawer.Views.Login;
import com.agmr.navdrawer.Views.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.fabiomsr.moneytextview.MoneyTextView;

import java.util.List;

import static android.icu.text.DisplayContext.LENGTH_SHORT;

public class EyeglassAdapter extends RecyclerView.Adapter<EyeglassAdapter.EyeglassViewHolder> {
    private Context mContext;
    private List<Eyeglass> EyeglassesList;
    private List<CartItem> cList;
    private AdapterView.OnItemClickListener mListener;
    private String imgUrl;
    private DatabaseReference mDatabaseRefProd;
    private FirebaseUser mUser;
    private FirebaseAuth mAuth;
    private Query userQ;
    private ValueEventListener mDBListener;
    private CartRepository cR;

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

    //Binding Data to view
    @Override
    public void onBindViewHolder(final EyeglassViewHolder holder, int position) {
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

                String UserId = mUser.getUid();
                int specqty = 1;
                //Wait ka lang dyan... may problema pa to

                if (UserId.isEmpty()) {
                    //Insert snackbar message here... and redirect to login page
                    Toast.makeText(mContext, "User id not found", Toast.LENGTH_LONG).show();

                } else {

                    if (isItemExisted(currentEyeglass.getmModel()) == false) {
                        addToCart(currentEyeglass.getmModel(), currentEyeglass.getmKey(), UserId, currentEyeglass.getmImageUrl(), currentEyeglass.getmPrice(), specqty);
                    }
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return EyeglassesList.size();
    }

    //ONGOING
    private boolean isItemExisted(String model) {
        List<CartItem> cI = cR.fetchItemByModel(model);
        for (CartItem temp : cI) {
            if (temp.getModel().equals(model)) {
                temp.setQuantity(temp.getQuantity() + 1);
                cR.updateCartItem(temp);
                Toast.makeText(mContext, "Item Existed on your cart.", Toast.LENGTH_SHORT).show();
                return true;
            }
        }
        return false;
    }

    private boolean addToCart(String model, String key, String uid, String imageUrl, float price, int quantity) {
        cR.insertCartItem(model, key, uid, imageUrl, price, quantity);
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
            cR = new CartRepository(mContext);

        }


    }


}
