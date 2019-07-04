package br.uff.caronet.models;

import java.util.List;

public class Zone {

    public Zone (){

    }

    private String name;
    private List<String> neighborhoods;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getNeighborhoods() {
        return neighborhoods;
    }

    public void setNeighborhoods(List<String> neighborhoods) {
        this.neighborhoods = neighborhoods;
    }
}
