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

import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import com.compubase.chefbukhari.R;
import com.compubase.chefbukhari.ui.activities.HomeActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class TermsFragment extends Fragment {


    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.img_back_ar)
    ImageView imgBackAr;
    @BindView(R.id.rel)
    RelativeLayout rel;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.txt_one)
    TextView txtOne;
    @BindView(R.id.txt_two)
    TextView txtTwo;
    @BindView(R.id.txt_three)
    TextView txtThree;
    @BindView(R.id.txt_foyr)
    TextView txtFoyr;
    private HomeActivity homeActivity;
    private String string;

    private Unbinder unbinder;

    public TermsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_terms, container, false);
        homeActivity = (HomeActivity) getActivity();

        unbinder = ButterKnife.bind(this, view);
        SharedPreferences preferences = homeActivity.getSharedPreferences("user", Context.MODE_PRIVATE);

        string = preferences.getString("lan", "");

        if (string.equals("ar")) {
            imgBackAr.setVisibility(View.VISIBLE);

            Typeface typeface = ResourcesCompat.getFont(homeActivity, R.font.hacen_dalal_st_regular);

            txtFoyr.setTypeface(typeface);
            txtOne.setTypeface(typeface);
            txtThree.setTypeface(typeface);
            txtTwo.setTypeface(typeface);
            txtTitle.setTypeface(typeface);

        } else {
            imgBack.setVisibility(View.VISIBLE);

            Typeface typeface = ResourcesCompat.getFont(homeActivity, R.font.century_gothic_400);

            txtFoyr.setTypeface(typeface);
            txtOne.setTypeface(typeface);
            txtThree.setTypeface(typeface);
            txtTwo.setTypeface(typeface);
            txtTitle.setTypeface(typeface);
        }

        imgBackAr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                homeActivity.onBackPressed();
            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
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
}
