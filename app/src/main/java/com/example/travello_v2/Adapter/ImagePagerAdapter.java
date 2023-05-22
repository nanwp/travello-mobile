package com.example.travello_v2.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.travello_v2.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ImagePagerAdapter extends PagerAdapter {
    private List<String> imageUrlList;
    private Context context;

    public ImagePagerAdapter(List<String> imageUrlList, Context context){
        this.imageUrlList = imageUrlList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return imageUrlList.size();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_image, container, false);
        ImageView imageView = view.findViewById(R.id.imageView);
        String imageUrl = imageUrlList.get(position);
        Picasso.get().load(imageUrl).into(imageView);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }
}
