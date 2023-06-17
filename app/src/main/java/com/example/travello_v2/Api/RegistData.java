package com.example.travello_v2.Api;

import android.os.AsyncTask;

import com.example.travello_v2.Interface.StatusMessageDataListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class RegistData extends AsyncTask<Void, Void, Void> {
    private String firstName, lastName, email, password;
    private String message;
    private int statusCode;
    private StatusMessageDataListener listener;

    public RegistData(String firstName, String lastName, String email, String password, StatusMessageDataListener listener) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.listener = listener;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            ApiClient apiClient = new ApiClient();
            String response = apiClient.registration(firstName, lastName, email, password);

            JSONObject responseJson = new JSONObject(response);

            this.statusCode = responseJson.getInt("code");
            this.message = responseJson.getString("message");
        } catch (IOException e) {
            this.message = e.getMessage();
        } catch (JSONException e) {
            this.message = e.getMessage();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void unused) {
        super.onPostExecute(unused);
        if (listener != null){
            listener.onStatusMessageDataReceived(this.statusCode, this.message);
        }
    }
}
