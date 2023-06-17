package com.example.travello_v2.Api;

import android.os.AsyncTask;

import com.example.travello_v2.Interface.LoginDataListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class LoginData extends AsyncTask<Void, Void, Void> {

    private String email;
    private String password;

    private String message;
    private int statusCode;
    private String token;
    private LoginDataListener listener;

    public LoginData(String email, String password, LoginDataListener listener) {
        this.email = email;
        this.password = password;
        this.listener = listener;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            ApiClient apiClient = new ApiClient();
            String response = apiClient.login(email, password);

            JSONObject responseJson = new JSONObject(response);
            this.statusCode = responseJson.getInt("code");
            this.message = responseJson.getString("message");
            if (statusCode == 200) {
                JSONObject data = responseJson.getJSONObject("data");
                this.token = data.getString("token");
            }else {
                this.token = "";
            }



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
        if (listener != null) {
            listener.onLoginDataReceived(this.token, this.statusCode, this.message);
        }
    }
}
