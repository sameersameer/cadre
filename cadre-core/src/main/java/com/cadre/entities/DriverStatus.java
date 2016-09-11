package com.cadre.entities;


public enum DriverStatus {
    REGISTERED("REGISTERED"), ONBOARDED("ONBOARDED"), ACTIVE("ACTIVE"), INACTIVE("INACTIVE");

    private String name;

    DriverStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public static DriverStatus getDriverStatus(String agent) {
        for (DriverStatus value : DriverStatus.values()) {
            if (value.getName().equals(agent)) {
                return value;
            }
        }
        return null;
    }
    }
