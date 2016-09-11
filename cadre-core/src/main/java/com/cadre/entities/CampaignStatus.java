package com.cadre.entities;


public enum CampaignStatus {
    DRAFT("DRAFT"), READY("READY"), RUNNING("RUNNING"), COMPLETE("COMPLETE");

    private String name;

    CampaignStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public static CampaignStatus getCampaignStatus(String agent) {
        for (CampaignStatus value : CampaignStatus.values()) {
            if (value.getName().equals(agent)) {
                return value;
            }
        }
        return null;
    }
    }
