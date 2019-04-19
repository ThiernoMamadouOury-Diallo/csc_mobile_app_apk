package fr.tas.esipe.tasclientmobile.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Parking {

    @SerializedName("id_parking")
    int idParking;
    int capacity;
    @SerializedName("type_parking")
    TypeParking typeParking;
    String address;
    private Double latitude;
    private Double longitude;
    @SerializedName("list_places")
    List<ParkingPlace> listParkingPlace;

    public int getIdParking() {
        return idParking;
    }

    public void setIdParking(int idParking) {
        this.idParking = idParking;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public TypeParking getTypeParking() {
        return typeParking;
    }

    public void setTypeParking(TypeParking typeParking) {
        this.typeParking = typeParking;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public List<ParkingPlace> getListParkingPlace() {
        return listParkingPlace;
    }

    public void setListParkingPlace(List<ParkingPlace> listParkingPlace) {
        this.listParkingPlace = listParkingPlace;
    }
}
