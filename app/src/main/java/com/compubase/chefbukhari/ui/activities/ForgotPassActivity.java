package com.compubase.chefbukhari.ui.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import com.compubase.chefbukhari.R;
import com.compubase.chefbukhari.data.API;
import com.compubase.chefbukhari.helpers.RetrofitClient;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPassActivity extends AppCompatActivity {

    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.ed_mail)
    EditText edMail;
    @BindView(R.id.btn_signIn)
    Button btnSignIn;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.img_back_ar)
    ImageView imgBackAr;
    @BindView(R.id.txt_address)
    TextView txtAddress;

    private SharedPreferences preferences;
    private String string;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass);
        ButterKnife.bind(this);

        preferences = getSharedPreferences("user", MODE_PRIVATE);

        string = preferences.getString("lan", "");


        if (string.equals("ar")) {
            imgBackAr.setVisibility(View.VISIBLE);
            edMail.setTextDirection(View.TEXT_DIRECTION_RTL);
            Typeface typeface = ResourcesCompat.getFont(this, R.font.hacen_dalal_st_regular);

            txtAddress.setTypeface(typeface);
            btnSignIn.setTypeface(typeface);

        } else {
            imgBack.setVisibility(View.VISIBLE);
            edMail.setTextDirection(View.TEXT_DIRECTION_LTR);

            Typeface typeface = ResourcesCompat.getFont(this, R.font.century_gothic_400);

            txtAddress.setTypeface(typeface);
            btnSignIn.setTypeface(typeface);
        }

        imgBackAr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }

    @OnClick({R.id.img_back, R.id.btn_signIn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                onBackPressed();
                break;
            case R.id.btn_signIn:
                forgetPassword();
                break;
        }
    }

    private void forgetPassword() {

        progressBar.setVisibility(View.VISIBLE);
        String mail = edMail.getText().toString();

        if (TextUtils.isEmpty(mail)) {
            edMail.setError("ادخل البريد الالكتروني");
        } else {

            RetrofitClient.getInstant().create(API.class).forgete_password_by_email(mail).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                    if (response.isSuccessful()) {

                        try {
                            assert response.body() != null;
                            String string = response.body().string();

                            progressBar.setVisibility(View.GONE);

                            if (string.equals("True")) {
                                startActivity(new Intent(ForgotPassActivity.this, MainActivity.class));
                                Toast.makeText(ForgotPassActivity.this, "تم ارسال كلمة المرور الجديده",
                                        Toast.LENGTH_SHORT).show();
                                finish();
                            } else if (string.equals("False")) {

                                progressBar.setVisibility(View.GONE);

                                Toast.makeText(ForgotPassActivity.this, "تاكد من صحة البريد الالكتروني", Toast.LENGTH_SHORT).show();
                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                }
            });
        }
    }
}
