package com.compubase.chefbukhari.ui.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import com.compubase.chefbukhari.R;
import com.yariksoffice.lingver.Lingver;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LangActivity extends AppCompatActivity {


    @BindView(R.id.lan_en)
    Button lanEn;
    @BindView(R.id.lan_ar)
    Button lanAr;
    private SharedPreferences preferences;
    private String string;
    private SharedPreferences.Editor editor;
    private String lan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_lang);
        ButterKnife.bind(this);

        editor = getSharedPreferences("user", MODE_PRIVATE).edit();


        preferences = getSharedPreferences("user", MODE_PRIVATE);
        string = preferences.getString("lan", "");

        if (string.equals("ar")) {
            Typeface typeface = ResourcesCompat.getFont(this, R.font.hacen_dalal_st_regular);

            lanAr.setTypeface(typeface);
            lanEn.setTypeface(typeface);

        } else {

            Typeface typeface = ResourcesCompat.getFont(this, R.font.century_gothic_400);

            lanAr.setTypeface(typeface);
            lanEn.setTypeface(typeface);
        }

    }

    @OnClick({R.id.lan_en, R.id.lan_ar})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lan_en:

                lan = "en";

                Lingver.getInstance().setLocale(LangActivity.this, "en");
                editor.putString("lan", lan);
                startActivity(new Intent(LangActivity.this, MainActivity.class));

                editor.apply();
                break;
            case R.id.lan_ar:

                lan = "ar";

                Lingver.getInstance().setLocale(LangActivity.this, "ar");
                editor.putString("lan", lan);

                editor.apply();

                startActivity(new Intent(LangActivity.this, MainActivity.class));
                break;
        }
    }
}
