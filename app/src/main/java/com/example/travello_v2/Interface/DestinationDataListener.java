package com.example.travello_v2.Interface;

import com.example.travello_v2.Models.DestinationModel;

public interface DestinationDataListener {
    void onDestinationDataReceived(DestinationModel data, int statusCode, String message);

}
