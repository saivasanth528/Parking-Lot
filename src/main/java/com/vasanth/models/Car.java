package com.vasanth.models;

import lombok.Getter;


public class Car {
    @Getter
    private String registrationNumber;
    @Getter
    private String colour;

    public Car(final String registrationNumber, final String colour) {
        this.registrationNumber = registrationNumber;
        this.colour = colour;
    }

}
