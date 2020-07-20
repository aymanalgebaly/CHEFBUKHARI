package com.compubase.chefbukhari.ui.activities;

import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import com.compubase.chefbukhari.R;
import com.compubase.chefbukhari.models.OrdersAgentResponse;
import com.compubase.chefbukhari.models.OrdersResponse;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ArrivalActivity extends AppCompatActivity implements OnMapReadyCallback {

    @BindView(R.id.txt_location)
    TextView txtLocation;
    @BindView(R.id.sp_city)
    TextView spCity;
    @BindView(R.id.sp_neighborhood)
    TextView spNeighborhood;
    @BindView(R.id.map)
    MapView map;
    private GoogleMap mMap;
    private String lan, lon;
    private String address;
    private String city, area;
    private float lat,longt;
    private SharedPreferences preferences;
    private String string;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arrival);
        ButterKnife.bind(this);
        initMap(savedInstanceState, this);


        preferences = getSharedPreferences("user", MODE_PRIVATE);

        string = preferences.getString("lan", "");

        if (getIntent().getExtras() != null) {

            OrdersAgentResponse ordersResponse = getIntent().getExtras().getParcelable("ordersResponse");

            assert ordersResponse != null;
            lan = ordersResponse.getSitelan();
            lon = ordersResponse.getSitelon();
            address = ordersResponse.getAddress();
            city = ordersResponse.getCity();
            area = ordersResponse.getArea();

        }

        lat = Float.parseFloat(lan);
        longt = Float.parseFloat(lon);

        spCity.setText(city);
        spNeighborhood.setText(area);
        txtLocation.setText(address);

        if (string.equals("ar")) {
            Typeface typeface = ResourcesCompat.getFont(this,R.font.hacen_dalal_st_regular);

            txtLocation.setTypeface(typeface);
            spNeighborhood.setTypeface(typeface);
            spCity.setTypeface(typeface);

        } else {

            Typeface typeface = ResourcesCompat.getFont(this,R.font.century_gothic_400);

            txtLocation.setTypeface(typeface);
            spNeighborhood.setTypeface(typeface);
            spCity.setTypeface(typeface);
        }
    }

    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        LatLng latLng = new LatLng(lat, longt);

        // Creating a marker
        MarkerOptions markerOptions = new MarkerOptions();

        // Setting the position for the marker
        markerOptions.position(latLng);

        // Setting the title for the marker.
        // This will be displayed on taping the marker
        markerOptions.title(latLng.latitude + " : " + latLng.longitude);

        // Clears the previously touched position
        mMap.clear();

        // Animating to the touched position
        mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16f));

        // Placing a marker on the touched position
        mMap.addMarker(markerOptions);
    }

    private void initMap(Bundle savedInstanceState, ArrivalActivity inflate) {

        MapView mapView = inflate.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        MapsInitializer.initialize(inflate);
        mapView.getMapAsync(this);

    }

}