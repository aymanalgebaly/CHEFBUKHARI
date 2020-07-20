package com.compubase.chefbukhari.ui.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.compubase.chefbukhari.R;
import com.compubase.chefbukhari.adapters.FavAdapter;
import com.compubase.chefbukhari.data.API;
import com.compubase.chefbukhari.helpers.RetrofitClient;
import com.compubase.chefbukhari.models.FavouritesResponse;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoritesFragment extends Fragment {


    @BindView(R.id.rcv_fav)
    RecyclerView rcvFav;
    @BindView(R.id.img_back_ar)
    ImageView imgBackAr;
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.progress)
    ProgressBar progress;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    private SharedPreferences preferences;
    private String id;
    private FavouritesResponse favModel;
    private ArrayList<FavouritesResponse> favModelArrayList = new ArrayList<>();
    private FavAdapter favAdapter;
    private String string;

    Unbinder unbinder;


    public FavoritesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);
        unbinder = ButterKnife.bind(this, view);

        preferences = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        id = preferences.getString("id", "");

        string = preferences.getString("lan", "");


        if (string.equals("ar")) {
            imgBackAr.setVisibility(View.VISIBLE);

            Typeface typeface = ResourcesCompat.getFont(getContext(), R.font.hacen_dalal_st_regular);

            txtTitle.setTypeface(typeface);

        } else {
            imgBack.setVisibility(View.VISIBLE);

            Typeface typeface = ResourcesCompat.getFont(getContext(), R.font.century_gothic_400);

            txtTitle.setTypeface(typeface);
        }

        imgBackAr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        setupRecyclerTopRated();
        fetchDataTopRated();


        return view;
    }

    private void fetchDataTopRated() {

        progress.setVisibility(View.VISIBLE);

        favModelArrayList.clear();

        Call<List<FavouritesResponse>> call2 = RetrofitClient.getInstant().create(API.class).selectFave(id);

        call2.enqueue(new Callback<List<FavouritesResponse>>() {
            @Override
            public void onResponse(Call<List<FavouritesResponse>> call, Response<List<FavouritesResponse>> response) {

                if (response.isSuccessful()) {

                    List<FavouritesResponse> body = response.body();

                    progress.setVisibility(View.GONE);

                    assert body != null;
                    for (int j = 0; j < body.size(); j++) {

                        favModel = new FavouritesResponse();

                        favModel.setId(body.get(j).getIdAdmin());
                        favModel.setCategory(body.get(j).getCategory());
                        favModel.setDes(body.get(j).getDes());
                        favModel.setDesEn(body.get(j).getDesEn());
                        favModel.setImg1(body.get(j).getImg1());
                        favModel.setImg2(body.get(j).getImg2());
                        favModel.setImg3(body.get(j).getImg3());
                        favModel.setTitle(body.get(j).getTitle());
                        favModel.setTitleEn(body.get(j).getTitleEn());
                        favModel.setId(body.get(j).getId());
                        favModel.setIdUser(body.get(j).getIdUser());
                        favModel.setId1(body.get(j).getId1());
                        favModel.setNumberRate(body.get(j).getNumberRate());
                        favModel.setPrice(body.get(j).getPrice());
                        favModel.setPriceDiscount(body.get(j).getPriceDiscount());
                        favModel.setRate(body.get(j).getRate());

                        favModelArrayList.add(favModel);

                        Log.i("onResponse", favModelArrayList.toString());

                    }
                    favAdapter = new FavAdapter(favModelArrayList);
                    rcvFav.setAdapter(favAdapter);
                    favAdapter.notifyDataSetChanged();

                }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }

            }

            @Override
            public void onFailure(Call<List<FavouritesResponse>> call, Throwable t) {
                progress.setVisibility(View.GONE);
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();

                Log.i("onFailure: ", t.getMessage());
            }
        });
    }


    private void setupRecyclerTopRated() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        rcvFav.setLayoutManager(linearLayoutManager);
        rcvFav.setNestedScrollingEnabled(false);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();

    }
}
