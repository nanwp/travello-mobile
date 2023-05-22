package com.example.travello_v2.Models;

public class UlasanModels {
    private String user;
    private String message;
    private float rating;

    public UlasanModels(){

    }

    public UlasanModels(String user, String message, float rating) {
        this.user = user;
        this.message = message;
        this.rating = rating;
    }
    public void setUser(String user){
        this.user = user;
    }
    public String getUser(){
        return this.user;
    }

    public void setMessage(String message){
        this.message = message;
    }
    public String getMessage(){
        return this.message;
    }

    public void setRating(float rating){
        this.rating = rating;
    }
    public float getRating(){
        return this.rating;
    }
}