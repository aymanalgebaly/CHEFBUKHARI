package com.compubase.chefbukhari.ui.fragments;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.compubase.chefbukhari.R;
import com.compubase.chefbukhari.adapters.CartAdapter;
import com.compubase.chefbukhari.models.CartModel;
import com.compubase.chefbukhari.ui.activities.HomeActivity;

import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * A simple {@link Fragment} subclass.
 */
public class CartFragment extends Fragment {


    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.rel)
    RelativeLayout rel;
    @BindView(R.id.rcv_cart)
    RecyclerView rcvCart;
    @BindView(R.id.btn_checkOut)
    Button btnCheckOut;
    @BindView(R.id.btn_keep_shopping)
    Button btnKeepShopping;
    @BindView(R.id.lin_btn)
    LinearLayout linBtn;
    @BindView(R.id.img_back_ar)
    ImageView imgBackAr;
    @BindView(R.id.txt_title)
    TextView txtTitle;

    private Unbinder unbinder;
    private HomeActivity homeActivity;
    private String iid;
    private Realm realm;
    private ArrayList<CartModel> cartModelArrayList = new ArrayList<>();
    private CartAdapter cartAdapter;
    private double price;

    private double totalPrice = 0;
    private String string;

    public CartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        homeActivity = (HomeActivity) getActivity();
        unbinder = ButterKnife.bind(this, view);

        SharedPreferences preferences = homeActivity.getSharedPreferences("user", Context.MODE_PRIVATE);

        string = preferences.getString("lan", "");


        if (string.equals("ar")) {
            imgBackAr.setVisibility(View.VISIBLE);

            Typeface typeface = ResourcesCompat.getFont(homeActivity, R.font.hacen_dalal_st_regular);

            txtTitle.setText(R.string.cart_shopping);

            btnCheckOut.setTypeface(typeface);
            btnKeepShopping.setTypeface(typeface);
            txtTitle.setTypeface(typeface);

        } else {
            imgBack.setVisibility(View.VISIBLE);

            Typeface typeface = ResourcesCompat.getFont(homeActivity, R.font.century_gothic_400);

            txtTitle.setText(R.string.cart_shopping);

            btnCheckOut.setTypeface(typeface);
            btnKeepShopping.setTypeface(typeface);
            txtTitle.setTypeface(typeface);
        }

        imgBackAr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                homeActivity.onBackPressed();
            }
        });

        Realm.init(Objects.requireNonNull(getContext()));
        realm = Realm.getDefaultInstance();

        setupRecycler();
        showData();

        return view;
    }

    private void setupRecycler() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setReverseLayout(false);
        rcvCart.setLayoutManager(linearLayoutManager);

    }

    @SuppressLint("SetTextI18n")
    private void showData() {

        cartModelArrayList.clear();

        RealmResults<CartModel> all = realm.where(CartModel.class).findAll();

        Log.i("showData: ", String.valueOf(all.size()));
        for (int i = 0; i < all.size(); i++) {

            CartModel productsModel = new CartModel();
            assert all.get(i) != null;

            productsModel.setTitle(Objects.requireNonNull(all.get(i)).getTitle());
            productsModel.setDes(Objects.requireNonNull(all.get(i)).getDes());
            productsModel.setImg1(Objects.requireNonNull(all.get(i)).getImg1());
            productsModel.setItem_price(Objects.requireNonNull(all.get(i)).getItem_price());
            productsModel.setId(Objects.requireNonNull(all.get(i)).getId());
            productsModel.setItem_number(Objects.requireNonNull(all.get(i)).getItem_number());

//            assert all.get(i) != null;
//            iid = all.get(i).getId();

            cartModelArrayList.add(productsModel);


            if (all.get(i) != null) {
                assert all.get(i) != null;
                price = all.get(i).getItem_price();
//                int pr = Integer.parseInt(String.valueOf(price));
                totalPrice = totalPrice + price;

                Log.i("ggggg: ", String.valueOf(totalPrice));


            }

        }

        cartAdapter = new CartAdapter(homeActivity);
        rcvCart.setAdapter(cartAdapter);
        cartAdapter.setData(cartModelArrayList, totalPrice);
        cartAdapter.notifyDataSetChanged();


    }

    @OnClick({R.id.img_back, R.id.btn_checkOut, R.id.btn_keep_shopping})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                homeActivity.onBackPressed();
                break;
            case R.id.btn_checkOut:

                if (cartModelArrayList.isEmpty()) {

                    Toast.makeText(homeActivity, R.string.cart_empty, Toast.LENGTH_SHORT).show();
                } else {

                    Bundle bundle = new Bundle();
                    bundle.putDouble("totalPrice", totalPrice);
                    Log.i("onClick: ", String.valueOf(totalPrice));
                    homeActivity.displaySelectedFragmentWithBackBundle(new DeliveryFragment(), bundle);
                }

                break;
            case R.id.btn_keep_shopping:
                startActivity(new Intent(homeActivity, HomeActivity.class));
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void onResume() {
        super.onResume();

        if (string.equals("ar")) {
            imgBackAr.setVisibility(View.VISIBLE);

            Typeface typeface = ResourcesCompat.getFont(homeActivity, R.font.hacen_dalal_st_regular);

            btnCheckOut.setTypeface(typeface);
            btnKeepShopping.setTypeface(typeface);
            txtTitle.setTypeface(typeface);

        } else {
            imgBack.setVisibility(View.VISIBLE);

            Typeface typeface = ResourcesCompat.getFont(homeActivity, R.font.century_gothic_400);

            btnCheckOut.setTypeface(typeface);
            btnKeepShopping.setTypeface(typeface);
            txtTitle.setTypeface(typeface);
        }
    }
}
