package com.github.exper0.codechallenge;

public class CityNotFoundException extends Exception {
    private final String city;

    public CityNotFoundException(String city) {
        super("city '" + city + "' is not found");
        this.city = city;
    }

    public String getCity() {
        return city;
    }
}
