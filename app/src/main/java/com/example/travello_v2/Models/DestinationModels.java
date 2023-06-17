package com.example.travello_v2.Models;

import org.json.JSONArray;

public class DestinationModels {
    private String id;
    private String name;
    private String description;
    private String location;
    private String category;
    private JSONArray image;
    private float rating;
    private int totalUlasan;
    private String createdAt;
    private String updatedAt;

    public DestinationModels(){}
    public DestinationModels(String id, String name, String description, String location, String category, JSONArray image, float rating, int totalUlasan, String createdAt, String updatedAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.location = location;
        this.category = category;
        this.image = image;
        this.rating = rating;
        this.totalUlasan = totalUlasan;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
    public float getRating(){return this.rating;}
    public void setRating(float rating){this.rating = rating;}
    public JSONArray getImage() {
        return image;
    }

    public void setImage(JSONArray image) {
        this.image = image;
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

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public int getTotalUlasan() {
        return totalUlasan;
    }

    public void setTotalUlasan(int totalUlasan) {
        this.totalUlasan = totalUlasan;
    }
}