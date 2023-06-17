package com.example.travello_v2.Api;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class UpdataPasswordTask extends AsyncTask<Void, Void, String> {
    private Context context;
    private String token;

    private String oldPassword;
    private String newPassword;

    private String message;

    public UpdataPasswordTask(Context context, String token, String oldPassword, String newPassword) {
        this.context = context;
        this.token = token;
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }

    @Override
    protected String doInBackground(Void... voids) {
        String result = null;
        try {
            ApiClient apiClient = new ApiClient();

            String response = apiClient.updatePassword(token, this.oldPassword, this.newPassword);

            JSONObject jsonResponse = new JSONObject(response);

            String status = jsonResponse.getString("status");

            if (!status.equals("OK")) {
                this.message = jsonResponse.getString("message");
                result = jsonResponse.toString();
            } else {
                this.message = jsonResponse.getString("message");
                result = "OK";
            }

        } catch (IOException | JSONException e) {
            e.printStackTrace();
            result = e.getMessage();
        }

        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        if (result != null) {
            if (result.equals("OK")) {
                Toast.makeText(context, this.message, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(context, "Update password gagal: " + this.message, Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(context, "Terjadi kesalahan: " , Toast.LENGTH_LONG).show();
        }
    }
}

