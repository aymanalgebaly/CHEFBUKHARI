package com.compubase.chefbukhari.ui.activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.compubase.chefbukhari.R;
import com.compubase.chefbukhari.data.API;
import com.compubase.chefbukhari.helpers.RetrofitClient;
import com.compubase.chefbukhari.models.OrderDetails;
import com.compubase.chefbukhari.models.UserDatum;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClientActivity extends AppCompatActivity {

    @BindView(R.id.img_user)
    ImageView imgUser;
    @BindView(R.id.ed_username)
    TextView edUsername;
    @BindView(R.id.ed_mail)
    TextView edMail;
    @BindView(R.id.ed_mobile)
    TextView edMobile;
    @BindView(R.id.lin_userData)
    LinearLayout linUserData;
    private int id_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);
        ButterKnife.bind(this);

        if (getIntent().getExtras() != null){
            id_user = getIntent().getExtras().getInt("id_user");

            Log.i( "onCreate: ", String.valueOf(id_user));
        }

        fetchData();
    }

    private void fetchData() {

        RetrofitClient.getInstant().create(API.class).user_profile(String.valueOf(id_user)).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();

                assert response.body() != null;
                try {

                    List<UserDatum> userData = Arrays.asList(gson.fromJson(response.body().string(), UserDatum[].class));
                    if (response.isSuccessful()) {

                        String name = userData.get(0).getName();
                        String email = userData.get(0).getEmail();
                        String img = userData.get(0).getImg();
                        String phone = userData.get(0).getPhone();

                        edMail.setText(email);
                        edMobile.setText(phone);
                        edUsername.setText(name);

                        Glide.with(ClientActivity.this).load(img).into(imgUser);
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