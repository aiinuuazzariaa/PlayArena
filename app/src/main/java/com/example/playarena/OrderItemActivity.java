package com.example.playarena;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class OrderItemActivity extends RecyclerView.Adapter<OrderItemActivity.ViewHolder> {

    private ArrayList<Order> orderList;

    public OrderItemActivity(ArrayList<Order> orderList) {
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_order_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Order order = orderList.get(position);

        holder.tvFieldName.setText(order.getFieldName());
        holder.tvTime.setText(order.getDate() + " • " + order.getStart() + " - " + order.getEnd());
        holder.tvTotal.setText("Rp " + (int) order.getTotalPrice());
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvFieldName, tvTime, tvTotal;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvFieldName = itemView.findViewById(R.id.tvFieldName);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvTotal = itemView.findViewById(R.id.tvTotal);
        }
    }
}
