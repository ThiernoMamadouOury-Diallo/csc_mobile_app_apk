package fr.tas.esipe.tasclientmobile.model;

import com.google.gson.annotations.SerializedName;

public class ParkingPlace {

    @SerializedName("id_parking_place")
    int idParkingPlace;
    @SerializedName("is_occupied")
    boolean isOccupied;
    @SerializedName("width")
    float width;
    @SerializedName("height")
    float height;

}
