package com.compubase.chefbukhari.ui.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import com.compubase.chefbukhari.R;
import com.compubase.chefbukhari.data.API;
import com.compubase.chefbukhari.helpers.RetrofitClient;
import com.compubase.chefbukhari.helpers.SharedPrefManager;
import com.compubase.chefbukhari.helpers.SpinnerUtils;
import com.compubase.chefbukhari.models.CartModel;
import com.compubase.chefbukhari.models.ProductsModel;
import com.compubase.chefbukhari.models.ProductsSizeModel;
import com.compubase.chefbukhari.ui.activities.HomeActivity;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class SelectedItemFragment extends Fragment {


    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.main_slider)
    SliderLayout mainSlider;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.price)
    TextView price;
    @BindView(R.id.desc)
    TextView desc;
    @BindView(R.id.rating)
    RatingBar rating;
    @BindView(R.id.ed_extra_request)
    EditText edExtraRequest;
    @BindView(R.id.img_min)
    RelativeLayout imgMin;
    @BindView(R.id.number)
    TextView number;
    @BindView(R.id.img_plus)
    ImageView imgPlus;
    @BindView(R.id.btn_add)
    Button btnAdd;
    @BindView(R.id.custom_indicator)
    PagerIndicator customIndicator;
    @BindView(R.id.img_back_ar)
    ImageView imgBackAr;
    @BindView(R.id.txt_sr)
    TextView txtSr;
    @BindView(R.id.txt_details)
    TextView txtDetails;
    @BindView(R.id.txt_extra)
    TextView txtExtra;
    @BindView(R.id.sp_size)
    Spinner spSize;
    private int id;
    private String titlee, pricee, img1, img2, img3, isFave, priceDiscount, description, category, rate;
    private HomeActivity homeActivity;
    private Unbinder unbinder;
    private int inc_number = 1;
    private Realm realm;
    private String string;
    private String desEn, titleEn;
    private int nextId;

    List<String> productsSizeModelList = new ArrayList<>();
    List<Double> productsSizeModelListInteger = new ArrayList<>();
    private String pro_size;
    private Integer priceSize;
    private double integer;
    private ProductsModel list;
    private Integer id_pro;


    public SelectedItemFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_selected_item, container, false);
        homeActivity = (HomeActivity) getActivity();
        unbinder = ButterKnife.bind(this, view);

        SharedPreferences preferences = homeActivity.getSharedPreferences("user", Context.MODE_PRIVATE);

        string = preferences.getString("lan", "");

        if (string.equals("ar")) {
            imgBackAr.setVisibility(View.VISIBLE);

            Typeface typeface = ResourcesCompat.getFont(homeActivity, R.font.hacen_dalal_st_regular);

            txtDetails.setTypeface(typeface);
            txtExtra.setTypeface(typeface);
            txtSr.setTypeface(typeface);
            btnAdd.setTypeface(typeface);

        } else {
            imgBack.setVisibility(View.VISIBLE);

            Typeface typeface = ResourcesCompat.getFont(homeActivity, R.font.century_gothic_400);

            txtDetails.setTypeface(typeface);
            txtExtra.setTypeface(typeface);
            txtSr.setTypeface(typeface);
            btnAdd.setTypeface(typeface);
        }

        imgBackAr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                homeActivity.onBackPressed();
            }
        });


        Realm.init(Objects.requireNonNull(getActivity()));
        realm = Realm.getDefaultInstance();


        assert getArguments() != null;

        list = getArguments().getParcelable("list");

        id_pro = list.getId();
        this.id = getArguments().getInt("id");
        titlee = getArguments().getString("title");
        category = getArguments().getString("category");
        description = getArguments().getString("des");
        img1 = getArguments().getString("img1");
        img2 = getArguments().getString("img2");
        img3 = getArguments().getString("img3");
        isFave = getArguments().getString("isfav");
        pricee = getArguments().getString("price");
        priceDiscount = getArguments().getString("priceDiscount");
        rate = getArguments().getString("rate");
        desEn = getArguments().getString("desEn");
        titleEn = getArguments().getString("titleEn");

        Log.i("onBindViewHolder: ", String.valueOf(this.id));


        List<String> imagsList = new ArrayList<>();
        imagsList.add(img1);
        imagsList.add(img2);
        imagsList.add(img3);

        for (int o = 0; o < imagsList.size(); o++) {

            DefaultSliderView textSliderView = new DefaultSliderView(getActivity());
            textSliderView
                    .description("")
                    .image(imagsList.get(o))
                    .setScaleType(BaseSliderView.ScaleType.Fit);
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra", "slider");
            if (null != mainSlider) {
                mainSlider.addSlider(textSliderView);
                mainSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
                mainSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
                mainSlider.setCustomAnimation(new DescriptionAnimation());
                mainSlider.setDuration(6000);
                mainSlider.moveNextPosition();
                mainSlider.startAutoCycle();
                mainSlider.setCustomIndicator(customIndicator);

            }

        }

        if (string.equals("ar")) {

            title.setText(titlee);
            price.setText(pricee);
            desc.setText(description);

            title.setGravity(Gravity.END);


            if (rate.equals("")) {
                rating.setRating(0);
            } else {
                rating.setRating(Float.parseFloat(rate));
            }
        } else {
            title.setText(titleEn);
            price.setText(pricee);
            desc.setText(desEn);

            title.setGravity(Gravity.END);


            if (rate.equals("")) {
                rating.setRating(0);
            } else {
                rating.setRating(Float.parseFloat(rate));
            }
        }

        productSize();

        return view;

    }

    private void productSize() {

        Log.i( "productSize: ", String.valueOf(id_pro));
        RetrofitClient.getInstant().create(API.class).productSize(String.valueOf(id_pro)).enqueue(new Callback<List<ProductsSizeModel>>() {
            @Override
            public void onResponse(Call<List<ProductsSizeModel>> call, Response<List<ProductsSizeModel>> response) {

                List<ProductsSizeModel> body = response.body();
                assert body != null;
                for (int i = 0; i <body.size() ; i++) {

                    if (string.equals("ar")){

                        productsSizeModelList.add(body.get(i).getSize());

                    }else {
                        productsSizeModelList.add(body.get(i).getSizeEn());

                    }
                    productsSizeModelListInteger.add(Double.valueOf(body.get(i).getPrice()));
                    SpinnerUtils.SetSpinnerAdapter(homeActivity, spSize, productsSizeModelList, android.R.layout.simple_spinner_item);

                    spSize.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                            pro_size = productsSizeModelList.get(position);

                            integer = productsSizeModelListInteger.get(position);


                            price.setText(String.valueOf(integer));

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

                }

            }

            @Override
            public void onFailure(Call<List<ProductsSizeModel>> call, Throwable t) {

            }
        });
    }

    @OnClick({R.id.img_back, R.id.img_min, R.id.img_plus, R.id.btn_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                homeActivity.onBackPressed();
                break;
            case R.id.img_min:
                if (inc_number > 1) {
                    inc_number--;
                    number.setText(String.valueOf(inc_number));
                }
                break;
            case R.id.img_plus:
                if (inc_number >= 1)
                    inc_number++;
                number.setText(String.valueOf(inc_number));
                break;
            case R.id.btn_add:
                addItem();
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }


    private void addItem() {

        String number_item = number.getText().toString();
        String extraRequests = edExtraRequest.getText().toString();
        String title = this.title.getText().toString();
        String priceeee = price.getText().toString();

//        if (realm.where(CartModel.class).max("no") != null){
//            nextId = Objects.requireNonNull(realm.where(CartModel.class).max("no")).intValue() + 1;
//        }

        Log.i("addItem: ", number_item);


        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {


//                if (realm.where(CartModel.class).max("no") != null){
//              nextId = Objects.requireNonNull(realm.where(CartModel.class).max("no")).intValue() + 1;
//        }

                CartModel cartModel = bgRealm.createObject(CartModel.class);

                if (string.equals("ar")){
                    cartModel.setTitle(title);

                }else {
                    cartModel.setTitle(titleEn);
                }
                cartModel.setItem_price(Double.parseDouble(String.valueOf(priceeee)));
                cartModel.setImg1(img1);
                cartModel.setId(id_pro);
                cartModel.setItem_number(number_item);
                cartModel.setExtra_request(extraRequests);

            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                // Transaction was a success.

                Toast.makeText(getActivity(), R.string.added_successfully, Toast.LENGTH_SHORT).show();
                Log.i("onBindViewHolder: ", String.valueOf(id));


                RealmResults<CartModel> all = realm.where(CartModel.class).findAll();
                homeActivity.cartBadge.setText(String.valueOf(all.size()));


                RealmResults<CartModel> alll = realm.where(CartModel.class).findAll();

                for (int j = 0; j <alll.size() ; j++) {

                    assert alll.get(j) != null;

                    Log.i( "onSuccess: ",alll.get(j).getItem_number());

                }


            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                // Transaction failed and was automatically canceled.
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                Log.i("onError", error.getMessage());
            }
        });
    }

}
