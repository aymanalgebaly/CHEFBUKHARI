package com.compubase.chefbukhari.ui.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.compubase.chefbukhari.R;
import com.yariksoffice.lingver.Lingver;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LanTwoActivity extends AppCompatActivity {

    @BindView(R.id.lan_en)
    Button lanEn;
    @BindView(R.id.lan_ar)
    Button lanAr;
    private String string;
    private SharedPreferences.Editor editor;
    private String lan;

    @SuppressLint("CommitPrefEdits")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_lan_two);
        ButterKnife.bind(this);


        editor = getSharedPreferences("user", MODE_PRIVATE).edit();


    }

    @OnClick({R.id.lan_en, R.id.lan_ar})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lan_en:

                lan = "en";

                Lingver.getInstance().setLocale(LanTwoActivity.this, lan);
                editor.putString("lan", lan);
                startActivity(new Intent(LanTwoActivity.this, HomeActivity.class));

                editor.apply();
                break;
            case R.id.lan_ar:


                lan = "ar";


                Lingver.getInstance().setLocale(LanTwoActivity.this, lan);
                editor.putString("lan", lan);

                editor.apply();

                startActivity(new Intent(LanTwoActivity.this, HomeActivity.class));
                break;
        }
    }
}
