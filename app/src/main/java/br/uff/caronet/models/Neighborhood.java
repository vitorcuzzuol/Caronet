package br.uff.caronet.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Neighborhood implements Parcelable {

    private String name;
    private String zone;
    private String city;

    public Neighborhood() {

    }

    public Neighborhood(String name, String zone, String city) {
        this.name = name;
        this.zone = zone;
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }



    protected Neighborhood(Parcel in) {
        name = in.readString();
        zone = in.readString();
        city = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(zone);
        dest.writeString(city);
    }

    public static final Creator<Neighborhood> CREATOR = new Creator<Neighborhood>() {
        @Override
        public Neighborhood createFromParcel(Parcel in) {
            return new Neighborhood(in);
        }

        @Override
        public Neighborhood[] newArray(int size) {
            return new Neighborhood[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

}
