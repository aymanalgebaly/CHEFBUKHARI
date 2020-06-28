package com.compubase.chefbukhari.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.compubase.chefbukhari.R;
import com.compubase.chefbukhari.models.CartModel;
import com.compubase.chefbukhari.models.OrdersDetailsResponse;
import com.compubase.chefbukhari.models.OrdersResponse;

import java.util.ArrayList;
import java.util.List;

public class DetailsAdapter extends RecyclerView.Adapter<DetailsAdapter.ViewHolderDetails> {

    private List<OrdersDetailsResponse> cartModelList;
    private Context context;
    private SharedPreferences preferences;

    public DetailsAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolderDetails onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        preferences = context.getSharedPreferences("user",Context.MODE_PRIVATE);
        View inflate = LayoutInflater.from(context).inflate(R.layout.details_design, parent, false);
        return new ViewHolderDetails(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderDetails holder, int position) {

        OrdersDetailsResponse cartModel = cartModelList.get(position);

        String string = preferences.getString("lan", "");

        if (string.equals("ar")){
            holder.price.setText(cartModel.getItemPrice());
            holder.title.setText(cartModel.getTitle());

        }else {

            holder.price.setText(cartModel.getItemPrice());
            holder.title.setText(cartModel.getTitleEn());
        }



        Glide.with(context).load(cartModel.getImg1()).placeholder(R.drawable.inside_app_logo).into(holder.img);
    }

    @Override
    public int getItemCount() {
        return cartModelList != null ? cartModelList.size():0;
    }

    public void setData(List<OrdersDetailsResponse> ordersResponseArrayList) {
        this.cartModelList = ordersResponseArrayList;
    }

    public class ViewHolderDetails extends RecyclerView.ViewHolder {

        ImageView img;
        TextView title,price;
        public ViewHolderDetails(@NonNull View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.img_cart);
            title = itemView.findViewById(R.id.txt_title);
            price = itemView.findViewById(R.id.txt_price);
        }
    }
}
