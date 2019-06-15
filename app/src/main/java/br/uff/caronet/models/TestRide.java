package br.uff.caronet.models;

import java.util.Date;

public class TestRide {

    private Driver driver;
    private Date departure;
    private boolean goingToUff;

    public TestRide() {
    }

    public TestRide(Driver driver, Date departure, boolean goingToUff) {

        this.driver = driver;
        this.departure = departure;
        this.goingToUff = goingToUff;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
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
}
