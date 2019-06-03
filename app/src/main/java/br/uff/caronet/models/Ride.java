package br.uff.caronet.models;

import java.util.List;

public class Ride {

    private User Driver;
    private ChatMessage chat;
    private List<User> passengers;
    private boolean goingToUFF;
    private Campi campus;
    private int spots;


    public Ride() {
        // Needed for firebase/firestore
    }


    public Ride(User driver, ChatMessage chat, List<User> passengers, boolean goingToUFF, Campi campus, int spots) {
        Driver = driver;
        this.chat = chat;
        this.passengers = passengers;
        this.goingToUFF = goingToUFF;
        this.campus = campus;
        this.spots = spots;
    }

    public int getSpots() {
        return spots;
    }

    public void setSpots(int spots) {
        this.spots = spots;
    }


    public User getDriver() {
        return Driver;
    }

    public void setDriver(User driver) {
        Driver = driver;
    }

    public ChatMessage getChat() {
        return chat;
    }

    public void setChat(ChatMessage chat) {
        this.chat = chat;
    }

    public List<User> getPassengers() {
        return passengers;
    }

    public void setPassengers(List<User> passengers) {
        this.passengers = passengers;
    }

    public boolean isGoingToUFF() {
        return goingToUFF;
    }

    public void setGoingToUFF(boolean goingToUFF) {
        this.goingToUFF = goingToUFF;
    }

    public Campi getCampus() {
        return campus;
    }

    public void setCampus(Campi campus) {
        this.campus = campus;
    }
}
