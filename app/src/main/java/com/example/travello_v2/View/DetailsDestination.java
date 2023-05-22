package com.example.travello_v2.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.travello_v2.Adapter.AdapterRecycleViewUlasan;
import com.example.travello_v2.Adapter.ImagePagerAdapter;
import com.example.travello_v2.Api.DestinationData;
import com.example.travello_v2.Interface.DestinationDataListener;
import com.example.travello_v2.Models.DestinationModel;
import com.example.travello_v2.R;
import com.rd.PageIndicatorView;

import org.json.JSONObject;

import java.util.List;

public class DetailsDestination extends AppCompatActivity implements DestinationDataListener {

    private ViewPager viewPager;
    private ImagePagerAdapter adapter;
    private TextView title, lokasi, totalReview, deskripsi;
    private JSONObject data;
    private List<String> imageUrl;
    private PageIndicatorView pageIndicatorView;
    private RatingBar ratingBar;
    RecyclerView recyclerView;
    RecyclerView.Adapter recyclerViewAdapter;
    RecyclerView.LayoutManager recycleViewLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_destination);

        ratingBar = findViewById(R.id.rating);
        pageIndicatorView = findViewById(R.id.indicator);
        viewPager = findViewById(R.id.pagerImage);
        title = findViewById(R.id.title);
        lokasi = findViewById(R.id.lokasi);
        totalReview = findViewById(R.id.totalReview);
        deskripsi = findViewById(R.id.deskripsi);

        recyclerView = findViewById(R.id.recycler_ulasan);
        recyclerView.setHasFixedSize(true);

        recycleViewLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(recycleViewLayoutManager);




        String id = getIntent().getStringExtra("id");
        DestinationData destinationData = new DestinationData(id, this);
        destinationData.execute();


    }



    @Override
    public void onDestinationDataReceived(DestinationModel data, int statusCode, String message) {
        if (statusCode != 200){
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }
        System.out.println("Data : "+data+" Code  "+statusCode);

        ratingBar.setRating(data.getRating());
        title.setText(data.getName());
        lokasi.setText(data.getLocation());
        deskripsi.setText(data.getDescription());
        totalReview.setText(data.getTotalUlasan() + " Reviews");

        recyclerViewAdapter = new AdapterRecycleViewUlasan(this, data.getUlasan());
        recyclerView.setAdapter(recyclerViewAdapter);

        adapter = new ImagePagerAdapter(data.getUrlImage(), this);
        viewPager.setAdapter(adapter);
        pageIndicatorView.setViewPager(viewPager);


    }
}