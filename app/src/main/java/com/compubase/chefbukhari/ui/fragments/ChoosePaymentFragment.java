package com.compubase.chefbukhari.ui.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.compubase.chefbukhari.R;
import com.compubase.chefbukhari.ui.activities.HomeActivity;
import com.yariksoffice.lingver.Lingver;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChoosePaymentFragment extends Fragment {


    private HomeActivity homeActivity;
    private String string;

    public ChoosePaymentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_choose_payment, container, false);
        homeActivity = (HomeActivity) getActivity();

        SharedPreferences preferences = homeActivity.getSharedPreferences("user", Context.MODE_PRIVATE);

        string = preferences.getString("lan", "");


        return view;
    }

}
