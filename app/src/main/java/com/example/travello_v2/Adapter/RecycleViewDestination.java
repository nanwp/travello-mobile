package com.example.travello_v2.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travello_v2.View.DetailsDestination;
import com.example.travello_v2.Models.DestinationModels;
import com.example.travello_v2.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class RecycleViewDestination extends RecyclerView.Adapter<RecycleViewDestination.ViewHolder>{
    private ArrayList<DestinationModels> dataDestination;

    private Context context;

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textName;
        TextView textLocation;
        ImageView imageIcon;
        RatingBar ratingBar;
        TextView totalUlasan;

        RelativeLayout parretLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            textName = itemView.findViewById(R.id.judul_destinasi);
            textLocation = itemView.findViewById(R.id.lokasi_destionasi);
            imageIcon = itemView.findViewById(R.id.image_destinasi);
            parretLayout = itemView.findViewById(R.id.paretDestination);
            ratingBar = itemView.findViewById(R.id.rating_list);
            totalUlasan = itemView.findViewById(R.id.total_ulasan);
        }
    }

    public RecycleViewDestination(Context context, ArrayList<DestinationModels> dataDestination) {
        this.context = context;
        this.dataDestination = dataDestination;
    }

    @NonNull
    @Override
    public RecycleViewDestination.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_destination, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleViewDestination.ViewHolder holder, int position) {

        TextView textName = holder.textName;
        TextView textLocation = holder.textLocation;
        ImageView imageIcon = holder.imageIcon;
        RatingBar ratingBar = holder.ratingBar;
        TextView totalUlasan = holder.totalUlasan;

        ratingBar.setRating(dataDestination.get(position).getRating());
        textName.setText(dataDestination.get(position).getName());
        textLocation.setText(dataDestination.get(position).getLocation());
        totalUlasan.setText(dataDestination.get(position).getTotalUlasan() + " Reviews");

        JSONArray imageArray = dataDestination.get(position).getImage();

        String imageUrl = null;
        try {
            imageUrl = (String) imageArray.get(0);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Picasso.get().load(imageUrl).resize(320, 180 ).placeholder(R.mipmap.ic_launcher).into(imageIcon);

        holder.parretLayout.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailsDestination.class);
            intent.putExtra("id", dataDestination.get(position).getId());


            context.startActivity(intent);
        });
    }


    @Override
    public int getItemCount() {
        if (dataDestination == null){
            return 0;
        }
        return dataDestination.size();
    }
}
