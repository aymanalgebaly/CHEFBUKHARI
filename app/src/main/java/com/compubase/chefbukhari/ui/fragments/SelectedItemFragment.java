package com.compubase.chefbukhari.ui.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.compubase.chefbukhari.R;
import com.compubase.chefbukhari.models.CartModel;
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
    ImageView imgMin;
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
    private int id;
    private String titlee, pricee, img1, img2, img3, isFave, priceDiscount, description, category, rate;
    private HomeActivity homeActivity;
    private Unbinder unbinder;
    private int inc_number = 1;
    private Realm realm;
    private String string;
    private String desEn, titleEn;
    private int nextId;

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
        } else {
            imgBack.setVisibility(View.VISIBLE);
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
        id = getArguments().getInt("id");
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

        Log.i( "onBindViewHolder: ", String.valueOf(id));


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


        return view;

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

                cartModel.setTitle(title);
                cartModel.setItem_price(Double.parseDouble(pricee));
                cartModel.setImg1(img1);
                cartModel.setId(id);
                cartModel.setItem_number(number_item);
                cartModel.setExtra_request(extraRequests);

            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                // Transaction was a success.

                Toast.makeText(getActivity(), "data inserted", Toast.LENGTH_SHORT).show();
                Log.i( "onBindViewHolder: ", String.valueOf(id));


                RealmResults<CartModel> all = realm.where(CartModel.class).findAll();
                homeActivity.cartBadge.setText(String.valueOf(all.size()));
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
