package com.compubase.chefbukhari.ui.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.compubase.chefbukhari.R;
import com.compubase.chefbukhari.data.API;
import com.compubase.chefbukhari.helpers.RetrofitClient;
import com.compubase.chefbukhari.models.OrdersAgentResponse;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SendDeliveryCodeAgentActivity extends AppCompatActivity {

    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.img_back_ar)
    ImageView imgBackAr;
    @BindView(R.id.rel)
    RelativeLayout rel;
    @BindView(R.id.txt_coupon)
    TextView txtCoupon;
    @BindView(R.id.ed_coupon)
    EditText edCoupon;
    @BindView(R.id.btn_coupon)
    Button btnCoupon;
    @BindView(R.id.btn_viewOrder)
    Button btnViewOrder;
    @BindView(R.id.btn_client)
    Button btnClient;
    private OrdersAgentResponse agent;
    private String branch,deliverCode,datee,timee;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_delivery_code_agent);
        ButterKnife.bind(this);

        SharedPreferences preferences = getSharedPreferences("user",MODE_PRIVATE);
        String string = preferences.getString("lan", "");

        agent = getIntent().getParcelableExtra("agent");

        assert agent != null;
        branch = agent.getBranch();
        deliverCode = agent.getDeliverCode();
        datee = agent.getDatee();
        timee = agent.getTimee();
        id = agent.getId();


        if (string.equals("ar")) {
            imgBackAr.setVisibility(View.VISIBLE);
        }else {
            imgBack.setVisibility(View.VISIBLE);
        }

        imgBackAr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @OnClick({R.id.img_back, R.id.img_back_ar, R.id.btn_coupon, R.id.btn_viewOrder, R.id.btn_client})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                break;
            case R.id.img_back_ar:
                break;
            case R.id.btn_coupon:
                sendDeliveryCode();
                break;
            case R.id.btn_viewOrder:
                break;
            case R.id.btn_client:
                break;
        }
    }

    private void sendDeliveryCode() {
        RetrofitClient.getInstant().create(API.class).deliver_order(deliverCode, String.valueOf(id)).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                try {
                    assert response.body() != null;
                    String string = response.body().string();

                    if (string.equals("True")){

                        Toast.makeText(SendDeliveryCodeAgentActivity.this, "Delivery Code Sent", Toast.LENGTH_LONG).show();
                    }else {

                        Toast.makeText(SendDeliveryCodeAgentActivity.this, "Delivery Code Didn't Sent", Toast.LENGTH_LONG).show();

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
}
