package com.compubase.chefbukhari.ui.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.compubase.chefbukhari.R;
import com.compubase.chefbukhari.adapters.DetailsAdapter;
import com.compubase.chefbukhari.data.API;
import com.compubase.chefbukhari.helpers.RetrofitClient;
import com.compubase.chefbukhari.helpers.TinyDB;
import com.compubase.chefbukhari.models.OrderDetails;
import com.compubase.chefbukhari.models.OrdersDetailsResponse;
import com.compubase.chefbukhari.models.OrdersResponse;
import com.compubase.chefbukhari.models.RegisterResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderDetailsActivity extends AppCompatActivity {

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
    private int id;
    private List<OrdersDetailsResponse> body;
    private DetailsAdapter ordersDashboardAdapter;
    private ArrayList<OrdersResponse> ordersResponseArray = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        ButterKnife.bind(this);

        if (getIntent().getExtras() != null){
            id = getIntent().getExtras().getInt("id");
        }

        fetchData();
        setupRecycler();
        fetchDataRecy();
    }

    private void fetchDataRecy() {

        RetrofitClient.getInstant().create(API.class).selecte_all_product_of_orders(String.valueOf(id)).enqueue(new Callback<List<OrdersDetailsResponse>>() {
            @Override
            public void onResponse(Call<List<OrdersDetailsResponse>> call, Response<List<OrdersDetailsResponse>> response) {

                if (response.isSuccessful()){

                    body = response.body();

                }
                ordersDashboardAdapter = new DetailsAdapter(OrderDetailsActivity.this);
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

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcvDetails.setLayoutManager(linearLayoutManager);

    }

    private void fetchData() {

        RetrofitClient.getInstant().create(API.class).orderDetails(id).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {


                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();

                assert response.body() != null;
                try {

                    List<OrderDetails> orderDetails = Arrays.asList(gson.fromJson(response.body().string(), OrderDetails[].class));
                    if (response.isSuccessful()) {

                        String datee = orderDetails.get(0).getDatee();
                        String deliverCode = orderDetails.get(0).getDeliverCode();
                        String status = orderDetails.get(0).getStatus();
                        String timee = orderDetails.get(0).getTimee();

                        Log.i( "onResponse: ",datee);
                        Log.i( "onResponse: ",deliverCode);
                        Log.i( "onResponse: ",status);
                        Log.i( "onResponse: ",timee);

                        txtCode.setText(deliverCode);
                        txtDate.setText(datee);
                        txtStatus.setText(status);
                        txtTime.setText(timee);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    @OnClick({R.id.img_back, R.id.img_back_ar})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                break;
            case R.id.img_back_ar:
                break;
        }
    }
}