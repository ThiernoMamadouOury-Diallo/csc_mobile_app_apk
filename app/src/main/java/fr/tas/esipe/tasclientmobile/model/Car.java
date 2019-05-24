package fr.tas.esipe.tasclientmobile.model;

public class Car {

    private int id;
    private String created_year;
    private String license_number;
    private String model;
    private String brand;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCreated_year() {
        return created_year;
    }

    public void setCreated_year(String created_year) {
        this.created_year = created_year;
    }

    public String getLicense_number() {
        return license_number;
    }

    public void setLicense_number(String license_number) {
        this.license_number = license_number;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
}
