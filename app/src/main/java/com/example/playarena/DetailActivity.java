package com.example.playarena;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {
    private TextView name, location, category, description;
    private RatingBar rating;
    private ImageView pict;
    private Button btnBack, btnMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        name = findViewById(R.id.tvName);
        location = findViewById(R.id.tvLocation);
        rating = findViewById(R.id.tvRating);
        category = findViewById(R.id.tvCategory);
        description = findViewById(R.id.tvDescription);
        pict = findViewById(R.id.ivPict);
        btnBack = findViewById(R.id.btnBack);
        btnMap = findViewById(R.id.btnMap);

        String nameLapangan = getIntent().getStringExtra("name");
        String locationLapangan = getIntent().getStringExtra("location");
        float ratingLapangan = getIntent().getFloatExtra("rating", 0f);
        String categoryLapangan = getIntent().getStringExtra("category");
        String descriptionLapangan = getIntent().getStringExtra("description");
        int pictLapangan = getIntent().getIntExtra("pict", 0);

        name.setText(nameLapangan);
        location.setText(locationLapangan);
        rating.setRating(ratingLapangan);
        category.setText(categoryLapangan);
        description.setText(descriptionLapangan);
        pict.setImageResource(pictLapangan);

        Toast.makeText(this, "Detail show " + nameLapangan, Toast.LENGTH_SHORT).show();

        btnBack.setOnClickListener(v -> {
            finish();
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        });

        btnMap.setOnClickListener(v -> {
            String maps = getIntent().getStringExtra("maps");

            if (maps != null && !maps.isEmpty()) {
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(maps));
                try {
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
    }
}
