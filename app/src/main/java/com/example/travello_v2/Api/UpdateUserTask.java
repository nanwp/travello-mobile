package com.example.travello_v2.Api;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class UpdateUserTask extends AsyncTask<Void, Void, String> {
    private Context context;
    private String token;
    private String name;

    public UpdateUserTask(Context context, String token, String name) {
        this.context = context;
        this.token = token;
        this.name = name;
    }

    @Override
    protected String doInBackground(Void... voids) {
        String result = null;
        try {
            ApiClient apiClient = new ApiClient();
            String response = apiClient.updateUser(token, name);

            JSONObject jsonResponse = new JSONObject(response);

            String status = jsonResponse.getString("status");

            if (!status.equals("OK")) {
                result = jsonResponse.toString();
            } else {
                String data = jsonResponse.getString("data");
                JSONObject jsonData = new JSONObject(data);
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
                Toast.makeText(context, "Update user berhasil", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Update user gagal: " + result, Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(context, "Terjadi kesalahan: " + result, Toast.LENGTH_SHORT).show();
        }
    }
}
