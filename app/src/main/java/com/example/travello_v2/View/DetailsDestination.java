package com.example.travello_v2.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.travello_v2.Adapter.AdapterRecycleViewUlasan;
import com.example.travello_v2.Adapter.ImagePagerAdapter;
import com.example.travello_v2.Api.DestinationData;
import com.example.travello_v2.Api.PostUlasan;
import com.example.travello_v2.Interface.DestinationDataListener;
import com.example.travello_v2.Interface.StatusMessageDataListener;
import com.example.travello_v2.Models.DestinationModel;
import com.example.travello_v2.R;
import com.rd.PageIndicatorView;

import org.json.JSONObject;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class DetailsDestination extends AppCompatActivity implements DestinationDataListener {

    private ViewPager viewPager;
    private ImagePagerAdapter adapter;
    private TextView title, lokasi, totalReview, deskripsi;
    private JSONObject data;
    private List<String> imageUrl;
    private PageIndicatorView pageIndicatorView;
    private RatingBar ratingBar;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter recyclerViewAdapter;
    private RecyclerView.LayoutManager recycleViewLayoutManager;
    private Dialog dialog;
    private Dialog dialogStatus;

    private String id;
    private boolean isLogin;

    private Button addUlasan;


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
        addUlasan = findViewById(R.id.berikanUlasan);

        recyclerView = findViewById(R.id.recycler_ulasan);
        recyclerView.setHasFixedSize(true);

        recycleViewLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(recycleViewLayoutManager);

        this.id = getIntent().getStringExtra("id");
        DestinationData destinationData = new DestinationData(id, this);
        destinationData.execute();

        TokenManager tokenManager = new TokenManager(this);
        String token = tokenManager.getToken();
        this.isLogin = tokenManager.hasToken();
        addUlasan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLogin){
                    initAddUlasan(token);
                    dialog.show();
                }else {
                    statusDialog("Fail", "Harus Login", false);
                    dialogStatus.show();
                }
            }
        });
    }

    private void initAddUlasan(String token){
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_add_ulasan);
        dialog.setCancelable(false);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        dialog.getWindow().setAttributes(lp);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));



        RatingBar ratingAdd = dialog.findViewById(R.id.rating);
        EditText message = dialog.findViewById(R.id.message_ulasan);
        Button btnSend = dialog.findViewById(R.id.send);
        Button btnClose = dialog.findViewById(R.id.close);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float rating = ratingAdd.getRating();
                String ulasanMessage = message.getText().toString();
                dialog.cancel();

                PostUlasan post = new PostUlasan(token, id, ulasanMessage, rating, new StatusMessageDataListener() {
                    @Override
                    public void onStatusMessageDataReceived(int statusCode, String message) {
                        if (statusCode == 200){
                            statusDialog("Success", message, true);
                        }else{
                            statusDialog("Fail", message, false);
                        }
                        dialogStatus.show();
                    }
                });
                post.execute();


            }
        });

    }



    private void statusDialog(String title, String message, boolean success){
        dialogStatus = new Dialog(this);
        dialogStatus.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogStatus.setContentView(R.layout.dialog_message);
        dialogStatus.setCancelable(!success);

        dialogStatus.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ImageView imageView = dialogStatus.findViewById(R.id.icon);

        TextView etTitle = dialogStatus.findViewById(R.id.title);
        TextView etMessage = dialogStatus.findViewById(R.id.message);
        Button etButton = dialogStatus.findViewById(R.id.btnInput);

        etTitle.setText(title);
        etMessage.setText(message);
        System.out.println(message);

        if (success){
            etTitle.setTextColor(ContextCompat.getColor(this, R.color.success));
            imageView.setImageResource(R.drawable.truee);
            etButton.setBackgroundColor(ContextCompat.getColor(this, R.color.success));
            final boolean[] buttonClicked = {false};

            etButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    buttonClicked[0] = true;
                    Intent intent = new Intent(getApplicationContext(), DetailsDestination.class);
                    intent.putExtra("id", id);
                    startActivity(intent);
                    finish();
                }
            });
            new CountDownTimer(6000, 1000){

                @Override
                public void onTick(long millisUntilFinished) {
                    etButton.setText(String.format(
                            Locale.getDefault(), "close (%d)", TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) + 1
                    ));
                }

                @Override
                public void onFinish() {
                    if(!buttonClicked[0]){
                        Intent intent = new Intent(getApplicationContext(), DetailsDestination.class);
                        intent.putExtra("id", id);
                        startActivity(intent);
                        finish();
                    }else {
                        dialogStatus.dismiss();
                    }

                }
            }.start();
        } else  {
            etTitle.setTextColor(ContextCompat.getColor(this, R.color.fail));
            imageView.setImageResource(R.drawable.falsee);
            etButton.setBackgroundColor(ContextCompat.getColor(this, R.color.fail));
            etButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!isLogin){
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }else{
                        dialogStatus.dismiss();
                    }
                }
            });
            new CountDownTimer(6000, 1000){

                @Override
                public void onTick(long millisUntilFinished) {
                    if (!isLogin){
                        etButton.setText(String.format(
                                Locale.getDefault(), "Login (%d)", TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) + 1
                        ));
                    }else{
                        etButton.setText(String.format(
                                Locale.getDefault(), "close (%d)", TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) + 1
                        ));
                    }
                }

                @Override
                public void onFinish() {
                    dialogStatus.dismiss();
                }
            }.start();
        }

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

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, DashboardActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }

        if (dialogStatus != null && dialogStatus.isShowing()) {
            dialogStatus.dismiss();
        }
    }
}