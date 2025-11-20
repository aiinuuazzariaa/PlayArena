package com.example.playarena;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class LapanganItemActivity extends RecyclerView.Adapter<LapanganItemActivity.ViewHolder> implements Filterable {
    private Context context;
    private List<Lapangan> lapanganList;
    private List<Lapangan> lapanganListFull;

    public LapanganItemActivity(Context context, List<Lapangan> lapanganList) {
        this.context = context;
        this.lapanganList = lapanganList;
        this.lapanganListFull = new ArrayList<>(lapanganList);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_lapangan_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Lapangan lapangan = lapanganList.get(position);

        holder.name.setText(lapangan.getName());
        holder.location.setText(lapangan.getLocation());
        holder.category.setText(lapangan.getCategory());
        holder.description.setText(lapangan.getDescription());
        holder.rules.setText(lapangan.getRules());

        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(new Locale("in", "ID"));
        String hargaRupiah = formatRupiah.format(lapangan.getPrice());
        hargaRupiah = hargaRupiah.replace("Rp.", "Rp. ");
        holder.price.setText(hargaRupiah);

        holder.pict.setImageResource(lapangan.getPict());
        holder.rating.setRating(lapangan.getRating());
        holder.rating_value.setText(String.valueOf(lapangan.getRating()));

        holder.btn_detail.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailActivity.class);

            intent.putExtra("name", lapangan.getName());
            intent.putExtra("location", lapangan.getLocation());
            intent.putExtra("category", lapangan.getCategory());
            intent.putExtra("description", lapangan.getDescription());
            intent.putExtra("rules", lapangan.getRules());
            intent.putExtra("price", lapangan.getPrice());
            intent.putExtra("pict", lapangan.getPict());
            intent.putExtra("maps", lapangan.getMaps());
            intent.putExtra("rating", lapangan.getRating());
            intent.putExtra("rating_value", lapangan.getRating());

            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return lapanganList.size();
    }

    @Override
    public Filter getFilter() {
        return filterLapangan;
    }

    private Filter filterLapangan = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Lapangan> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(lapanganListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Lapangan item : lapanganListFull) {
                    if (item.getName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            lapanganList.clear();
            lapanganList.addAll((List<Lapangan>) results.values);
            notifyDataSetChanged();
        }
    };

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, location, category, description, rules, price, rating_value;
        RatingBar rating;
        ImageView pict;
        Button btn_detail, btn_map;

        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            location = itemView.findViewById(R.id.location);
            category = itemView.findViewById(R.id.category);
            description = itemView.findViewById(R.id.description);
            rules = itemView.findViewById(R.id.rules);
            price = itemView.findViewById(R.id.price);
            pict = itemView.findViewById(R.id.pict);
            btn_detail = itemView.findViewById(R.id.btnDetail);
            btn_map = itemView.findViewById(R.id.btnMap);
            rating = itemView.findViewById(R.id.rating);
            rating_value = itemView.findViewById(R.id.rating_value);
        }
    }
}
