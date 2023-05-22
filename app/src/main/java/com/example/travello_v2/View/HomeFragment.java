package com.example.travello_v2.View;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.travello_v2.Adapter.RecycleViewDestination;
import com.example.travello_v2.Api.DestinationsData;
import com.example.travello_v2.Interface.DestinationsDataListener;
import com.example.travello_v2.Models.DestinationModels;
import com.example.travello_v2.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment implements DestinationsDataListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView recyclerView;
    private RecyclerView recyclerView2;

    private RecycleViewDestination adapterRecyclerView;
    private RecycleViewDestination adapterRecyclerView2;

    private LinearLayoutManager linearLayoutManager;
    private LinearLayoutManager linearLayoutManager2;


    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        View view = inflater.inflate(R.layout.fragment_home, container, false);



        recyclerView = view.findViewById(R.id.recycleView);
        recyclerView2 = view.findViewById(R.id.recycleView2);

        DestinationsData destinationData = new DestinationsData("","", this);
        destinationData.execute();



        Button btnMountain = view.findViewById(R.id.mount_button);
        btnMountain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), DestinationsActivity.class);
                intent.putExtra("cat", "Mountain");
                startActivity(intent);
            }
        });

        Button btnBeach = view.findViewById(R.id.beach_button);
        btnBeach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), DestinationsActivity.class);
                intent.putExtra("cat", "Beach");
                startActivity(intent);
            }
        });


        Button btnHotel = view.findViewById(R.id.hotel_button);
        btnHotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), DestinationsActivity.class);
                intent.putExtra("cat", "Hotel");
                startActivity(intent);
            }
        });

        Button btnSearch = view.findViewById(R.id.search_button);
        EditText search = view.findViewById(R.id.search);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String valSearch = search.getText().toString();
                Intent intent = new Intent(getContext(), DestinationsActivity.class);
                intent.putExtra("search", valSearch);
                startActivity(intent);
            }
        });

        return view;
    }


    @Override
    public void onDestinationDataReceived(ArrayList<DestinationModels> destinationModels, int statusCode, String message) {


        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        adapterRecyclerView = new RecycleViewDestination(getContext(), destinationModels);

        linearLayoutManager2 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        adapterRecyclerView2 = new RecycleViewDestination(getContext(), destinationModels);

        recyclerView2.setLayoutManager(linearLayoutManager2);
        recyclerView2.setAdapter(adapterRecyclerView2);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapterRecyclerView);

    }
}