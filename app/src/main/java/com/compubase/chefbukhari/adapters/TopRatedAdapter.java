package com.compubase.chefbukhari.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.compubase.chefbukhari.R;
import com.compubase.chefbukhari.data.API;
import com.compubase.chefbukhari.helpers.RetrofitClient;
import com.compubase.chefbukhari.helpers.ShowConfirmMsgDialog;
import com.compubase.chefbukhari.models.ProductsModel;
import com.compubase.chefbukhari.ui.activities.HomeActivity;
import com.compubase.chefbukhari.ui.activities.LoginActivity;
import com.compubase.chefbukhari.ui.fragments.SelectedItemFragment;
import com.like.LikeButton;
import com.like.OnLikeListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class TopRatedAdapter extends RecyclerView.Adapter<TopRatedAdapter.ViewHolder> {
    private Context context;
    private List<ProductsModel> productsResponseList;
    private SharedPreferences preferences;
    private String id_user;
    private Integer id;
    private HomeActivity homeActivity;
    private ShowConfirmMsgDialog showConfirmMsgDialog;
    private Integer id_userr;
    private Integer id_pro;

    public TopRatedAdapter(List<ProductsModel> productsModels) {
        this.productsResponseList = productsModels;
    }

    public void setDataList(List<ProductsModel> productsModels) {
        this.productsResponseList = productsModels;
    }

    public TopRatedAdapter(Context context) {
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        context = viewGroup.getContext();
        showConfirmMsgDialog = new ShowConfirmMsgDialog(context);
        View view = LayoutInflater.from(context).inflate(R.layout.top_rated_design, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {


        preferences = context.getSharedPreferences("user", Context.MODE_PRIVATE);

        String lan = preferences.getString("lan", "");

        final ProductsModel productsModel = productsResponseList.get(i);


        id_pro = productsResponseList.get(i).getId();

        String category = productsResponseList.get(i).getCategory();
        String des = productsResponseList.get(i).getDes();

//        Log.i( "onBindViewHolder: ", String.valueOf(id));
        String img1 = productsResponseList.get(i).getImg1();
        String img2 = productsResponseList.get(i).getImg2();
        String img3 = productsResponseList.get(i).getImg3();
        String isfav = productsResponseList.get(i).getIsfav();
        String price = productsResponseList.get(i).getPrice();
        String rate1 = productsResponseList.get(i).getRate();
        String title = productsResponseList.get(i).getTitle();
        String desEn = productsResponseList.get(i).getDesEn();
        String titleEn = productsResponseList.get(i).getTitleEn();

        Log.i( "onBindViewHolder: ",lan);


        if (lan.equals("ar")){

            viewHolder.offer.setText(productsModel.getPrice());

            String rate = productsModel.getRate();

            if (rate.equals("")) {
                viewHolder.ratingBar.setRating(0);
            } else {

                viewHolder.ratingBar.setRating(Float.parseFloat(rate));

            }
            viewHolder.title.setText(productsModel.getTitle());

        }else if (lan.equals("en")){

            viewHolder.offer.setText(productsModel.getPrice());
            viewHolder.title.setText(productsModel.getTitleEn());

            String rate = productsModel.getRate();

            if (rate.equals("")) {
                viewHolder.ratingBar.setRating(0);
            } else {

                viewHolder.ratingBar.setRating(Float.parseFloat(rate));

            }
        }

//        viewHolder.offer.setText(productsModel.getPrice());
//
//        String rate = productsModel.getRate();
//
//        if (rate.equals("")) {
//            viewHolder.ratingBar.setRating(0);
//        } else {
//
//            viewHolder.ratingBar.setRating(Float.parseFloat(rate));
//
//        }
//        viewHolder.title.setText(productsModel.getTitle());

        Glide.with(context).load(productsModel.getImg1()).into(viewHolder.img_item);

            if (isfav.equals("yes")){
                viewHolder.heart.setLiked(true);
                viewHolder.heart.setLikeDrawableRes(R.drawable.red_heart);

            }else {
                viewHolder.heart.setLiked(false);
                viewHolder.heart.setUnlikeDrawableRes(R.drawable.empty_heart);

        }


        boolean login = preferences.getBoolean("login", false);

        viewHolder.heart.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {

                id = productsResponseList.get(i).getId();


                Log.i( "liked: ", String.valueOf(TopRatedAdapter.this.id));

                if (!login){
                    context.startActivity(new Intent(context, LoginActivity.class));
                }else {

                    viewHolder.heart.setLikeDrawableRes(R.drawable.red_heart);

                    Retrofit retrofit = RetrofitClient.getInstant();
                    API api = retrofit.create(API.class);
                    Call<ResponseBody> responseBodyCall = api.insertFav(id_user, String.valueOf(id));
                    responseBodyCall.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if (response.isSuccessful()){
                                try {
                                    assert response.body() != null;
                                    String string = response.body().string();
                                    if (string.equals("True")){


                                        Toast.makeText(context, R.string.add_to_favourite, Toast.LENGTH_SHORT).show();
//                                        showConfirmMsgDialog.
//                                                createDilog(context,context.getString(R.string.add_to_favourite));

                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

                }



            }
            @Override
            public void unLiked(LikeButton likeButton) {

                TopRatedAdapter.this.id = productsResponseList.get(i).getId();


                viewHolder.heart.setLikeDrawableRes(R.drawable.empty_heart);

//                viewHolder.heart.setLikeDrawableRes(R.drawable.red_heart);

                Retrofit retrofit = RetrofitClient.getInstant();
                API api = retrofit.create(API.class);
                Call<ResponseBody> responseBodyCall = api.insertFav(id_user, String.valueOf(TopRatedAdapter.this.id));
                responseBodyCall.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()){
                            try {
                                assert response.body() != null;
                                String string = response.body().string();
                                if (string.equals("True")){

//                                    showConfirmMsgDialog.
//                                            createDilog(context,context.getString(R.string.add_to_favourite));

                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });


        id_user = preferences.getString("id", "");

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Toast.makeText(context, String.valueOf(id), Toast.LENGTH_SHORT).show();
                String isfav = productsModel.getIsfav();
                homeActivity = (HomeActivity) context;

                Bundle bundle = new Bundle();
                bundle.putInt("id", id_pro); //key and value
                bundle.putString("title", title);
                bundle.putString("category", category);
                bundle.putString("des", des);
                bundle.putString("img1", img1);
                bundle.putString("img2", img2);
                bundle.putString("img3", img3);
                bundle.putString("isfav", isfav);
                bundle.putString("price", price);
                bundle.putString("rate", rate1);
                bundle.putString("desEn", desEn);
                bundle.putString("titleEn", titleEn);

                Log.i( "onBindViewHolder: ", String.valueOf(TopRatedAdapter.this.id));


                SelectedItemFragment selectedItemFragment = new SelectedItemFragment();
                selectedItemFragment.setArguments(bundle);
                homeActivity.displaySelectedFragmentWithBack(selectedItemFragment);

            }
        });

    }

    @Override
    public int getItemCount() {
        return productsResponseList != null ? productsResponseList.size():0;
    }

    public void setAdapter(ArrayList<ProductsModel> topRatedModels) {
        this.productsResponseList = topRatedModels;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title,offer,offer_sale,txt_discount;
        ImageView img_item;
        LikeButton heart;
        RatingBar ratingBar;
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
