package com.example.travello_v2.Api;

import android.os.AsyncTask;

import com.example.travello_v2.DestinationDataListener;
import com.example.travello_v2.Models.DestinationModels;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class DestinationData extends AsyncTask<Void, Void, ArrayList<DestinationModels>> {

    private String search;
    private String category;
    private ArrayList<DestinationModels> destinationModelsArrayList;
    private DestinationDataListener listener;
    private int statusCode;
    private JSONArray dataResponse;
    private String message;

    public DestinationData(String search, String category, DestinationDataListener listener) {
        this.search = search;
        this.category = category;
        this.listener = listener;
    }

    @Override
    protected ArrayList<DestinationModels> doInBackground(Void... voids) {
        try {
            ApiClient apiClient = new ApiClient();
            String response = apiClient.getDestinations(this.search, this.category);

            processResponse(response);

            convertJsonToDestinationModels();

            return destinationModelsArrayList;

        } catch (IOException e) {
            e.printStackTrace();
//            System.out.println(e.getMessage());
            message = e.getMessage();

        } catch (JSONException e) {
            e.printStackTrace();
//            System.out.println(e.getMessage());
            message = e.getMessage();
        }

        return null; // Mengembalikan null jika terjadi kesalahan
    }

    private void processResponse(String response) throws JSONException {
        JSONObject jsonResponse = new JSONObject(response);
        String message = jsonResponse.getString("message");
        int statusCode = jsonResponse.getInt("code");
        JSONArray dataResponse = jsonResponse.getJSONArray("data");

        this.statusCode = statusCode;

        this.message = message;

        // Melakukan penanganan respons seperti pemrosesan status kode, manipulasi data, dll.

        // Menyimpan dataResponse agar dapat diakses di metode convertJsonToDestinationModels()
        this.dataResponse = dataResponse;
    }

    private void convertJsonToDestinationModels() throws JSONException {
        destinationModelsArrayList = new ArrayList<>();
        for (int i = 0; i < dataResponse.length(); i++) {
            JSONObject dat = new JSONObject(dataResponse.getString(i));
            destinationModelsArrayList.add(i, new DestinationModels(
                    dat.getString("_id"),
                    dat.getString("name"),
                    dat.getString("description"),
                    dat.getString("location"),
                    dat.getString("category"),
                    dat.getString("image"),
                    (float) dat.getDouble("rating"),
                    dat.getString("created_at"),
                    dat.getString("updated_at")
            ));
        }
    }


    @Override
    protected void onPostExecute(ArrayList<DestinationModels> destinationModels) {
        super.onPostExecute(destinationModels);


        System.out.println(this.message);

        if (listener != null){
            listener.onDestinationDataReceived(destinationModels, this.statusCode, this.message);
        }
    }
}
