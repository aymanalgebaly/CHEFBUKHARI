package com.compubase.chefbukhari.adapters;

import android.content.Context;

import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.compubase.chefbukhari.R;
import com.compubase.chefbukhari.models.CategoriesModel;
import com.compubase.chefbukhari.ui.activities.HomeActivity;
import com.compubase.chefbukhari.ui.fragments.CategorySelectedFragment;

import java.util.ArrayList;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.ViewHolder> {
    private Context context;
    private ArrayList<CategoriesModel> categoryResponseArrayList;
    private SharedPreferences preferences;
    private String string;


    public CategoriesAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        context = viewGroup.getContext();
        preferences = context.getSharedPreferences("user",Context.MODE_PRIVATE);
        string = preferences.getString("lan", "");
        View view = LayoutInflater.from(context).inflate(R.layout.categories_design, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {

        final CategoriesModel categoriesModel = categoryResponseArrayList.get(i);


        Glide.with(context).load(categoriesModel.getImg()).into(viewHolder.img);

        String name = categoriesModel.getName();
        String nameEn = categoriesModel.getNameEn();


        if (string.equals("ar")) {

            viewHolder.textView.setText(categoriesModel.getName());

            Typeface typeface = ResourcesCompat.getFont(context, R.font.hacen_dalal_st_regular);

            viewHolder.textView.setTypeface(typeface);


        } else {

            viewHolder.textView.setText(categoriesModel.getNameEn());

            Typeface typeface = ResourcesCompat.getFont(context, R.font.century_gothic_400);

            viewHolder.textView.setTypeface(typeface);

        }

        Log.i( "onBindViewHolder: ",name);
        Log.i( "onBindViewHolder: ",nameEn);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeActivity homeActivity = (HomeActivity) context;

                Bundle bundle = new Bundle();
                bundle.putString("name",name);
                bundle.putString("nameEn",nameEn);

                CategorySelectedFragment categorySelectedFragment = new CategorySelectedFragment();
                categorySelectedFragment.setArguments(bundle);
                homeActivity.displaySelectedFragmentWithBack(categorySelectedFragment);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryResponseArrayList != null ? categoryResponseArrayList.size():0;

    }

    public void setAdapter(ArrayList<CategoriesModel> categoryResponses) {
        this.categoryResponseArrayList = categoryResponses;
    }

    public void setData(ArrayList<CategoriesModel> categoryResponseArrayList) {

        this.categoryResponseArrayList = categoryResponseArrayList;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView img;
        TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.img_circle);
            textView = itemView.findViewById(R.id.txt_title);
        }
    }
}
