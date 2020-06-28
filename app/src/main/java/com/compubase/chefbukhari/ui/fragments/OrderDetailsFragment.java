package com.compubase.chefbukhari.ui.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.compubase.chefbukhari.R;
import com.compubase.chefbukhari.adapters.DetailsAdapter;
import com.compubase.chefbukhari.adapters.OrdersDashboardAdapter;
import com.compubase.chefbukhari.data.API;
import com.compubase.chefbukhari.helpers.RetrofitClient;
import com.compubase.chefbukhari.models.OrdersDetailsResponse;
import com.compubase.chefbukhari.models.OrdersResponse;
import com.compubase.chefbukhari.ui.activities.HomeActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderDetailsFragment extends Fragment {


    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.img_back_ar)
    ImageView imgBackAr;
    @BindView(R.id.rel)
    RelativeLayout rel;
    @BindView(R.id.txt_date)
    TextView txtDate;
    @BindView(R.id.txt_time)
    TextView txtTime;
    @BindView(R.id.txt_status)
    TextView txtStatus;
    @BindView(R.id.txt_code)
    TextView txtCode;
    @BindView(R.id.rcv_details)
    RecyclerView rcvDetails;
    private HomeActivity homeActivity;
    private String string;
    private OrdersResponse orderResponse;
    private DetailsAdapter detailsAdapter;
    private String deliverCode,status,timee,datee;

    private Unbinder unbinder;

    private ArrayList<OrdersResponse>ordersResponseArrayList = new ArrayList<>();
    private ArrayList<OrdersResponse>ordersResponseArray = new ArrayList<>();
    private Integer id;
    private DetailsAdapter ordersDashboardAdapter;
    private List<OrdersDetailsResponse> body = new ArrayList<>();

    public OrderDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order_details, container, false);
        homeActivity = (HomeActivity) getActivity();
        unbinder = ButterKnife.bind(this,view);

        SharedPreferences preferences = homeActivity.getSharedPreferences("user", Context.MODE_PRIVATE);

        string = preferences.getString("lan", "");

        assert getArguments() != null;
        orderResponse = getArguments().getParcelable("orderResponse");

        assert orderResponse != null;
        datee = orderResponse.getDatee();
        timee = orderResponse.getTimee();
        status = orderResponse.getStatus();
        deliverCode = orderResponse.getDeliverCode();
        id = orderResponse.getId();

        if (string.equals("ar")) {
            imgBackAr.setVisibility(View.VISIBLE);
            txtCode.setText(String.valueOf(deliverCode));
            txtDate.setText(String.valueOf(datee));
            txtStatus.setText(String.valueOf(status));
            txtTime.setText(String.valueOf(timee));
        } else {
            imgBack.setVisibility(View.VISIBLE);
            txtCode.setText(String.valueOf(deliverCode));
            txtDate.setText(String.valueOf(datee));
            txtStatus.setText(String.valueOf(status));
            txtTime.setText(String.valueOf(timee));
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



        setupRecycler();
        fetchData();

        return view;
    }

    private void fetchData() {

        RetrofitClient.getInstant().create(API.class).selecte_all_product_of_orders(String.valueOf(id)).enqueue(new Callback<List<OrdersDetailsResponse>>() {
            @Override
            public void onResponse(Call<List<OrdersDetailsResponse>> call, Response<List<OrdersDetailsResponse>> response) {

                if (response.isSuccessful()){

                    body = response.body();

                }
                ordersDashboardAdapter = new DetailsAdapter(getActivity());
                rcvDetails.setAdapter(ordersDashboardAdapter);
                ordersDashboardAdapter.setData(body);
                ordersDashboardAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<OrdersDetailsResponse>> call, Throwable t) {

            }
        });
    }


    private void setupRecycler() {

        ordersResponseArray.clear();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        rcvDetails.setLayoutManager(linearLayoutManager);

    }



    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
