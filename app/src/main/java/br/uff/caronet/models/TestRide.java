package br.uff.caronet.models;

import java.util.Date;
import java.util.List;

public class TestRide {

    private ViewUser driver;
    private Date departure;
    private boolean goingToUff;
    private List<ViewUser> passengers;
    private String campus;
    private String neighborhood;

    public TestRide() {

    }

    public TestRide(ViewUser driver, Date departure, boolean goingToUff,
                    String campus, String neighborhood, List<ViewUser> passengers) {

        this.driver = driver;
        this.departure = departure;
        this.goingToUff = goingToUff;
        this.campus = campus;
        this.neighborhood = neighborhood;
        this.passengers = passengers;
    }

    public ViewUser getDriver() {
        return driver;
    }

    public void setDriver(ViewUser driver) {
        this.driver = driver;
    }

    public Date getDeparture() {
        return departure;
    }

    public void setDeparture(Date departure) {
        this.departure = departure;
    }

    public boolean isGoingToUff() {
        return goingToUff;
    }

    public void setGoingToUff(boolean goingToUff) {
        this.goingToUff = goingToUff;
    }

    public List<ViewUser> getPassengers() {
        return passengers;
    }

    public void setPassengers(List<ViewUser> passengers) {
        this.passengers = passengers;
    }

    public String getCampus() {
        return campus;
    }

    public void setCampus(String campus) {
        this.campus = campus;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }
}
