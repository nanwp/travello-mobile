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
import com.example.travello_v2.Api.DestinationsData;
import com.example.travello_v2.Interface.DestinationsDataListener;
import com.example.travello_v2.Models.DestinationModels;
import com.example.travello_v2.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MounthFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MounthFragment extends Fragment implements DestinationsDataListener {

    RecyclerView recyclerView;
    RecyclerView.Adapter recyclerViewAdapter;
    RecyclerView.LayoutManager recycleViewLayoutManager;
    EditText search;
    TextView itemCount;



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MounthFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MounthFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MounthFragment newInstance(String param1, String param2) {
        MounthFragment fragment = new MounthFragment();
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
        View view = inflater.inflate(R.layout.fragment_mounth, container, false);
        recyclerView = view.findViewById(R.id.recyc);
        recyclerView.setHasFixedSize(true);


        DestinationsData destinationData = new DestinationsData("","mountain", this);
        destinationData.execute();

        itemCount = view.findViewById(R.id.count);


        search = view.findViewById(R.id.search);
        AppCompatButton btnSearch = view.findViewById(R.id.search_button);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String valSearch = search.getText().toString();
                DestinationsData destinationData = new DestinationsData(valSearch, "mountain", new DestinationsDataListener() {
                    @Override
                    public void onDestinationDataReceived(ArrayList<DestinationModels> destinationModels, int statusCode, String message) {
                        recycleViewLayoutManager = new LinearLayoutManager(getContext());
                        recyclerViewAdapter = new RecycleViewDestination(getContext(), destinationModels);
                        recyclerView.setLayoutManager(recycleViewLayoutManager);
                        recyclerView.setAdapter(recyclerViewAdapter);
                        itemCount.setText(recyclerViewAdapter.getItemCount()+" mountains found");
                        if (statusCode != 200){
                            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                destinationData.execute();
            }
        });

        return view;
    }

    @Override
    public void onDestinationDataReceived(ArrayList<DestinationModels> destinationModels, int statusCode, String message) {
        if (getContext() != null) {
            recycleViewLayoutManager = new LinearLayoutManager(getContext());
            recyclerViewAdapter = new RecycleViewDestination(getContext(), destinationModels);
            recyclerView.setLayoutManager(recycleViewLayoutManager);
            recyclerView.setAdapter(recyclerViewAdapter);
            itemCount.setText(recyclerViewAdapter.getItemCount()+" mountains found");

            if (statusCode != 200){
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
            }

        }
    }
}