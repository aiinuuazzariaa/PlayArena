package com.example.playarena;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyOrdersActivity extends AppCompatActivity {
    RecyclerView rvOrders;
    OrderDatabase orderDb;
    ArrayList<Order> orderList;
    OrderItemActivity adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toast.makeText(this, "MyOrders opened!", Toast.LENGTH_SHORT).show();
        setContentView(R.layout.activity_my_orders);
        try {
            rvOrders = findViewById(R.id.rvOrders);
            rvOrders.setLayoutManager(new LinearLayoutManager(this));

            orderDb = new OrderDatabase(this);

            SharedPreferences prefs = getSharedPreferences("user_session", MODE_PRIVATE);
            String email = prefs.getString("email", "");

            Toast.makeText(this, "Email: " + email, Toast.LENGTH_SHORT).show();

            orderList = orderDb.getOrdersByEmail(email);

            adapter = new OrderItemActivity(orderList);
            rvOrders.setAdapter(adapter);

        } catch (Exception e) {
            Toast.makeText(this, "ERR: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void goToMain(View view) {
        Intent intent = new Intent(MyOrdersActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
