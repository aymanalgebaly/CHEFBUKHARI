package com.compubase.chefbukhari.ui.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.compubase.chefbukhari.R;
import com.compubase.chefbukhari.data.API;
import com.compubase.chefbukhari.helpers.RetrofitClient;
import com.compubase.chefbukhari.models.RegisterResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.ed_mail)
    EditText edMail;
    @BindView(R.id.ed_pass)
    EditText edPass;
    @BindView(R.id.txt_forgot)
    TextView txtForgot;
    @BindView(R.id.btn_signIn)
    Button btnSignIn;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.img_back_ar)
    ImageView imgBackAr;


    private SharedPreferences preferences;
    private String area, name, email, phone, city, type, img;
    private int id;
    private String string;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        preferences = getSharedPreferences("user", MODE_PRIVATE);

        string = preferences.getString("lan", "");

        if (string.equals("ar")) {
            edPass.setTextDirection(View.TEXT_DIRECTION_RTL);
            edMail.setTextDirection(View.TEXT_DIRECTION_RTL);
        }

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

        progressBar.setVisibility(View.GONE);
    }

    private void loginValidate() {

        String mail = edMail.getText().toString();
        String pass = edPass.getText().toString();

        if (TextUtils.isEmpty(mail)) {
            edMail.setError("اسم المستخدم مطلوب");
        } else if (TextUtils.isEmpty(pass)) {
            edPass.setError("كلمة المرور مطلوبه");
        } else {

            Call<ResponseBody> call2 = RetrofitClient.getInstant().create(API.class).UserLogin(mail, pass);

            call2.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                    GsonBuilder builder = new GsonBuilder();
                    Gson gson = builder.create();

                    assert response.body() != null;
                    try {

                        List<RegisterResponse> loginModelList =
                                Arrays.asList(gson.fromJson(response.body().string(), RegisterResponse[].class));
                        if (response.isSuccessful()) {

                            progressBar.setVisibility(View.VISIBLE);

                            name = loginModelList.get(0).getName();
                            email = loginModelList.get(0).getEmail();
                            id = loginModelList.get(0).getId();
                            img = loginModelList.get(0).getImg();
                            area = loginModelList.get(0).getArea();
                            city = loginModelList.get(0).getCity();
                            phone = loginModelList.get(0).getPhone();
                            type = loginModelList.get(0).getType();


                            if (type.equals("agent")){

                                sharedLogin();

                                startActivity(new Intent(LoginActivity.this, AgentDashboardActivity.class));

                                finish();

                            }else {

                                sharedLogin();

                                startActivity(new Intent(LoginActivity.this, HomeActivity.class));

                                finish();

                            }




                        }

                    } catch (Exception e) {
                        Toast.makeText(LoginActivity.this, "خطأ في البريد الالكتروني او كلمة المرور", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void sharedLogin() {
        SharedPreferences.Editor editor = getSharedPreferences("user", MODE_PRIVATE).edit();

        preferences = getSharedPreferences("user", MODE_PRIVATE);

        editor.putBoolean("login", true);

        editor.putString("id", String.valueOf(id));
        editor.putString("name", name);
        editor.putString("email", email);
        editor.putString("phone", phone);
        editor.putString("image", img);
        editor.putString("city", city);
        editor.putString("area", area);
        editor.putString("type", type);

        editor.apply();
    }

    @OnClick({R.id.img_back, R.id.txt_forgot, R.id.btn_signIn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                onBackPressed();
                break;
            case R.id.txt_forgot:
                break;
            case R.id.btn_signIn:
                loginValidate();
                break;
        }
    }
}
