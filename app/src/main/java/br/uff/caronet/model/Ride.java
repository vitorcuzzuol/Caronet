package br.uff.caronet.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Ride implements Parcelable {

    private String id;
    private ViewUser driver;
    private Neighborhood neighborhood;
    private List<ViewUser> passengers;
    private boolean goingToUff;
    private Date departure;
    private String campus;
    private int spots;
    private String description;
    private Car car;

    public Ride() {

    }

    public Ride(String id, ViewUser driver, Date departure, boolean goingToUff,
                String campus, Neighborhood neighborhood, List<ViewUser> passengers, int spots) {

        this.id = id;
        this.driver = driver;
        this.departure = departure;
        this.goingToUff = goingToUff;
        this.campus = campus;
        this.neighborhood = neighborhood;
        this.passengers = passengers;
        this.spots = spots;
    }

    public Ride(ViewUser driver, Date departure, boolean goingToUff,
                String campus, Neighborhood neighborhood, int spots) {

        this.driver = driver;
        this.departure = departure;
        this.goingToUff = goingToUff;
        this.campus = campus;
        this.neighborhood = neighborhood;
        this.spots = spots;
    }

    public void setNeighborhood(Neighborhood neighborhood) {
        this.neighborhood = neighborhood;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Neighborhood getNeighborhood() {
        return neighborhood;
    }

    public int getSpots() {
        return spots;
    }

    public void setSpots(int spots) {
        this.spots = spots;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    @Override
    public String toString() {
        return "Ride{" +
                "id='" + id + '\'' +
                ", driver=" + driver +
                ", neighborhood=" + neighborhood +
                ", passengers=" + passengers +
                ", goingToUff=" + goingToUff +
                ", departure=" + departure +
                ", campus='" + campus + '\'' +
                ", spots=" + spots +
                ", description='" + description + '\'' +
                ", car=" + car +
                '}';
    }

    //Used for moving an object from an activity/fragment to another.

    public static final Creator<Ride> CREATOR = new Creator<Ride>() {
        @Override
        public Ride createFromParcel(Parcel in) {
            return new Ride(in);
        }

        @Override
        public Ride[] newArray(int size) {
            return new Ride[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeParcelable(driver, flags);
        dest.writeParcelable(neighborhood, flags);
        dest.writeParcelable(car, flags);
        dest.writeTypedList(passengers);
        dest.writeInt (goingToUff ? 1 : 0);
        dest.writeString(campus);
        dest.writeLong(departure != null ? departure.getTime() : -1);
        dest.writeInt(spots);
        dest.writeString(id);
    }

    protected Ride(Parcel in) {

        driver = in.readParcelable(ViewUser.class.getClassLoader());
        neighborhood = in.readParcelable(Neighborhood.class.getClassLoader());
        car = in.readParcelable(Car.class.getClassLoader());
        passengers = new ArrayList<ViewUser>();
        in.readTypedList(passengers, ViewUser.CREATOR);
        goingToUff = (in.readInt() != 0);
        campus = in.readString();
        departure = in.readLong() == -1 ? null : new Date(in.readLong());
        spots = in.readInt();
        id = in.readString();
    }
}
