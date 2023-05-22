package com.example.travello_v2.Models;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class DestinationModel {
    private String id;
    private String name;
    private String description;
    private String location;
    private String category;
    private List<String> urlImage;
    private float rating;
    private int totalUlasan;
    private ArrayList<UlasanModels> ulasan;

    public DestinationModel(String id, String name, String description, String location, String category, List<String> urlImage, float rating, int totalUlasan, ArrayList<UlasanModels> ulasan) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.location = location;
        this.category = category;
        this.urlImage = urlImage;
        this.rating = rating;
        this.totalUlasan = totalUlasan;
        this.ulasan = ulasan;
    }

    public ArrayList<UlasanModels> getUlasan() {
        return ulasan;
    }

    public void setUlasan(ArrayList<UlasanModels> ulasan) {
        this.ulasan = ulasan;
    }

    public int getTotalUlasan() {
        return totalUlasan;
    }

    public void setTotalUlasan(int totalUlasan) {
        this.totalUlasan = totalUlasan;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public List<String> getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(List<String> urlImage) {
        this.urlImage = urlImage;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
