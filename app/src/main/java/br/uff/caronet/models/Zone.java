package br.uff.caronet.models;

import java.util.List;

public class Zone {


    private String name;
    private String city;
    private List<String> neighborhoods;

    public Zone (){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public List<String> getNeighborhoods() {
        return neighborhoods;
    }

    public void setNeighborhoods(List<String> neighborhoods) {
        this.neighborhoods = neighborhoods;
    }
}
