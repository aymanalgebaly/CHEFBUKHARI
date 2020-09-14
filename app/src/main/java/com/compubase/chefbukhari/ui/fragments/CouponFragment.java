package com.compubase.chefbukhari.ui.fragments;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.compubase.chefbukhari.R;
import com.compubase.chefbukhari.data.API;
import com.compubase.chefbukhari.helpers.RequestHandler;
import com.compubase.chefbukhari.helpers.RetrofitClient;
import com.compubase.chefbukhari.helpers.SharedPrefManager;
import com.compubase.chefbukhari.models.CartModel;
import com.compubase.chefbukhari.ui.activities.HomeActivity;
import com.compubase.chefbukhari.ui.activities.LoginActivity;
import com.paytabs.paytabs_sdk.payment.ui.activities.PayTabActivity;
import com.paytabs.paytabs_sdk.utils.PaymentParams;

import java.io.IOException;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.realm.Realm;
import io.realm.RealmResults;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class CouponFragment extends Fragment {

    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.rel)
    RelativeLayout rel;
    @BindView(R.id.txt_coupon)
    TextView txtCoupon;
    @BindView(R.id.ed_coupon)
    EditText edCoupon;
    @BindView(R.id.btn_coupon)
    Button btnCoupon;
    @BindView(R.id.txt_sum)
    TextView txtSum;
    @BindView(R.id.txt_delivery)
    TextView txtDelivery;
    @BindView(R.id.txt_total)
    TextView txtTotal;
    @BindView(R.id.card)
    CardView card;
    @BindView(R.id.txt_pay)
    TextView txtPay;
    @BindView(R.id.lin_googlePay)
    LinearLayout linGooglePay;
    @BindView(R.id.lin_payTabs)
    LinearLayout linPayTabs;
    @BindView(R.id.img_back_ar)
    ImageView imgBackAr;
    @BindView(R.id.progress)
    ProgressBar progress;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.txt_summm)
    TextView txtSummm;
    @BindView(R.id.txt_srrr)
    TextView txtSrrr;
    @BindView(R.id.txt_dliveryyyy)
    TextView txtDliveryyyy;
    @BindView(R.id.txt_srr)
    TextView txtSrr;
    @BindView(R.id.txt_tooootal)
    TextView txtTooootal;
    @BindView(R.id.txt_sr)
    TextView txtSr;
    @BindView(R.id.txt_cridite)
    TextView txtCridite;
    private Unbinder unbinder;
    private HomeActivity homeActivity;
    private int totalPrice;
    private String string;
    private Realm realm;
    private String id;
    private String city, distric;
    private String address;
    private float lon, lat;
    private boolean login;

    public CouponFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_coupon, container, false);

        homeActivity = (HomeActivity) getActivity();
        unbinder = ButterKnife.bind(this, view);


        Realm.init(Objects.requireNonNull(getContext()));
        realm = Realm.getDefaultInstance();

        SharedPreferences preferences = homeActivity.getSharedPreferences("user", Context.MODE_PRIVATE);

        string = preferences.getString("lan", "");
        id = preferences.getString("id", "");
        login = preferences.getBoolean("login", false);


        if (string.equals("ar")) {
            imgBackAr.setVisibility(View.VISIBLE);

            Typeface typeface = ResourcesCompat.getFont(homeActivity, R.font.hacen_dalal_st_regular);

            txtCoupon.setTypeface(typeface);
            txtCridite.setTypeface(typeface);
            txtDliveryyyy.setTypeface(typeface);
            txtPay.setTypeface(typeface);
            txtTitle.setTypeface(typeface);
            btnCoupon.setTypeface(typeface);
            txtSr.setTypeface(typeface);
            txtSrr.setTypeface(typeface);
            txtSrrr.setTypeface(typeface);
            txtSummm.setTypeface(typeface);
            txtTooootal.setTypeface(typeface);

        } else {
            imgBack.setVisibility(View.VISIBLE);

            Typeface typeface = ResourcesCompat.getFont(homeActivity, R.font.century_gothic_400);

            txtCoupon.setTypeface(typeface);
            txtCridite.setTypeface(typeface);
            txtDliveryyyy.setTypeface(typeface);
            txtPay.setTypeface(typeface);
            txtTitle.setTypeface(typeface);
            btnCoupon.setTypeface(typeface);
            txtSr.setTypeface(typeface);
            txtSrr.setTypeface(typeface);
            txtSrrr.setTypeface(typeface);
            txtSummm.setTypeface(typeface);
            txtTooootal.setTypeface(typeface);

        }
        imgBackAr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                homeActivity.onBackPressed();
            }
        });

        SharedPrefManager sharedPrefManager = SharedPrefManager.getInstance(homeActivity);
        totalPrice = sharedPrefManager.getTotalPrice();

        txtSum.setText(String.valueOf(totalPrice));

        String delivery = txtDelivery.getText().toString();

        double d_delivery = Double.parseDouble(delivery);

        double d_totalPrice = Double.parseDouble(String.valueOf(totalPrice));

        double d_total_price_final = d_delivery + d_totalPrice;

        txtTotal.setText(String.valueOf(d_total_price_final));


        city = SharedPrefManager.getInstance(homeActivity).getArrivalTime();
        distric = SharedPrefManager.getInstance(homeActivity).getLuggageType();
        address = SharedPrefManager.getInstance(homeActivity).getSameGender();


        lat = SharedPrefManager.getInstance(homeActivity).getLat();
        lon = SharedPrefManager.getInstance(homeActivity).getLon();

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @OnClick({R.id.img_back, R.id.btn_coupon, R.id.lin_googlePay, R.id.lin_payTabs})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                homeActivity.onBackPressed();
                break;
            case R.id.btn_coupon:
                useCoupon();
                break;
            case R.id.lin_googlePay:
