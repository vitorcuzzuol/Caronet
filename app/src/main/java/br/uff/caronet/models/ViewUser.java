package br.uff.caronet.models;

import android.os.Parcel;
import android.os.Parcelable;

public class ViewUser implements Parcelable {

    private String id;
    private String name;
    private String photo;

    public ViewUser() {

    }

    public ViewUser(String id, String name/*, String photo*/){

        this.id = id;
        this.name = name;
        this.photo = photo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }



    /**Parceble Functions
     *
     * Used for moving an object from an activity/fragment to another.
     */

    public ViewUser(Parcel in) {
        id = in.readString();
        name = in.readString();
        photo = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(photo);
    }

    public static final Creator<ViewUser> CREATOR = new Creator<ViewUser>() {
        @Override
        public ViewUser createFromParcel(Parcel in) {
            return new ViewUser(in);
        }

        @Override
        public ViewUser[] newArray(int size) {
            return new ViewUser[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }


}
