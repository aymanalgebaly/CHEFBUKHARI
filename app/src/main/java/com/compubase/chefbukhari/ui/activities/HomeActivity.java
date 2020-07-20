package com.compubase.chefbukhari.ui.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Layout;
import android.text.SpannableString;
import android.text.style.AlignmentSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.compubase.chefbukhari.R;
import com.compubase.chefbukhari.models.CartModel;
import com.compubase.chefbukhari.ui.fragments.AboutFragment;
import com.compubase.chefbukhari.ui.fragments.CartFragment;
import com.compubase.chefbukhari.ui.fragments.DashboardFragment;
import com.compubase.chefbukhari.ui.fragments.FavoritesFragment;
import com.compubase.chefbukhari.ui.fragments.HomeFragment;
import com.compubase.chefbukhari.ui.fragments.LanguageFragment;
import com.compubase.chefbukhari.ui.fragments.TermsFragment;
import com.google.android.material.navigation.NavigationView;
import com.yariksoffice.lingver.Lingver;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmResults;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public TextView cartBadge;

    @BindView(R.id.cart_img)
    ImageView cartImg;

    private Realm realm;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private String string;
    private boolean login;
    private RealmResults<CartModel> all;
    private NavigationView navigationView;

    @SuppressLint("CommitPrefEdits")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        preferences = getSharedPreferences("user",MODE_PRIVATE);
        string = preferences.getString("lan", "");
        login = preferences.getBoolean("login", false);


        Realm.init(this);
        realm = Realm.getDefaultInstance();
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        cartBadge = findViewById(R.id.cart_badge);


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        Menu menu = navigationView.getMenu();

        for (int i = 0; i <menu.size() ; i++) {

            CharSequence title = menu.getItem(i).getTitle();

            if (string.equals("ar")) {
                Typeface typeface = ResourcesCompat.getFont(this,R.font.hacen_dalal_st_regular);

                navigationView.setItemTextAppearance(R.style.NavigationText);

            } else {

                Typeface typeface = ResourcesCompat.getFont(this,R.font.century_gothic_400);

                navigationView.setItemTextAppearance(R.style.NavigationText_en);

            }

        }

        if (login){
            menu.findItem(R.id.signin).setVisible(false);
            menu.findItem(R.id.signup).setVisible(false);
        }else {
            menu.findItem(R.id.signout).setVisible(false);
        }

        for (int i = 0; i < menu.size(); i++) {

            MenuItem item = menu.getItem(i);
            SpannableString s = new SpannableString(item.getTitle());

            s.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER), 0, s.length(), 0);

            item.setTitle(s);
        }

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        Objects.requireNonNull(getSupportActionBar()).setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.menu_icon);

        all = realm.where(CartModel.class).findAll();

        Log.i("ttttttt: ",String.valueOf(all.size()));

        cartBadge.setText(String.valueOf(all.size()));

        editor = getSharedPreferences("lan", MODE_PRIVATE).edit();




        HomeFragment homeFragment = new HomeFragment();
        displaySelectedFragment(homeFragment);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        if (id == R.id.dashboard) {

            if (!login){
                startActivity(new Intent(HomeActivity.this,LoginActivity.class));
            }else {

                displaySelectedFragmentWithBack(new DashboardFragment());

            }

            // Handle the camera action
        } else if (id == R.id.orders) {

            if (!login){
                startActivity(new Intent(HomeActivity.this,LoginActivity.class));
            }else {

                displaySelectedFragmentWithBack(new DashboardFragment());

            }


        } else if (id == R.id.fav) {

            if (!login){
                startActivity(new Intent(HomeActivity.this,LoginActivity.class));
            }else {
                displaySelectedFragmentWithBack(new FavoritesFragment());

            }


        } else if (id == R.id.home) {
            displaySelectedFragmentWithBack(new HomeFragment());

        }else if (id == R.id.signin) {
            startActivity(new Intent(HomeActivity.this,LoginActivity.class));

        } else if (id == R.id.signup) {
            startActivity(new Intent(HomeActivity.this,RegisterActivity.class));

        } else if (id == R.id.about) {
            displaySelectedFragmentWithBack(new AboutFragment());

        }else if (id == R.id.signout){
            SharedPreferences.Editor editor = getSharedPreferences("user", MODE_PRIVATE).edit();

            preferences = getSharedPreferences("user", MODE_PRIVATE);

//            editor.clear();

            editor.putBoolean("login", false);

            editor.apply();
            startActivity(new Intent(HomeActivity.this, MainActivity.class));
            finish();

        }else if (id == R.id.terms){
            displaySelectedFragmentWithBack(new TermsFragment());

        }else if (id == R.id.lan){
            startActivity(new Intent(HomeActivity.this,LanTwoActivity.class));
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void emptyCart() {
//        cartBadge.setText("0");
        cartBadge.setText(String.valueOf(all.size()));

        CartFragment cartFragment = new CartFragment();
//        displaySelectedFragmentWithBack(cartFragment);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.frame, cartFragment);
        fragmentTransaction.addToBackStack(null).commit();
    }

    public void displaySelectedFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame, fragment);
        fragmentTransaction.commit();
    }

    public void displaySelectedFragmentWithBack(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame, fragment);
        fragmentTransaction.addToBackStack(null).commit();
    }

    public void displaySelectedFragmentWithBackBundle(Fragment fragment,Bundle bundle) {

        fragment.setArguments(bundle);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame, fragment);
        fragmentTransaction.addToBackStack(null).commit();
    }

    @OnClick(R.id.cart_img)
    public void onViewClicked() {
        emptyCart();
    }

    @Override
    protected void onResume() {
        super.onResume();

        cartBadge.setText(String.valueOf(all.size()));

    }
}
