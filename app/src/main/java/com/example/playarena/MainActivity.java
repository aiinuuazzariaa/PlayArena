package com.example.playarena;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private LapanganItemActivity adapter;
    private SearchView searchView;
    private Button btnAll, btnBadminton, btnBasket, btnFutsal, btnTenis;
    private boolean isCategorySelected = false;
    private String currentCategory = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        searchView = findViewById(R.id.searchView);

        btnAll = findViewById(R.id.btnAll);
        btnBadminton = findViewById(R.id.btnBadminton);
        btnBasket = findViewById(R.id.btnBasket);
        btnFutsal = findViewById(R.id.btnFutsal);
        btnTenis = findViewById(R.id.btnTenis);

        int id = searchView.getContext().getResources()
                .getIdentifier("android:id/search_src_text", null, null);
        EditText searchText = searchView.findViewById(id);

        if (searchText != null) {
            searchText.setTextColor(Color.BLACK);
            searchText.setHintTextColor(Color.GRAY);
            searchText.setTextSize(16);
        }

        ArrayList<Lapangan> lapanganList = LapanganData.getData();

        adapter = new LapanganItemActivity(this, lapanganList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

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
        btnAll.setOnClickListener(v -> {resetCategoryFilter(); highlightButton(btnAll); });
        btnBadminton.setOnClickListener(v -> applyCategory("badminton", btnBadminton));
        btnBasket.setOnClickListener(v -> applyCategory("basket", btnBasket));
        btnFutsal.setOnClickListener(v -> applyCategory("futsal", btnFutsal));
        btnTenis.setOnClickListener(v -> applyCategory("tenis", btnTenis));
    }

    private void applyCategory(String category, Button btn) {

        if (isCategorySelected && currentCategory.equals(category)) {
            resetCategoryFilter();
            return;
        }

        isCategorySelected = true;
        currentCategory = category;

        adapter.getFilter().filter(category);

        highlightButton(btn);
    }

    private void resetCategoryFilter() {
        isCategorySelected = false;
        currentCategory = "";

        adapter.getFilter().filter("");

        resetButtonStyles();
    }

    private void highlightButton(Button selectedBtn) {
        resetButtonStyles();

        selectedBtn.setBackgroundTintList(
                ColorStateList.valueOf(Color.parseColor("#1C398E"))
        );
        selectedBtn.setTextColor(Color.WHITE);
    }

    private void resetButtonStyles() {
        Button[] buttons = {btnAll, btnBadminton, btnBasket, btnFutsal, btnTenis};

        for (Button btn : buttons) {
            btn.setBackgroundTintList(
                    ColorStateList.valueOf(Color.parseColor("#C8C7C9"))
            );
            btn.setTextColor(Color.parseColor("#FFFFFF"));
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}
