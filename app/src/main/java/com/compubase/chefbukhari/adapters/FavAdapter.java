package com.compubase.chefbukhari.adapters;

import android.content.Context;
import android.content.SharedPreferences;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.compubase.chefbukhari.R;
import com.compubase.chefbukhari.helpers.TinyDB;
import com.compubase.chefbukhari.models.FavouritesResponse;
import com.compubase.chefbukhari.ui.activities.HomeActivity;
import com.compubase.chefbukhari.ui.fragments.SelectedItemFragment;
import com.like.LikeButton;

import java.util.ArrayList;
import java.util.List;


public class FavAdapter extends RecyclerView.Adapter<FavAdapter.ViewHolder> {
    private Context context;
    private List<FavouritesResponse> favouritesResponses;
    private SharedPreferences preferences;
    private String id_user;
    private HomeActivity homeActivity;

    public FavAdapter(List<FavouritesResponse> productsModels) {
        this.favouritesResponses = productsModels;
    }

    public FavAdapter(Context context) {
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        context = viewGroup.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.top_rated_design, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {

        preferences = context.getSharedPreferences("user", Context.MODE_PRIVATE);

        String lan = preferences.getString("lan", "");

        final FavouritesResponse productsModel = favouritesResponses.get(i);

        viewHolder.heart.setBackgroundResource(R.drawable.red_heart);

        String priceDiscount = productsModel.getPriceDiscount();

        if (lan.equals("ar")){

            viewHolder.txt_discount.setText(priceDiscount);
            viewHolder.offer.setText(productsModel.getPrice());

            String rate = productsModel.getRate();

            if (rate.equals("")){
                viewHolder.ratingBar.setRating(0);
            }else {

                viewHolder.ratingBar.setRating(Float.parseFloat(rate));

            }
            viewHolder.title.setText(productsModel.getTitle());


        }else {


            viewHolder.txt_discount.setText(priceDiscount);
            viewHolder.offer.setText(productsModel.getPrice());

            String rate = productsModel.getRate();

            if (rate.equals("")){
                viewHolder.ratingBar.setRating(0);
            }else {

                viewHolder.ratingBar.setRating(Float.parseFloat(rate));

            }
            viewHolder.title.setText(productsModel.getTitleEn());

        }


        Glide.with(context).load(productsModel.getImg1()).into(viewHolder.img_item);


        id_user = preferences.getString("id", "");

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                homeActivity = (HomeActivity) context;

                Bundle bundle = new Bundle();
                bundle.putInt("id", productsModel.getId()); //key and value
                bundle.putString("title", productsModel.getTitle());
                bundle.putString("titleEn", productsModel.getTitleEn());
                bundle.putString("category", productsModel.getCategory());
                bundle.putString("des", productsModel.getDes());
                bundle.putString("desEn", productsModel.getDesEn());
                bundle.putString("img1", productsModel.getImg1());
                bundle.putString("img2", productsModel.getImg2());
                bundle.putString("img3", productsModel.getImg3());
                bundle.putString("price", productsModel.getPrice());
                bundle.putString("rate", productsModel.getRate());

                SelectedItemFragment selectedItemFragment = new SelectedItemFragment();
                selectedItemFragment.setArguments(bundle);
                homeActivity.displaySelectedFragmentWithBack(selectedItemFragment);

                TinyDB tinyDB = new TinyDB(context);
                tinyDB.putString("pic",productsModel.getImg1());
                tinyDB.putString("pic1",productsModel.getImg2());
                tinyDB.putString("pic2",productsModel.getImg3());
                tinyDB.putString("name",productsModel.getTitle());
                tinyDB.putString("rate",productsModel.getNumberRate());
                tinyDB.putString("price",productsModel.getPrice());
                tinyDB.putString("des",productsModel.getDes());
                tinyDB.putString("dis",productsModel.getPriceDiscount());
                tinyDB.putString("id", String.valueOf(productsModel.getId()));
            }
        });

        viewHolder.heart.setLikeDrawableRes(R.drawable.red_heart);
    }

//    private void addToFav(Integer id) {
//        Retrofit retrofit = RetrofitClient.getInstant();
//        API api = retrofit.create(API.class);
//        Call<ResponseBody> responseBodyCall = api.insertFav(id_user, String.valueOf(id));
//        responseBodyCall.enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                if (response.isSuccessful()){
//                    try {
//                        assert response.body() != null;
//                        String string = response.body().string();
//                        if (string.equals("True")){
//                            Toast.makeText(context, "Added to favourite", Toast.LENGTH_SHORT).show();
//                        }
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

    @Override
    public int getItemCount() {
        return favouritesResponses != null ? favouritesResponses.size():0;
    }

    public void setAdapter(ArrayList<FavouritesResponse> topRatedModels) {
        this.favouritesResponses = topRatedModels;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title,offer,offer_sale,txt_discount;
        ImageView img_item;
        RatingBar ratingBar;
        LikeButton heart;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.pro_name);
            offer = itemView.findViewById(R.id.price_txt);
            txt_discount = itemView.findViewById(R.id.discount_txt);
            ratingBar = itemView.findViewById(R.id.rating);
            heart = itemView.findViewById(R.id.like_button);
            img_item = itemView.findViewById(R.id.pro_img);


        }
    }
}
