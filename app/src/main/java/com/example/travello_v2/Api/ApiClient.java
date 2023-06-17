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

    public static String getDestination(String id) throws IOException {

        Request request = new Request.Builder()
                .url(API_BASE_URL + "destination/"+id)
                .get()
                .build();
        Response response = httpClient.newCall(request).execute();
        System.out.println(request.url());
        String responseData = response.body().string();
        response.close();

        return responseData;
    }

    public static String login(String email, String password) throws IOException {
        MediaType mediaType = MediaType.parse("application/json");
        String requestBody = String.format("{\"email\":\"%s\",\"password\":\"%s\"}", email, password);
        RequestBody body = RequestBody.create(mediaType, requestBody);

        Request request = new Request.Builder()
                .url(API_BASE_URL + "login")
                .post(body)
                .build();

        Response response = httpClient.newCall(request).execute();
        String responseData = response.body().string();
        response.close();

        return responseData;
    }

    public  static String registration(String firstName, String lastname, String email, String password) throws IOException {
        MediaType mediaType = MediaType.parse("application/json");
        String requestBody = String.format("{\"first_name\":\"%s\",\"last_name\":\"%s\",\"email\":\"%s\",\"password\":\"%s\"}",firstName, lastname,email, password);
        RequestBody body = RequestBody.create(mediaType, requestBody);

        Request request = new Request.Builder()
                .url(API_BASE_URL + "register")
                .post(body)
                .build();

        Response response = httpClient.newCall(request).execute();
        String responseData = response.body().string();
        response.close();

        return responseData;
    }

    public static String getUser(String token) throws IOException {
        Request request = new Request.Builder()
                .url(API_BASE_URL + "user")
                .get()
                .addHeader("Authorization", "Bearer " + token)
                .build();

        Response response = httpClient.newCall(request).execute();
        String responseData = response.body().string();
        response.close();
        return responseData;
    }
    public static String addUlasan(String token, String destinationId, String message, float rating) throws IOException {
        MediaType mediaType = MediaType.parse("application/json");
        String requestBody = String.format("{\"destination_id\":\"%s\",\"message\":\"%s\",\"rating\":%.2f}",destinationId, message, rating);
        RequestBody body = RequestBody.create(mediaType, requestBody);

        Request request = new Request.Builder()
                .url(API_BASE_URL + "ulasan")
                .post(body)
                .addHeader("Authorization", "Bearer " + token)
                .build();

        Response response = httpClient.newCall(request).execute();
        String responseData = response.body().string();
        response.close();
        System.out.println("Pada API CLIENT "+requestBody+"\ntoken : "+token);

        return responseData;
    }
}
