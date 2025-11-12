package com.example.playarena;

public class Lapangan {
    private String name;
    private String location;
    private String category;
    private String description;
    private int pict;
    private String maps;
    private float rating;


    public Lapangan(String name, String location, String category, String description, int pict, String maps, float rating) {
        this.name = name;
        this.location = location;
        this.category = category;
        this.description = description;
        this.pict = pict;
        this.maps = maps;
        this.rating = rating;
    }

    public String getName() { return name; }
    public String getLocation() { return location; }
    public String getCategory() { return category; }
    public String getDescription() {return description;}
    public int getPict() { return pict; }
    public String getMaps() { return maps; }
    public float getRating() { return rating; }
}
