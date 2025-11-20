package com.example.playarena;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.NumberFormat;
import java.util.Locale;

public class DetailActivity extends AppCompatActivity {
    private TextView name, location, category, description, rules, price;
    private RatingBar rating;
    private ImageView pict;
    private Button btnBack, btnMap, btnOrder;
    private String nameLapangan, locationLapangan, categoryLapangan, descriptionLapangan, rulesLapangan, mapsLink;
    private float ratingLapangan;
    private int priceLapangan, pictLapangan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        initViews();
        getIntentData();
        setDataToViews();
        setupListeners();
    }

    private void initViews() {
        name = findViewById(R.id.tvName);
        location = findViewById(R.id.tvLocation);
        rating = findViewById(R.id.tvRating);
        category = findViewById(R.id.tvCategory);
        description = findViewById(R.id.tvDescription);
        rules = findViewById(R.id.tvRules);
        price = findViewById(R.id.tvPrice);
        pict = findViewById(R.id.ivPict);
        btnBack = findViewById(R.id.btnBack);
        btnMap = findViewById(R.id.btnMap);
        btnOrder = findViewById(R.id.btnOrder);
    }

    private void getIntentData() {
        Intent intent = getIntent();

        nameLapangan = intent.getStringExtra("name");
        locationLapangan = intent.getStringExtra("location");
        ratingLapangan = intent.getFloatExtra("rating", 0f);
        categoryLapangan = intent.getStringExtra("category");
        descriptionLapangan = intent.getStringExtra("description");
        rulesLapangan = intent.getStringExtra("rules");
        priceLapangan = intent.getIntExtra("price", 0);
        pictLapangan = intent.getIntExtra("pict", 0);
        mapsLink = intent.getStringExtra("maps");
    }

    private void setDataToViews() {
        NumberFormat rupiah = NumberFormat.getCurrencyInstance(new Locale("in", "ID"));
        String hargaFormat = rupiah.format(priceLapangan)
                .replace(",00", "")
                .replace("Rp", "Rp ");

        name.setText(nameLapangan);
        location.setText(locationLapangan);
        rating.setRating(ratingLapangan);
        category.setText(categoryLapangan);
        description.setText(descriptionLapangan);
        rules.setText(rulesLapangan);
        price.setText(hargaFormat);
        pict.setImageResource(pictLapangan);

        Toast.makeText(this, "Detail show " + nameLapangan, Toast.LENGTH_SHORT).show();
    }

    private void setupListeners() {
        btnBack.setOnClickListener(v -> {
            finish();
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        });

        btnMap.setOnClickListener(v -> {
            if (mapsLink != null && !mapsLink.isEmpty()) {
                try {
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mapsLink));
                    startActivity(mapIntent);
                } catch (Exception e) {
                    Toast.makeText(DetailActivity.this,
                            "Cannot open Maps. Please install a map application.",
                            Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(DetailActivity.this,
                        "Maps link not available for this place.",
                        Toast.LENGTH_SHORT).show();
            }
        });

        btnOrder.setOnClickListener(v -> {
            SharedPreferences pref = getSharedPreferences("user_session", MODE_PRIVATE);
            String loggedEmail = pref.getString("email", null);

            if (loggedEmail == null) {

            }

            Intent intent = new Intent(DetailActivity.this, OrderActivity.class);

            intent.putExtra("name", nameLapangan);
            intent.putExtra("price", priceLapangan);
            intent.putExtra("location", locationLapangan);
            intent.putExtra("category", categoryLapangan);
            intent.putExtra("pict", pictLapangan);

            startActivity(intent);
        });
    }
}
