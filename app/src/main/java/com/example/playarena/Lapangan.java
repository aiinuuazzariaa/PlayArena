package com.example.playarena;

public class Lapangan {
    private String name, location, category, description, rules, maps;
    private int price, pict;
    private float rating;


    public Lapangan(String name, String location, String category, String description, String rules, int price, int pict, String maps, float rating) {
        this.name = name;
        this.location = location;
        this.category = category;
        this.description = description;
        this.price = price;
        this.rules = rules;
        this.pict = pict;
        this.maps = maps;
        this.rating = rating;
    }

    public String getName() { return name; }
    public String getLocation() { return location; }
    public String getCategory() { return category; }
    public String getDescription() { return description; }
    public String getRules() { return rules; }
    public int getPrice() { return price; }
    public int getPict() { return pict; }
    public String getMaps() { return maps; }
    public float getRating() { return rating; }
}
