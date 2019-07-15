package br.uff.caronet.models;

public class User {

    private String id;
    private String name;
    private  String email;
    private boolean driver;
    private Car car;
    private String photo;
    private char gender;
    private long phone;

    public User() {
    }

    public User(String name, String email, Boolean driver) {

        this.name = name;
        this.email = email;
        this.driver = driver;
    }

    public User(String id, String name, String email, boolean driver,
                Car car, String photo, char gender, long phone) {

        this.id = id;
        this.name = name;
        this.email = email;
        this.driver = driver;
        this.car = car;
        this.photo = photo;
        this.gender = gender;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public boolean isDriver() {
        return driver;
    }

    public void setDriver(boolean driver) {
        this.driver = driver;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public long getPhone() {
        return phone;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }
}
