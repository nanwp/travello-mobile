package com.example.travello_v2;

import com.example.travello_v2.Models.DestinationModels;

import java.util.ArrayList;

public interface DestinationDataListener {
    void onDestinationDataReceived(ArrayList<DestinationModels> destinationModels, int statusCode, String message);
}
