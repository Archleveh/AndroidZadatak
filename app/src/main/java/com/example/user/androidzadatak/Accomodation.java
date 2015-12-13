package com.example.user.androidzadatak;

import java.util.ArrayList;

/**
 * Razred koji definira strukturu informacija o hotelima
 */
public class Accomodation {

    private int id;
    private ArrayList<String> image;
    private String name;
    private String streetAddress;
    private String cityAddress;
    private int rating;
    private String description;

    public int getId() {return id;}
    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<String> getImage() {
        return image;
    }
    public void setImage(ArrayList<String> image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getStreetAddress() {return streetAddress;}
    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getCityAddress() {
        return cityAddress;
    }
    public void setCityAddress(String cityAddress) {
        this.cityAddress = cityAddress;
    }

    public int getRating() {
        return rating;
    }
    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
}
