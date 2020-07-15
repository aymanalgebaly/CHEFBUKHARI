package com.compubase.chefbukhari.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.compubase.chefbukhari.R;
import com.compubase.chefbukhari.models.OrdersAgentResponse;
import com.compubase.chefbukhari.models.OrdersResponse;
import com.compubase.chefbukhari.ui.activities.ArrivalActivity;
import com.compubase.chefbukhari.ui.activities.HomeActivity;
import com.compubase.chefbukhari.ui.activities.SendDeliveryCodeAgentActivity;

import java.util.ArrayList;

public class AgentDashboardAdapter extends RecyclerView.Adapter<AgentDashboardAdapter.ViewHolderDash> {

    private Context context;
    private ArrayList<OrdersAgentResponse>ordersAgentResponses;

    public AgentDashboardAdapter(Context context) {
        this.context = context;
    }

    public AgentDashboardAdapter(ArrayList<OrdersAgentResponse> responseArrayList) {
        this.ordersAgentResponses = responseArrayList;
    }

    @NonNull
    @Override
    public ViewHolderDash onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.order_dash_design_agent, parent, false);
        return new ViewHolderDash(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderDash holder, int position) {

        OrdersAgentResponse ordersResponse = ordersAgentResponses.get(position);


        holder.date.setText(ordersResponse.getDatee());
        holder.order_num.setText(String.valueOf(ordersResponse.getId()));
        holder.time.setText(ordersResponse.getTimee());
//        holder.code.setText(String.valueOf(ordersResponse.getDeliverCode()));
        holder.branch.setText(ordersResponse.getBranch());

        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ArrivalActivity.class);
                intent.putExtra("ordersResponse",ordersResponse);
                context.startActivity(intent);
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, SendDeliveryCodeAgentActivity.class);
                intent.putExtra("agent", ordersResponse);
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return ordersAgentResponses != null ? ordersAgentResponses.size():0;
    }

    public void setData(ArrayList<OrdersAgentResponse> ordersResponseArrayList) {
        this.ordersAgentResponses = ordersResponseArrayList;
    }

    public class ViewHolderDash extends RecyclerView.ViewHolder {

        TextView order_num,date,time,branch;
        Button button;
        public ViewHolderDash(@NonNull View itemView) {
            super(itemView);

            time = itemView.findViewById(R.id.time);
            order_num = itemView.findViewById(R.id.txt_num_order);
            date = itemView.findViewById(R.id.txt_date);
            branch = itemView.findViewById(R.id.txt_branch);
            button = itemView.findViewById(R.id.btn_arrival);
        }
    }
}
