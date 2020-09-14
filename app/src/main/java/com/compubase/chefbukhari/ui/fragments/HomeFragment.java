package com.compubase.chefbukhari.ui.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.compubase.chefbukhari.R;
import com.compubase.chefbukhari.adapters.CategoriesAdapter;
import com.compubase.chefbukhari.adapters.TopRatedAdapter;
import com.compubase.chefbukhari.data.API;
import com.compubase.chefbukhari.helpers.RetrofitClient;
import com.compubase.chefbukhari.models.CartModel;
import com.compubase.chefbukhari.models.CategoriesModel;
import com.compubase.chefbukhari.models.ProductsModel;
import com.compubase.chefbukhari.models.SliderModel;
import com.compubase.chefbukhari.ui.activities.HomeActivity;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.realm.Realm;
import io.realm.RealmResults;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    @BindView(R.id.rcv_categories)
    RecyclerView rcvCategories;
    @BindView(R.id.main_slider)
    SliderLayout mainSlider;
    @BindView(R.id.rcv_top_rated)
    RecyclerView rcvTopRated;
    @BindView(R.id.progress)
    ProgressBar progress;


    private Unbinder unbinder;

    private HomeActivity homeActivity;
    private String img;
    private CategoriesModel categoryResponse;
    private ArrayList<CategoriesModel> categoriesModelArrayList = new ArrayList<>();
    private CategoriesAdapter categoriesAdapter;
    private SharedPreferences preferences;
    private String id;
    private ArrayList<ProductsModel> productsModelArrayList = new ArrayList<>();
    private TopRatedAdapter topRatedAdapter;
    private ProductsModel productsModelsList;
    private String string;

    List<String>stringList = new ArrayList<>();


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, view);

        homeActivity = (HomeActivity) getActivity();

        assert homeActivity != null;
        preferences = homeActivity.getSharedPreferences("user", Context.MODE_PRIVATE);
        id = preferences.getString("id", "");

        string = preferences.getString("lan", "");


        Log.i("onCreateView: ", id);


        Realm.init(homeActivity);
        Realm realm = Realm.getDefaultInstance();
        RealmResults<ProductsModel> all = realm.where(ProductsModel.class).findAll();
        homeActivity.cartBadge.setText(String.valueOf(all.size()));

        if (id.equals("")) {
            id = "0";
        }

        setupRecycler();
        setupRecyclerTopRated();
        fetchData();
        fetchDataTopRated();
        fetchSlider();

