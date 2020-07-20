package com.compubase.chefbukhari.ui.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import com.compubase.chefbukhari.R;
import com.compubase.chefbukhari.ui.activities.HomeActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class DeliveryFragment extends Fragment {


    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.rel)
    RelativeLayout rel;
    @BindView(R.id.card_pickUp)
    CardView cardPickUp;
    @BindView(R.id.txt_client)
    TextView txtClient;
    @BindView(R.id.card_clientLocation)
    CardView cardClientLocation;
    @BindView(R.id.img_back_ar)
    ImageView imgBackAr;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.txt_method)
    TextView txtMethod;
    @BindView(R.id.txt_pick)
    TextView txtPick;
    private Unbinder unbinder;
    private HomeActivity homeActivity;
    private String string;

    public DeliveryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_delivery, container, false);
        homeActivity = (HomeActivity) getActivity();
        unbinder = ButterKnife.bind(this, view);

        SharedPreferences preferences = homeActivity.getSharedPreferences("user", Context.MODE_PRIVATE);

        string = preferences.getString("lan", "");

        if (string.equals("ar")) {
            imgBackAr.setVisibility(View.VISIBLE);

            Typeface typeface = ResourcesCompat.getFont(homeActivity, R.font.hacen_dalal_st_regular);

            txtClient.setTypeface(typeface);
            txtMethod.setTypeface(typeface);
            txtPick.setTypeface(typeface);
            txtTitle.setTypeface(typeface);

        } else {
            imgBack.setVisibility(View.VISIBLE);

            Typeface typeface = ResourcesCompat.getFont(homeActivity, R.font.century_gothic_400);

            txtClient.setTypeface(typeface);
            txtMethod.setTypeface(typeface);
            txtPick.setTypeface(typeface);
            txtTitle.setTypeface(typeface);
        }
        imgBackAr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                homeActivity.onBackPressed();
            }
        });

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @OnClick({R.id.img_back, R.id.card_pickUp, R.id.card_clientLocation})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                homeActivity.onBackPressed();
                break;
            case R.id.card_pickUp:
                homeActivity.displaySelectedFragmentWithBack(new PickUpFragment());
                break;
            case R.id.card_clientLocation:
                homeActivity.displaySelectedFragmentWithBack(new ClientLocationFragment());
                break;
        }
    }
}
