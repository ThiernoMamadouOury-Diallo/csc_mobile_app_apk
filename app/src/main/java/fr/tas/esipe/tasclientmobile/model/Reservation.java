package fr.tas.esipe.tasclientmobile.model;

public class Reservation {

    private int id;
    private String prevision_start;
    private String prevision_end;
    private String current_state;
    private Parking parking_start;
    private Parking parking_end;
    private Car car;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPrevision_start() {
        return prevision_start;
    }

    public void setPrevision_start(String prevision_start) {
        this.prevision_start = prevision_start;
    }

    public String getPrevision_end() {
        return prevision_end;
    }

    public void setPrevision_end(String prevision_end) {
        this.prevision_end = prevision_end;
    }

    public String getCurrent_state() {
        return current_state;
    }

    public void setCurrent_state(String current_state) {
        this.current_state = current_state;
    }

    public Parking getParking_start() {
        return parking_start;
    }

    public void setParking_start(Parking parking_start) {
        this.parking_start = parking_start;
    }

    public Parking getParking_end() {
        return parking_end;
    }

    public void setParking_end(Parking parking_end) {
        this.parking_end = parking_end;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }
}