//        realm.executeTransaction(new Realm.Transaction() {
//            @Override
//            public void execute(@NonNull Realm realm) {
//                RealmResults<CartModel> result = realm.where(CartModel.class).findAll();
//                result.deleteAllFromRealm();
//            }
//        });

        return view;
    }

    private void fetchSlider() {
        RetrofitClient.getInstant().create(API.class).slider().enqueue(new Callback<List<SliderModel>>() {
            @Override
            public void onResponse(Call<List<SliderModel>> call, Response<List<SliderModel>> response) {
                List<SliderModel> body = response.body();

                assert body != null;
                for (int o = 0; o < body.size(); o++) {

                    String slider = body.get(o).getSlider();

                    stringList.add(slider);

                    DefaultSliderView textSliderView = new DefaultSliderView(getActivity());

                    textSliderView
                            .description("")
                            .image(stringList.get(o))
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
//                                    mainSlider.setCustomIndicator(indicators);

                    }

                }

            }

            @Override
            public void onFailure(Call<List<SliderModel>> call, Throwable t) {

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
//        fetchData();
//        fetchDataTopRated();
    }



    private void fetchData() {

        progress.setVisibility(View.VISIBLE);

        categoriesModelArrayList.clear();

        RetrofitClient.getInstant().create(API.class).selecte_all_category().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();

                try {
                    assert response.body() != null;
                    List<CategoriesModel> categoriesModels =
                            Arrays.asList(gson.fromJson(response.body().string(), CategoriesModel[].class));

                    if (response.isSuccessful()) {

                        progress.setVisibility(View.GONE);

                        for (int i = 0; i < categoriesModels.size(); i++) {


                            img = categoriesModels.get(i).getImg();

//                            Log.i("onResponse: ", img);


                            categoryResponse = new CategoriesModel();

                            categoryResponse.setName(categoriesModels.get(i).getName());
                            categoryResponse.setImg(categoriesModels.get(i).getImg());
                            categoryResponse.setId(categoriesModels.get(i).getId());
                            categoryResponse.setNameEn(categoriesModels.get(i).getNameEn());
                            categoryResponse.setDateregister(categoriesModels.get(i).getDateregister());

                            categoriesModelArrayList.add(categoryResponse);

                        }

                        categoriesAdapter = new CategoriesAdapter(getActivity());
                        rcvCategories.setAdapter(categoriesAdapter);
                        categoriesAdapter.setData(categoriesModelArrayList);
                        categoriesAdapter.notifyDataSetChanged();

                    }
                } catch (IOException e) {
                    progress.setVisibility(View.GONE);

                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progress.setVisibility(View.GONE);

                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();

                Log.i("onFailure: ", t.getMessage());
            }
        });

    }

    private void setupRecycler() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL,
                false);
        rcvCategories.setLayoutManager(linearLayoutManager);
        rcvCategories.setNestedScrollingEnabled(false);

    }

    private void fetchDataTopRated() {

        progress.setVisibility(View.VISIBLE);

        productsModelArrayList.clear();

        Call<List<ProductsModel>> call2 = RetrofitClient.getInstant().create(API.class).viewProducts(id);

        call2.enqueue(new Callback<List<ProductsModel>>() {
            @Override
            public void onResponse(Call<List<ProductsModel>> call, Response<List<ProductsModel>> response) {

                assert response.body() != null;
                Log.i("onResponse: ", new Gson().toJson(response.body()));

//                GsonBuilder builder = new GsonBuilder();
//                Gson gson = builder.create();
//
//                try {
//                    assert response.body() != null;
//
//                    List<ProductsModel> productsModels =
//                            Arrays.asList(gson.fromJson(response.body().string(),ProductsModel[].class));

                progress.setVisibility(View.GONE);


                List<ProductsModel> body = response.body();

                for (int j = 0; j < body.size(); j++) {

                    productsModelsList = new ProductsModel();

                    productsModelsList.setCategory(body.get(j).getCategory());
                    productsModelsList.setDes(body.get(j).getDes());
                    productsModelsList.setDesEn(body.get(j).getDesEn());
                    productsModelsList.setImg1(body.get(j).getImg1());
                    productsModelsList.setImg2(body.get(j).getImg2());
                    productsModelsList.setImg3(body.get(j).getImg3());
                    productsModelsList.setTitle(body.get(j).getTitle());
                    productsModelsList.setTitleEn(body.get(j).getTitleEn());
                    productsModelsList.setId(body.get(j).getId());
                    productsModelsList.setPrice(body.get(j).getPrice());
                    productsModelsList.setRate(body.get(j).getRate());
                    productsModelsList.setIsfav(body.get(j).getIsfav());
                    productsModelsList.setPriceDiscount(body.get(j).getPriceDiscount());



                    productsModelArrayList.add(productsModelsList);
                }
                topRatedAdapter = new TopRatedAdapter(getActivity());
                rcvTopRated.setAdapter(topRatedAdapter);
                topRatedAdapter.setDataList(productsModelArrayList);
                topRatedAdapter.notifyDataSetChanged();

//                } catch (IOException e) {
//                    e.printStackTrace();
//                }

            }

            @Override
            public void onFailure(Call<List<ProductsModel>> call, Throwable t) {
                progress.setVisibility(View.GONE);

                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });
    }

    private void setupRecyclerTopRated() {

        LinearLayoutManager gridLayoutManager = new LinearLayoutManager(homeActivity);
        rcvTopRated.setLayoutManager(gridLayoutManager);
        rcvTopRated.setNestedScrollingEnabled(false);

    }

}
