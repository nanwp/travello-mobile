package com.example.travello_v2.Api;

import android.os.AsyncTask;

import com.example.travello_v2.Interface.DestinationDataListener;
import com.example.travello_v2.Models.DestinationModel;
import com.example.travello_v2.Models.UlasanModels;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DestinationData extends AsyncTask<Void, Void, DestinationModel> {

    private String id;
    private String message;
    private DestinationDataListener listener;
    private DestinationModel destinationModel;
    private int statusCode;

    public DestinationData(String id, DestinationDataListener listener){
        this.id = id;
        this.listener = listener;
    }
    @Override
    protected DestinationModel doInBackground(Void... voids) {

        try {
            ApiClient apiClient = new ApiClient();
            String response = apiClient.getDestination(id);

            JSONObject jsonResponse = new JSONObject(response);

            this.message = jsonResponse.getString("message");
            this.statusCode = jsonResponse.getInt("code");

            if (statusCode == 200) {
                JSONObject dataObj = jsonResponse.getJSONObject("data");
                JSONArray imageArray = dataObj.getJSONArray("image");
                List<String> imageList = new ArrayList<>();
                for (int i = 0; i <imageArray.length(); i++ ){
                    String imageUrl = imageArray.getString(i);
                    imageList.add(imageUrl);
                }


                JSONArray ulasanResp = dataObj.getJSONArray("ulasan");
                ArrayList<UlasanModels> ulasanArray = new ArrayList<>();

                for (int i = 0; i < ulasanResp.length(); i++){
                    JSONObject u = new JSONObject(ulasanResp.getString(i));
                    ulasanArray.add(i, new UlasanModels(
                            u.getString("user_name"),
                            u.getString("message"),
                            (float) u.getDouble("rating")
                    ));
                }

                destinationModel = new DestinationModel(
                        dataObj.getString("id"),
                        dataObj.getString("name"),
                        dataObj.getString("description"),
                        dataObj.getString("location"),
                        dataObj.getString("category"),
                        imageList,
                        (float) dataObj.getDouble("rating"),
                        dataObj.getInt("jumlah_ulasan"),
                        ulasanArray
                );
                return destinationModel;
            }
            return null;

        } catch (IOException e) {
            e.printStackTrace();
            message = e.getMessage();
        } catch (JSONException e) {
            e.printStackTrace();
            message = e.getMessage();
        }
        return null;
    }

    @Override
    protected void onPostExecute(DestinationModel data) {
        super.onPostExecute(data);

        if(listener != null){
            listener.onDestinationDataReceived(data, statusCode, message);
        }
    }
}
