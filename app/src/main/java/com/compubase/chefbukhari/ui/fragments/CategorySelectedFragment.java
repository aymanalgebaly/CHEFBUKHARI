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

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.compubase.chefbukhari.R;
import com.compubase.chefbukhari.adapters.CategoriesAdapter;
import com.compubase.chefbukhari.adapters.TopRatedAdapter;
import com.compubase.chefbukhari.data.API;
import com.compubase.chefbukhari.helpers.RetrofitClient;
import com.compubase.chefbukhari.helpers.TinyDB;
import com.compubase.chefbukhari.models.CategoriesProResponse;
import com.compubase.chefbukhari.models.ProductsModel;
import com.compubase.chefbukhari.ui.activities.HomeActivity;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategorySelectedFragment extends Fragment {


    @BindView(R.id.rcv_category)
    RecyclerView rcvCategory;
    @BindView(R.id.progress)
    ProgressBar progress;
    private HomeActivity homeActivity;
    private String string;
    private Unbinder unbinder;
    private String name,nameEn;
    private String category;
    private String id_user;
    private ProductsModel productsModelsList;
    private List<ProductsModel> productsModelArrayList = new ArrayList<>();
    private TopRatedAdapter topRatedAdapter;
    private boolean login;

    public CategorySelectedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_category_selected, container, false);
        homeActivity = (HomeActivity) getActivity();
        unbinder = ButterKnife.bind(this, view);

        SharedPreferences preferences = homeActivity.getSharedPreferences("user", Context.MODE_PRIVATE);

        string = preferences.getString("lan", "");
        id_user = preferences.getString("id", "");
        login = preferences.getBoolean("login", false);

        if (!login)
            id_user = "0";

        assert getArguments() != null;
        name = getArguments().getString("name");
        nameEn = getArguments().getString("nameEn");


        setupRecyclerTopRated();


        if (string.equals("ar")){

            fetchDataTopRated(name);

        }else {
            fetchDataTopRated(nameEn);
        }


        return view;
    }

    private void fetchDataTopRated(String name) {

        progress.setVisibility(View.VISIBLE);

        productsModelArrayList.clear();

        Call<List<ProductsModel>> call2 = RetrofitClient.getInstant().create(API.class)
                .select_all_product_by_category(name,id_user);

        call2.enqueue(new Callback<List<ProductsModel>>() {
            @Override
            public void onResponse(Call<List<ProductsModel>> call, Response<List<ProductsModel>> response) {

                assert response.body() != null;
                Log.i("onResponse: ", new Gson().toJson(response.body()));


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
//                            productsModelsList.setPriceDiscount(body.get(j).getPriceDiscount());
                    productsModelsList.setRate(body.get(j).getRate());
                    productsModelsList.setIsfav(body.get(j).getIsfav());

                    productsModelArrayList.add(productsModelsList);
                }
                topRatedAdapter = new TopRatedAdapter(getActivity());
                rcvCategory.setAdapter(topRatedAdapter);
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

                Log.i("onFailure: ", t.getMessage());
            }
        });
    }

    private void setupRecyclerTopRated() {

        GridLayoutManager gridLayoutManager = new GridLayoutManager(homeActivity, 2);
        rcvCategory.setLayoutManager(gridLayoutManager);
        rcvCategory.setNestedScrollingEnabled(false);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
