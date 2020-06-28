package com.compubase.chefbukhari.ui.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.compubase.chefbukhari.R;
import com.yariksoffice.lingver.Lingver;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.txt_browse)
    TextView txtBrowse;
    @BindView(R.id.btn_signIn)
    Button btnSignIn;
    @BindView(R.id.txt_signUp)
    TextView txtSignUp;

    private SharedPreferences preferences;
    private String string;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        preferences = getSharedPreferences("user",MODE_PRIVATE);

        string = preferences.getString("lan", "");


    }

    @OnClick({R.id.txt_browse, R.id.btn_signIn, R.id.txt_signUp})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.txt_browse:
                startActivity(new Intent(MainActivity.this,HomeActivity.class));
                break;
            case R.id.btn_signIn:
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                break;
            case R.id.txt_signUp:
                startActivity(new Intent(MainActivity.this,RegisterActivity.class));
                break;
        }
    }
}
