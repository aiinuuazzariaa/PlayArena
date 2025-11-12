package com.example.playarena;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.Touch;
import android.widget.Button;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private LapanganItemActivity adapter;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        searchView = findViewById(R.id.searchView);

        ArrayList<Lapangan> lapanganList = LapanganData.getData();

        adapter = new LapanganItemActivity(this, lapanganList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

//        Button all = findViewById(R.id.allBtn);
//        Button futsal = findViewById(R.id.futsalBtn);
//        Button badminton = findViewById(R.id.badmintonBtn);
//        Button basket = findViewById(R.id.basketBtn);
//        Button tenis = findViewById(R.id.tenisBtn);
//
//        all.setOnClickListener(v -> adapter.getFilter().filter(""));
//        futsal.setOnClickListener(v -> adapter.getFilter().filter("Futsal"));
//        badminton.setOnClickListener(v -> adapter.getFilter().filter("Badminton"));
//        basket.setOnClickListener(v -> adapter.getFilter().filter("Basket"));
//        tenis.setOnClickListener(v -> adapter.getFilter().filter("Tenis"));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}
