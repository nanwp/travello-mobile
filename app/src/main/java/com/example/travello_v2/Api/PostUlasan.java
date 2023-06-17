package com.example.travello_v2.Api;

import android.os.AsyncTask;

import com.example.travello_v2.Interface.StatusMessageDataListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class PostUlasan extends AsyncTask<Void, Void, Void> {
    private String token, destinationId, message;
    private String messageResponse;
    private int statusCode;
    private float rating;
    private StatusMessageDataListener listener;


    public PostUlasan(String token, String destinationId, String message, float rating, StatusMessageDataListener listener) {
        this.token = token;
        this.destinationId = destinationId;
        this.message = message;
        this.rating = rating;
        this.listener = listener;
    }


    @Override
    protected Void doInBackground(Void... voids) {
        try {
            ApiClient apiClient = new ApiClient();
            String response = apiClient.addUlasan(token, destinationId, message, rating);

            JSONObject jsonResponse = new JSONObject(response);

            this.messageResponse = jsonResponse.getString("message");
            System.out.println("PADA POST ULASAN "+response);
            this.statusCode = jsonResponse.getInt("code");


        } catch (IOException e) {
            this.messageResponse = e.getMessage();
        } catch (JSONException e) {
            this.messageResponse = e.getMessage();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void unused) {
        if (listener != null){
            listener.onStatusMessageDataReceived(statusCode, messageResponse);
        }
    }
}
