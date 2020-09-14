package com.compubase.chefbukhari.ui.fragments;


import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import com.compubase.chefbukhari.R;
import com.compubase.chefbukhari.helpers.SharedPrefManager;
import com.compubase.chefbukhari.helpers.SingleShotLocationProvider;
import com.compubase.chefbukhari.helpers.SpinnerUtils;
import com.compubase.chefbukhari.ui.activities.HomeActivity;
import com.google.android.gms.common.util.MapUtils;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class ClientLocationFragment extends Fragment implements OnMapReadyCallback {

    private static final int LOCATION_REQUEST_CODE = 100;


    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.rel)
    RelativeLayout rel;
    @BindView(R.id.txt_location)
    TextView txtLocation;
    @BindView(R.id.sp_city)
    Spinner spCity;
    @BindView(R.id.btn_checkOut)
    Button btnCheckOut;
    @BindView(R.id.sp_neighborhood)
    Spinner spNeighborhood;
    @BindView(R.id.map)
    MapView map;
    @BindView(R.id.img_back_ar)
    ImageView imgBackAr;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.txt_locationnnnn)
    TextView txtLocationnnnn;
    @BindView(R.id.txt_cityyyy)
    TextView txtCityyyy;
    @BindView(R.id.txt_dist)
    TextView txtDist;
    @BindView(R.id.txt_map)
    TextView txtMap;


    private HomeActivity homeActivity;
    private Unbinder unbinder;
    private String string;
    private String addressFromLatlng;
    private GoogleMap mMap;
    private MapUtils mapUtils;
    private BitmapDescriptor location_icon;
    private float lat, lon;

    private static final int REQUESTCODE = 101;
    private String item_distric;
    private List<String> cityList = new ArrayList<>();
    private List<String> districList = new ArrayList<>();
    private String item_city;
    private String address;
    private List<String> districListEn = new ArrayList<>();
    private List<String> cityListEn = new ArrayList<>();


    public ClientLocationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_client_location, container, false);
        homeActivity = (HomeActivity) getActivity();
        unbinder = ButterKnife.bind(this, view);


        SharedPreferences preferences = homeActivity.getSharedPreferences("user", Context.MODE_PRIVATE);

        string = preferences.getString("lan", "");


        assert string != null;
        if (string.equals("ar")) {
            imgBackAr.setVisibility(View.VISIBLE);

            Typeface typeface = ResourcesCompat.getFont(homeActivity, R.font.hacen_dalal_st_regular);

            txtCityyyy.setTypeface(typeface);
            txtLocationnnnn.setTypeface(typeface);
            txtDist.setTypeface(typeface);
            txtMap.setTypeface(typeface);
            txtTitle.setTypeface(typeface);
            btnCheckOut.setTypeface(typeface);

        } else {
            imgBack.setVisibility(View.VISIBLE);

            Typeface typeface = ResourcesCompat.getFont(homeActivity, R.font.century_gothic_400);

            txtCityyyy.setTypeface(typeface);
            txtLocationnnnn.setTypeface(typeface);
            txtDist.setTypeface(typeface);
            txtMap.setTypeface(typeface);
            txtTitle.setTypeface(typeface);
            btnCheckOut.setTypeface(typeface);
        }

        imgBackAr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                homeActivity.onBackPressed();
            }
        });

        cityList.add("الرياض");
        cityListEn.add("Riyadh");


        districList.add("الوادي");
        districList.add("نداء");
        districList.add("جامعة الإمام");
        districList.add("الفلاح");
        districList.add("النفل");
        districList.add("التعاون");
        districList.add("الازدهار");
        districList.add("الیاسمین");
        districList.add("الغدیر");
        districList.add("النخيل");
        districList.add("المرسلات");
        districList.add("النهضة");
        districList.add("جامعة الاميره نوره");
        districList.add("قرطبة");
        districList.add("المروج");
        districList.add("الواحة");
        districList.add("النرجس");
        districList.add("منطقه ظهرة لبن");


        districListEn.add("Alwadi");
        districListEn.add("Neda’a");
        districListEn.add("Alimam University");
        districListEn.add("Alfalah");
        districListEn.add("Alnafel");
        districListEn.add("Attaawoun");
        districListEn.add("Alezdihar");
        districListEn.add("Alyasmin");
        districListEn.add("Alghadir");
        districListEn.add("Alnakheel");
        districListEn.add("Almursalat");
        districListEn.add("Alnahda");
        districListEn.add("Princess Noura University");
        districListEn.add("Qurtoba");
        districListEn.add("Almorouj");
        districListEn.add("Alwaha");
        districListEn.add("Alnarjes");
        districListEn.add("Dahrat Laban Area");


        if (string.equals("ar")) {

            SpinnerUtils.SetSpinnerAdapter(homeActivity, spCity, cityList, android.R.layout.simple_spinner_item);
            SpinnerUtils.SetSpinnerAdapter(homeActivity, spNeighborhood, districList, android.R.layout.simple_spinner_item);


        } else {

            SpinnerUtils.SetSpinnerAdapter(homeActivity, spCity, cityListEn, android.R.layout.simple_spinner_item);
            SpinnerUtils.SetSpinnerAdapter(homeActivity, spNeighborhood, districListEn, android.R.layout.simple_spinner_item);
        }


        spCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                item_city = cityList.get(position);

                SharedPrefManager.getInstance(homeActivity).saveArrivalTime(item_city);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        spNeighborhood.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                item_distric = districList.get(position);
                SharedPrefManager.getInstance(homeActivity).saveLuggageType(item_distric);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        address = txtLocation.getText().toString();

        SharedPrefManager.getInstance(homeActivity).saveSameGender(address);

        initMap(savedInstanceState, view);


        return view;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @OnClick({R.id.img_back, R.id.btn_checkOut})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                homeActivity.onBackPressed();
                break;
            case R.id.btn_checkOut:
                address = txtLocation.getText().toString();

                SharedPrefManager.getInstance(homeActivity).saveSameGender(address);

                homeActivity.displaySelectedFragmentWithBack(new CouponFragment());
                break;
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (checkLocationPermission())
            getCurrentLocation();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUESTCODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getCurrentLocation();
                }
                break;
        }
    }

    private void initMap(Bundle savedInstanceState, View inflate) {

        MapView mapView = inflate.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        MapsInitializer.initialize(homeActivity);
        mapView.getMapAsync(this);

    }


    private boolean checkLocationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (homeActivity.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED
                    && homeActivity.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION}, LOCATION_REQUEST_CODE);
            } else
                return true;
        } else
            return true;

        return false;
    }

    private void getCurrentLocation() {


        SingleShotLocationProvider.requestSingleUpdate(homeActivity, location -> {


            SharedPrefManager.getInstance(getActivity()).saveUserLat((float) location.latitude);
            SharedPrefManager.getInstance(getActivity()).saveUserLon((float) location.longitude);


            lat = (float) location.latitude;
            lon = (float) location.longitude;

            Log.i("getCurrentLocation: ", String.valueOf(lat));
            Log.i("getCurrentLocation: ", String.valueOf(lon));

            addressFromLatlng = getAddressFromLatlng(new LatLng(location.latitude, location.longitude));

            txtLocation.setText(addressFromLatlng);

            addMarker();

        });

    }

    public String getAddressFromLatlng(LatLng latLng) {

        Geocoder geocoder;
        List<Address> addresses = null;
        geocoder = new Geocoder(getActivity(), Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5

//            Log.i("getAddressFromLatlng: ", addressFromLatlng);
            return addresses.get(0).getAddressLine(0);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }


    private void addMarker() {
        LatLng latLng = new LatLng(lat, lon);

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

    @Override
    public void onResume() {
        super.onResume();
        getCurrentLocation();

        if (string.equals("ar")) {
            imgBackAr.setVisibility(View.VISIBLE);

            Typeface typeface = ResourcesCompat.getFont(homeActivity, R.font.hacen_dalal_st_regular);

            txtCityyyy.setTypeface(typeface);
            txtLocationnnnn.setTypeface(typeface);
            txtDist.setTypeface(typeface);
            txtMap.setTypeface(typeface);
            txtTitle.setTypeface(typeface);
            btnCheckOut.setTypeface(typeface);

        } else {
            imgBack.setVisibility(View.VISIBLE);

            Typeface typeface = ResourcesCompat.getFont(homeActivity, R.font.century_gothic_400);

            txtCityyyy.setTypeface(typeface);
            txtLocationnnnn.setTypeface(typeface);
            txtDist.setTypeface(typeface);
            txtMap.setTypeface(typeface);
            txtTitle.setTypeface(typeface);
            btnCheckOut.setTypeface(typeface);
        }
    }
}
