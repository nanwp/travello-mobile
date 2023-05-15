package com.example.travello_v2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.travello_v2.Api.Destination;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        try {
//            TimeUnit.SECONDS.sleep(2);
//
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        Intent intent = new Intent(MainActivity.this, DashboardActivity.class);
//        startActivity(intent);
//        finish();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, DashboardActivity.class);
                startActivity(intent);
                finish();
            }
        },500);

    }

}