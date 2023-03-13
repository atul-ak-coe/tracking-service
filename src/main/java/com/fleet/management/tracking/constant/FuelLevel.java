package com.fleet.management.tracking.constant;

public enum FuelLevel {
    FULL("FULL"),
    HALF("HALF"),
    EMPTY("EMPTY");

    private String name;

    FuelLevel(String name) {
        this.name = name;
    }

    public String getName() {
        return  name;
    }
}
