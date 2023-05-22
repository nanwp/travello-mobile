package com.example.travello_v2.Interface;

import com.example.travello_v2.Models.DestinationModels;

import java.util.ArrayList;

public interface DestinationsDataListener {
    void onDestinationDataReceived(ArrayList<DestinationModels> destinationModels, int statusCode, String message);
}
