package com.example.travello_v2.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travello_v2.Models.UlasanModels;
import com.example.travello_v2.R;

import java.util.ArrayList;

public class AdapterRecycleViewUlasan extends RecyclerView.Adapter<AdapterRecycleViewUlasan.ViewHolder> {

    private ArrayList<UlasanModels> dataRating;

    private Context context;

    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout parretLayout;
        TextView userUlas, ulasan;
        RatingBar rating;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            userUlas = itemView.findViewById(R.id.user_ulas);
            ulasan = itemView.findViewById(R.id.ulasan_message);
            rating = itemView.findViewById(R.id.rating_ulas);
            parretLayout = itemView.findViewById(R.id.parret_ulasan);
        }
    }

    public AdapterRecycleViewUlasan(Context context, ArrayList<UlasanModels> data){
        this.context = context;
        this.dataRating = data;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_ulasan, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TextView user = holder.userUlas;
        TextView ulas = holder.ulasan;
        RatingBar rating = holder.rating;

        user.setText(dataRating.get(position).getUser());
        ulas.setText(dataRating.get(position).getMessage());
        rating.setRating(dataRating.get(position).getRating());

    }

    @Override
    public int getItemCount() {
        return dataRating.size();
    }
}
