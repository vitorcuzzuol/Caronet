package br.uff.caronet.models;

public class Driver {

    private String driverId;
    private String name;

    public Driver() {
    }

    public Driver(String driverId, String name) {
        this.driverId = driverId;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }
}
