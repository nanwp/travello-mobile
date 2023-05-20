package com.example.travello_v2.Api;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ApiClient {

    private static final String API_BASE_URL = "https://travello.pegelinux.tech/api/";
    private static  final OkHttpClient httpClient = new OkHttpClient();

    public static String getDestinations(String search, String category) throws IOException {

        Request request = new Request.Builder()
                .url(API_BASE_URL + "destinations?search="+search+"&category="+category)
                .get()
                .build();
        Response response = httpClient.newCall(request).execute();
        System.out.println(request.url());
        String responseData = response.body().string();
        response.close();

        return responseData;
    }
}
