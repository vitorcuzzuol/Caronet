package br.uff.caronet.models;


import android.os.Parcel;
import android.os.Parcelable;

public class Car implements Parcelable {

    private String plate;
    private String model;

    public Car() {

    }

    public Car(String plate, String model) {
        this.plate = plate;
        this.model = model;
    }


    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }


    protected Car(Parcel in) {
        plate = in.readString();
        model = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(plate);
        dest.writeString(model);
    }

    public static final Creator<Car> CREATOR = new Creator<Car>() {
        @Override
        public Car createFromParcel(Parcel in) {
            return new Car(in);
        }

        @Override
        public Car[] newArray(int size) {
            return new Car[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }


}
