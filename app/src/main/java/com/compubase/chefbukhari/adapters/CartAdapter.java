package com.compubase.chefbukhari.adapters;

import android.annotation.SuppressLint;
import android.content.Context;

import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.compubase.chefbukhari.R;
import com.compubase.chefbukhari.helpers.SharedPrefManager;
import com.compubase.chefbukhari.models.CartModel;
import com.compubase.chefbukhari.ui.activities.HomeActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Handler;

import io.realm.Realm;
import io.realm.RealmResults;


public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolderCart> {

    private Context context;
    private Realm realm;
    private List<CartModel> cartModels;
    private SharedPrefManager sharedPrefManager;
    private SharedPreferences preferences;
    private List<Double> totalPriceList = new ArrayList<>();
    private double totallPrice;

    public CartAdapter(Context context) {
        this.context = context;
    }

    public void setData(ArrayList<CartModel> cartModelArrayList, double totalPrice) {
        this.cartModels = cartModelArrayList;
        this.totallPrice = totalPrice;

        if (cartModelArrayList.size() > 0)
            for (int i = 0; i < cartModels.size(); i++) {
                totalPriceList.add(Double.parseDouble(String.valueOf(cartModels.get(i).getItem_price())));
            }

    }

    @NonNull
    @Override
    public ViewHolderCart onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        context = viewGroup.getContext();
        realm = Realm.getDefaultInstance();
        sharedPrefManager = SharedPrefManager.getInstance(context);
        preferences = context.getSharedPreferences("user",Context.MODE_PRIVATE);
        View view = LayoutInflater.from(context).inflate(R.layout.cart_design, viewGroup, false);
        return new ViewHolderCart(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolderCart viewHolderCart, @SuppressLint("RecyclerView") final int i) {

        final CartModel cartModel = cartModels.get(i);


        String string = preferences.getString("lan", "");

        if (string.equals("ar")){
            viewHolderCart.title.setGravity(Gravity.START | Gravity.BOTTOM | Gravity.CENTER_VERTICAL);
            viewHolderCart.title.setText(cartModel.getTitle());

            Typeface typeface = ResourcesCompat.getFont(context, R.font.hacen_dalal_st_regular);

            viewHolderCart.price.setTypeface(typeface);
            viewHolderCart.quntity.setTypeface(typeface);
            viewHolderCart.title.setTypeface(typeface);

        }else {
            viewHolderCart.title.setGravity(Gravity.END | Gravity.BOTTOM | Gravity.CENTER_VERTICAL);

            viewHolderCart.title.setText(cartModel.getTitle());

            Typeface typeface = ResourcesCompat.getFont(context, R.font.century_gothic_400);

            viewHolderCart.price.setTypeface(typeface);
            viewHolderCart.quntity.setTypeface(typeface);
            viewHolderCart.title.setTypeface(typeface);

        }


        viewHolderCart.quntity.setText(cartModel.getItem_number());
        viewHolderCart.price.setText(String.valueOf(cartModel.getItem_price()));
        Glide.with(context).load(cartModel.getImg1()).placeholder(R.drawable.inside_app_logo).into(viewHolderCart.img);


        int quntity = Integer.parseInt((String) viewHolderCart.quntity.getText());
        double total = Double.parseDouble((String) viewHolderCart.price.getText());

        double totalll = quntity * total;

        totalPriceList.set(i, totalll);

        totallPrice = getTotalPrice();


        sharedPrefManager.saveTotalPrice((int) totallPrice);


        viewHolderCart.img_min.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int quntity = Integer.parseInt((String) viewHolderCart.quntity.getText());
                double total_price = Double.parseDouble((String) viewHolderCart.price.getText());

                double price = total_price / quntity;

                if (quntity >= 2) {
                    quntity -= 1;

                    total_price = quntity * price;

                    totalPriceList.set(i, total_price);

                    totallPrice = getTotalPrice();


                    sharedPrefManager.saveTotalPrice((int) totallPrice);


                    viewHolderCart.quntity.setText(String.valueOf(quntity));

                }


            }
        });

        viewHolderCart.img_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int quntity = Integer.parseInt((String) viewHolderCart.quntity.getText());

                quntity += 1;

                double price = quntity * Double.parseDouble(String.valueOf(cartModel.getItem_price()));

                totalPriceList.set(i, price);

                totallPrice = getTotalPrice();


                sharedPrefManager.saveTotalPrice((int) totallPrice);


                viewHolderCart.quntity.setText(String.valueOf(quntity));

                }
        });

        viewHolderCart.img_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(@NonNull Realm realm) {
                        RealmResults<CartModel> result = realm.where(CartModel.class).findAll();
                        result.deleteFromRealm(i);
                        cartModels.remove(i);

                        RealmResults<CartModel> all = realm.where(CartModel.class).findAll();

                        HomeActivity homeActivity = (HomeActivity) context;
                        homeActivity.cartBadge.setText(String.valueOf(all.size()));
                        notifyDataSetChanged();
                    }
                });

            }
        });


    }

    @Override
    public int getItemCount() {
        return cartModels != null ? cartModels.size() : 0;
    }


    public class ViewHolderCart extends RecyclerView.ViewHolder {
        ImageView img,img_delete;

        LinearLayout img_min,img_plus;
        TextView title, price, quntity;

        public ViewHolderCart(@NonNull View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.img_cart);
            title = itemView.findViewById(R.id.txt_title);
            price = itemView.findViewById(R.id.txt_price);
            img_delete = itemView.findViewById(R.id.img_delete);
            quntity = itemView.findViewById(R.id.txt_num);

            img_min = itemView.findViewById(R.id.btn_min);
            img_plus = itemView.findViewById(R.id.btn_plus);

        }
    }

    public double getTotalPrice() {
        double total = 0;

        for (int i = 0; i < totalPriceList.size(); i++) {

            total = total + totalPriceList.get(i);
        }
        return total;
    }

}
