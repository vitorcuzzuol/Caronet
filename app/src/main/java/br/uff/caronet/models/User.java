package br.uff.caronet.models;

public class User {

    private String id;
    private String name;
    private String email;
    private char gender;
    private int birth;
    private String password;
    private int celNumber;
    private boolean driver;
    private String carModel;
    private String carPlate;



    public User() {
        //Empty constructor is needed for firebase/firestore
    }

    public User(String name, String email, char gender, int birth, String password, int celNumber, boolean driver) {
        this.name = name;
        this.email = email;
        this.gender = gender;
        this.birth = birth;
        this.password = password;
        this.celNumber = celNumber;
        this.driver = driver;
    }

    public User(String name, String email, char gender, int birth, String password,
                int celNumber, boolean driver, String carModel, String carPlate) {
        this.name = name;
        this.email = email;
        this.gender = gender;
        this.birth = birth;
        this.password = password;
        this.celNumber = celNumber;
        this.driver = driver;
        this.carModel = carModel;
        this.carPlate = carPlate;
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

    public void setEmail(String email) {
        this.email = email;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public int getBirth() {
        return birth;
    }

    public void setBirth(int birth) {
        this.birth = birth;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getCelNumber() {
        return celNumber;
    }

    public void setCelNumber(int celNumber) {
        this.celNumber = celNumber;
    }

    public boolean isDriver() {
        return driver;
    }

    public void setDriver(boolean driver) {
        this.driver = driver;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public String getCarPlate() {
        return carPlate;
    }

    public void setCarPlate(String carPlate) {
        this.carPlate = carPlate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
