package com.compubase.chefbukhari.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.DrawableImageViewTarget;
import com.compubase.chefbukhari.R;

import java.io.IOException;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

public class SplashActivity extends AppCompatActivity {

    private SharedPreferences preferences;
    private boolean login_user;
    private boolean login_agent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        preferences = getSharedPreferences("user", MODE_PRIVATE);
        login_user = preferences.getBoolean("login", false);
        login_agent = preferences.getBoolean("login_agent", false);

        /* Create an Intent that will start the Menu-Activity. */
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */



                if (login_user){

                    startActivity(new Intent(SplashActivity.this,HomeActivity.class));
                    finish();

                } else if (login_agent){
                    startActivity(new Intent(SplashActivity.this,AgentDashboardActivity.class));
                    finish();
                }else {
                    startActivity(new Intent(SplashActivity.this,LangActivity.class));
                    finish();

                }

            }
        }, 3000);

        ImageView imageView = findViewById(R.id.img_splash);

        Glide.with(getApplicationContext()).asGif().load(R.drawable.splash_new).into(imageView);

//        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim);
//        imageView.setAnimation(animation);
    }
}
