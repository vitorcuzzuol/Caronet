package br.uff.caronet.models;

public class TestUser {

    private String name;
    private  String email;
    private boolean driver;

    public TestUser() {
    }

    public TestUser(String name, String email, Boolean driver) {
        this.name = name;
        this.email = email;
        this.driver = driver;
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

}