//                googlePay();
//                functionVolly();
                break;
            case R.id.lin_payTabs:
                if (login) {

                    Payment();

                } else {
                    Toast.makeText(homeActivity, R.string.please_sign_in_first, Toast.LENGTH_SHORT).show();

                    startActivity(new Intent(homeActivity, LoginActivity.class));
                }
                break;
        }
    }

    private void googlePay() {

    }

    private void useCoupon() {

        progress.setVisibility(View.VISIBLE);

        String couponCode = edCoupon.getText().toString();

        RetrofitClient.getInstant().create(API.class).coupon(couponCode, totalPrice)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                        try {
                            assert response.body() != null;
                            String price = response.body().string();

                            totalPrice = Integer.parseInt(price);

                            progress.setVisibility(View.GONE);

                            txtTotal.setText(String.valueOf(totalPrice));

                            Toast.makeText(homeActivity, "تم حساب نسبة الخصم", Toast.LENGTH_SHORT).show();

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
    }

    private void functionVolly() {

        RealmResults<CartModel> all = realm.where(CartModel.class).findAll();


        StringBuilder GET_JSON_DATA_HTTP_URL =
                new StringBuilder("http://app.chefbukhari.com/sheifbokhary.asmx/insert_orders?id_user=" +
                        id + "&address=" + address + "&totle_price=" + totalPrice + "&area=" + distric + "&lan=" +
                        String.valueOf(lat) + "&lon=" + String.valueOf(lon) + "&copon_code="
                        + edCoupon.getText().toString()
                        + "&city=" + city + "&branch=" + distric);


        Log.i("functionVolly: ", id + "/" + address + "/" + totalPrice + "/" +
                distric + "/" + edCoupon.getText().toString()
                + "/" + city
                + "/" + distric);

        for (int i = 0; i <= all.size() - 1; i++) {
            assert all.get(i) != null;
            GET_JSON_DATA_HTTP_URL.append("&id_product=").append(String.valueOf(all.get(i).getId()));
            GET_JSON_DATA_HTTP_URL.append("&item_number=").append(String.valueOf(all.get(i).getItem_number()));
            GET_JSON_DATA_HTTP_URL.append("&item_price=").append(String.valueOf(all.get(i).getItem_price()));
            GET_JSON_DATA_HTTP_URL.append("&extra_request=").append(String.valueOf(all.get(i).getExtra_request()));

        }
//        for (int i = 0; i <= all.size() - 1; i++) {
//        }
//        for (int i = 0; i <= all.size() - 1; i++) {
//        }
//        for (int i = 0; i <= all.size() - 1; i++) {
//        }

        Log.i("sdsdsdsdsdsdsdsdsd", "functionVolly: " + GET_JSON_DATA_HTTP_URL.toString());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, GET_JSON_DATA_HTTP_URL.toString(),

                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if (response.equals("True")) {


                            Toast.makeText(getContext(), "تم ارسال الطلب", Toast.LENGTH_LONG).show();

                            realm.executeTransaction(new Realm.Transaction() {
                                @Override
                                public void execute(@NonNull Realm realm) {
                                    RealmResults<CartModel> result = realm.where(CartModel.class).findAll();
                                    result.deleteAllFromRealm();
                                }
                            });

                            startActivity(new Intent(homeActivity, HomeActivity.class));
                            homeActivity.finish();

//                                if (advSearchRadioBtn.isChecked()) {
//                                    Payment();
//                                } else {
//                                    Toast.makeText(getContext(), "تم ارسال الطلب", Toast.LENGTH_LONG).show();
//                                    startActivity(new Intent(getContext(), HomeActivity.class));
//                                    Objects.requireNonNull(getActivity()).finish();
//                                }
                        }

                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.i("sdsdsdsdsd", "onErrorResponse: " + error.toString());
                Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();

            }

        });

        RequestHandler.getInstance(getActivity()).addToRequestQueue(stringRequest);

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PaymentParams.PAYMENT_REQUEST_CODE) {
            Log.e("Tag", data.getStringExtra(PaymentParams.RESPONSE_CODE));
            Log.e("Tag", data.getStringExtra(PaymentParams.TRANSACTION_ID));


            functionVolly();

//            realm.executeTransaction(new Realm.Transaction() {
//                @Override
//                public void execute(@NonNull Realm realm) {
//                    RealmResults<CartModel> result = realm.where(CartModel.class).findAll();
//                    result.deleteAllFromRealm();
//                }
//            });
//
//            startActivity(new Intent(getContext(), HomeActivity.class));
//            Objects.requireNonNull(getActivity()).finish();


            if (data.hasExtra(PaymentParams.TOKEN) && !data.getStringExtra(PaymentParams.TOKEN).isEmpty()) {
                Log.e("Tag", data.getStringExtra(PaymentParams.TOKEN));
                Log.e("Tag", data.getStringExtra(PaymentParams.CUSTOMER_EMAIL));
                Log.e("Tag", data.getStringExtra(PaymentParams.CUSTOMER_PASSWORD));

                Toast.makeText(homeActivity, "لم تتم عمليه الدفع", Toast.LENGTH_SHORT).show();


            }
        }
    }


    private void Payment() {

        Intent in = new Intent(getActivity(), PayTabActivity.class);
        in.putExtra(PaymentParams.MERCHANT_EMAIL, "chefbukhariSA@gmail.com"); //this a demo account for testing the sdk
        in.putExtra(PaymentParams.SECRET_KEY, "vjfINYY6BIPDKpErbi9f1ehkv6VON40jnf2bd0hPI7stw7UIp4XrieMNkIC8MCs9Sul6uCVdKoHgvBZVFWnYb9MvSoEE16OTB9Z6");//Add your Secret Key Here
        in.putExtra(PaymentParams.LANGUAGE, string);
        in.putExtra(PaymentParams.TRANSACTION_TITLE, "Payment");
        in.putExtra(PaymentParams.AMOUNT, Double.valueOf(totalPrice));

        in.putExtra(PaymentParams.CURRENCY_CODE, "SAR");
        in.putExtra(PaymentParams.CUSTOMER_PHONE_NUMBER, "009660554679555");
        in.putExtra(PaymentParams.CUSTOMER_EMAIL, "chefbukhariSA@gmail.com");
        in.putExtra(PaymentParams.ORDER_ID, "123456");
        in.putExtra(PaymentParams.PRODUCT_NAME, "Product 1, Product 2");

//Billing Address
        in.putExtra(PaymentParams.ADDRESS_BILLING, "Flat 1,Building 123, Road 2345");
        in.putExtra(PaymentParams.CITY_BILLING, "riyadh");
        in.putExtra(PaymentParams.STATE_BILLING, "riyadh");
        in.putExtra(PaymentParams.COUNTRY_BILLING, "SAR");
        in.putExtra(PaymentParams.POSTAL_CODE_BILLING, "21577"); //Put Country Phone code if Postal code not available '00973'

//Shipping Address
        in.putExtra(PaymentParams.ADDRESS_SHIPPING, "Flat 1,Building 123, Road 2345");
        in.putExtra(PaymentParams.CITY_SHIPPING, "riyadh");
        in.putExtra(PaymentParams.STATE_SHIPPING, "riyadh");
        in.putExtra(PaymentParams.COUNTRY_SHIPPING, "SAR");
        in.putExtra(PaymentParams.POSTAL_CODE_SHIPPING, "21577"); //Put Country Phone code if Postal code not available '00973'

//Payment Page Style
        in.putExtra(PaymentParams.PAY_BUTTON_COLOR, "#A73338");
//        in.putExtra(PaymentParams.THEME, PaymentParams.THEME_LIGHT);

//Tokenization
        in.putExtra(PaymentParams.IS_TOKENIZATION, true);
        startActivityForResult(in, PaymentParams.PAYMENT_REQUEST_CODE);

    }
}
