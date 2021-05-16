package com.example.planify;

public class Room {

    private String description;
    private String image;
    private String name;
    private String price;
    private String rentDetails;
    private long seats;

    Room(){

    }

    public Room(String description, String image, String name, String price, String rentDetails, long seats) {
        this.description = description;
        this.image = image;
        this.name = name;
        this.price = price;
        this.rentDetails = rentDetails;
        this.seats = seats;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getRentDetails() {
        return rentDetails;
    }

    public void setRentDetails(String rentDetails) {
        this.rentDetails = rentDetails;
    }

    public long getSeats() {
        return seats;
    }

    public String getName() {
        return name;
    }
    public String getPrice() {
        return price;
    }

    public void setSeats(long seats) {
        this.seats = seats;
    }
}
