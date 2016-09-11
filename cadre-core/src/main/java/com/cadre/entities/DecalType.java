package com.cadre.entities;


public enum DecalType {
    FULL("FULL"), HALF("HALF"), PANEL("PANEL");

    private String name;

    DecalType(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public static DecalType getDecalType(String agent) {
        for (DecalType value : DecalType.values()) {
            if (value.getName().equals(agent)) {
                return value;
            }
        }
        return null;
    }
    }
