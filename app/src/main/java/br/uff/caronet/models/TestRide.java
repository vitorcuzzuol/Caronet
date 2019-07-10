package br.uff.caronet.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TestRide implements Parcelable {

    private ViewUser driver;
    private List<ViewUser> passengers;
    private boolean goingToUff;
    private Date departure;
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

    public static final Creator<TestRide> CREATOR = new Creator<TestRide>() {
        @Override
        public TestRide createFromParcel(Parcel in) {
            return new TestRide(in);
        }

        @Override
        public TestRide[] newArray(int size) {
            return new TestRide[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeParcelable(driver, flags);
        dest.writeTypedList(passengers);
        dest.writeInt (goingToUff ? 1 : 0);
        dest.writeString(campus);
        dest.writeString(neighborhood);
        dest.writeLong(departure != null ? departure.getTime() : -1);

    }

    protected TestRide(Parcel in) {

        driver = in.readParcelable(ViewUser.class.getClassLoader());
        passengers = new ArrayList<ViewUser>();
        in.readTypedList(passengers, ViewUser.CREATOR);
        goingToUff = (in.readInt() != 0);
        campus = in.readString();
        neighborhood = in.readString();
        departure = in.readLong() == -1 ? null : new Date(in.readLong());

    }
}
