package com.example.travello_v2.Api;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Destination {


    final static private String URL = "http://103.171.182.206:8070/ulasan?destination=6435be870ba7516fa87b032c";

    public Destination(){}
    public void getDestination(Context context){
        System.out.println("in get destination");

        StringRequest request = new StringRequest(
                Request.Method.GET,
                URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonResponse = new JSONObject(response);
                            System.out.println(response);
                            Toast.makeText(context, response, Toast.LENGTH_SHORT).show();


                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("error : ", error.networkResponse.toString());
                        System.out.println(error);
                        Toast.makeText(context, error.toString().trim(), Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("Content-Type", "application/json; charset=utf-8");
                params.put("Authorization", "Bearer "+"LMzgxMzQyMH0.WbyM8wIIimG2eiz24XGnwPSsBkFfDNE9So6LffCXvk0");

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context, null);
        requestQueue.add(request);
    }
}
