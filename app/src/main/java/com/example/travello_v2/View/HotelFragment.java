package com.example.travello_v2.View;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.travello_v2.Adapter.RecycleViewDestination;
import com.example.travello_v2.Api.ApiClient;
import com.example.travello_v2.Api.DestinationData;
import com.example.travello_v2.Interface.DestinationDataListener;
import com.example.travello_v2.Models.DestinationModels;
import com.example.travello_v2.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HotelFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class HotelFragment extends Fragment implements DestinationDataListener{

    RecyclerView recyclerView;
    RecyclerView.Adapter recyclerViewAdapter;
    RecyclerView.LayoutManager recycleViewLayoutManager;
    ArrayList<DestinationModels> data;
    EditText search;
    TextView itemCount;



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HotelFragment() {
        // Required empty public constructor
    }

    public static HotelFragment newInstance(String param1, String param2) {
        HotelFragment fragment = new HotelFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hotel, container, false);
        recyclerView = view.findViewById(R.id.recyc);
        recyclerView.setHasFixedSize(true);

        DestinationData destinationData = new DestinationData("","hotel", this);
        destinationData.execute();

        itemCount = view.findViewById(R.id.count);


        search = view.findViewById(R.id.search);
        AppCompatButton btnSearch = view.findViewById(R.id.search_button);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String valSearch = search.getText().toString();
                DestinationData destinationData = new DestinationData(valSearch, "hotel", new DestinationDataListener() {
                    @Override
                    public void onDestinationDataReceived(ArrayList<DestinationModels> destinationModels, int statusCode, String message) {
                        recycleViewLayoutManager = new LinearLayoutManager(getContext());
                        recyclerViewAdapter = new RecycleViewDestination(getContext(), destinationModels);
                        recyclerView.setLayoutManager(recycleViewLayoutManager);
                        recyclerView.setAdapter(recyclerViewAdapter);
                        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                        itemCount.setText("item count : "+recyclerViewAdapter.getItemCount());
                    }
                });
                destinationData.execute();
            }
        });

        return view;
    }

    @Override
    public void onDestinationDataReceived(ArrayList<DestinationModels> destinationModels, int statusCode, String message) {
        recycleViewLayoutManager = new LinearLayoutManager(getContext());
        recyclerViewAdapter = new RecycleViewDestination(getContext(), destinationModels);
        recyclerView.setLayoutManager(recycleViewLayoutManager);
        recyclerView.setAdapter(recyclerViewAdapter);
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        itemCount.setText("item count : "+recyclerViewAdapter.getItemCount());
    }
}