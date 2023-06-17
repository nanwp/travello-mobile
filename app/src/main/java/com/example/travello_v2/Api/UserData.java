package com.example.travello_v2.Api;

import android.os.AsyncTask;

import com.example.travello_v2.Interface.UserDataListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class UserData extends AsyncTask<Void, Void, Void> {
    private String token;
    private UserDataListener listener;
    private String email, name;
    private int statusCode;

    public UserData(String token, UserDataListener listener) {
        this.token = token;
        this.listener = listener;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            ApiClient apiClient = new ApiClient();
            String response = apiClient.getUser(token);

            JSONObject jsonResponse = new JSONObject(response);

            statusCode = jsonResponse.getInt("code");

            if (statusCode == 200){
                JSONObject data = jsonResponse.getJSONObject("data");
                this.email = data.getString("email");
                this.name = data.getString("name");
            }else{
                this.email = "";
                this.name = "";
            }


        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (JSONException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void unused) {
        super.onPostExecute(unused);
        if (listener != null){
            listener.onUserDataReceived(this.name, this.email);
        }
    }
}
