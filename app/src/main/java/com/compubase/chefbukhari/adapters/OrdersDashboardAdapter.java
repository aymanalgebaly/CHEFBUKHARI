package com.compubase.chefbukhari.adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.compubase.chefbukhari.R;
import com.compubase.chefbukhari.models.OrdersResponse;
import com.compubase.chefbukhari.ui.activities.HomeActivity;
import com.compubase.chefbukhari.ui.fragments.OrderDetailsFragment;

import java.util.ArrayList;
import java.util.List;

public class OrdersDashboardAdapter extends RecyclerView.Adapter<OrdersDashboardAdapter.ViewHolderDash> {

    private Context context;
    private ArrayList<OrdersResponse>responseArrayList;

    public OrdersDashboardAdapter(Context context) {
        this.context = context;
    }

    public OrdersDashboardAdapter(ArrayList<OrdersResponse> responseArrayList) {
        this.responseArrayList = responseArrayList;
    }

    @NonNull
    @Override
    public ViewHolderDash onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.order_dash_design, parent, false);
        return new ViewHolderDash(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderDash holder, int position) {

        OrdersResponse ordersResponse = responseArrayList.get(position);

        holder.date.setText(ordersResponse.getDatee());
        holder.order_num.setText(String.valueOf(ordersResponse.getId()));
        holder.order_id.setText(String.valueOf(ordersResponse.getId()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                HomeActivity homeActivity = (HomeActivity) context;

                Bundle bundle = new Bundle();
                bundle.putParcelable("orderResponse", ordersResponse); //key and value
                OrderDetailsFragment carDetailsFragment = new OrderDetailsFragment();
                carDetailsFragment.setArguments(bundle);
                homeActivity.displaySelectedFragment(carDetailsFragment);
            }
        });
    }

    @Override
    public int getItemCount() {
        return responseArrayList != null ? responseArrayList.size():0;
    }

    public void setData(ArrayList<OrdersResponse> ordersResponseArrayList) {
        this.responseArrayList = ordersResponseArrayList;
    }

    public class ViewHolderDash extends RecyclerView.ViewHolder {

        TextView order_num,date,order_id;
        public ViewHolderDash(@NonNull View itemView) {
            super(itemView);

            order_id = itemView.findViewById(R.id.orderId);
            order_num = itemView.findViewById(R.id.txt_num_order);
            date = itemView.findViewById(R.id.txt_date);
        }
    }
}
