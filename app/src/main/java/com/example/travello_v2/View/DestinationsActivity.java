package com.example.travello_v2.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.travello_v2.Adapter.RecycleViewDestination;
import com.example.travello_v2.Api.DestinationsData;
import com.example.travello_v2.Interface.DestinationsDataListener;
import com.example.travello_v2.Models.DestinationModels;
import com.example.travello_v2.R;

import java.util.ArrayList;
import java.util.List;

public class DestinationsActivity extends AppCompatActivity implements DestinationsDataListener, AdapterView.OnItemSelectedListener {

    private String category;

    RecyclerView recyclerView;
    RecyclerView.Adapter recyclerViewAdapter;
    RecyclerView.LayoutManager recycleViewLayoutManager;
    EditText search;
    String valSearch;
    TextView itemCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_destinations);

        recyclerView = findViewById(R.id.recyc);
        recyclerView.setHasFixedSize(true);
        itemCount = findViewById(R.id.count);

        Spinner spinnerFilter = findViewById(R.id.filter);

        ArrayAdapter<CharSequence>adapter=ArrayAdapter.createFromResource(this, R.array.filter, R.layout.spinner_item_selected);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_list);

        spinnerFilter.setAdapter(adapter);
        spinnerFilter.setOnItemSelectedListener(this);

        String selection = getIntent().getStringExtra("cat");
        int spinnerPosition = adapter.getPosition(selection);
        spinnerFilter.setSelection(spinnerPosition);

        valSearch = getIntent().getStringExtra("search");

        search = findViewById(R.id.search);
        search.setText(valSearch);

        Button btnSearch = findViewById(R.id.search_button);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                valSearch = search.getText().toString();
                getDataDestination();
            }
        });

        Button btnBack = findViewById(R.id.back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DestinationsActivity.this, DashboardActivity.class);
                startActivity(intent);
            }
        });

    }

    public void getDataDestination(){
        if(valSearch == null) {
            valSearch = "";
        }
        if(category == null){
            category ="";
        }

        DestinationsData destinationData = new DestinationsData(valSearch, category, this);
        destinationData.execute();
    }

    @Override
    public void onDestinationDataReceived(ArrayList<DestinationModels> destinationModels, int statusCode, String message) {

        if ( this != null) {
            recycleViewLayoutManager = new LinearLayoutManager(this);
            recyclerViewAdapter = new RecycleViewDestination(this, destinationModels);
            recyclerView.setLayoutManager(recycleViewLayoutManager);
            recyclerView.setAdapter(recyclerViewAdapter);

            itemCount.setText(recyclerViewAdapter.getItemCount()+" destinations found");
            if (statusCode != 200){
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            }
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        List<String> catList = new ArrayList<>();
        catList.add("");
        catList.add("mountain");
        catList.add("beach");
        catList.add("hotel");

        this.category = catList.get(position);

        getDataDestination();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}