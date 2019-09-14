package com.vytrack.tests;

import com.google.gson.annotations.SerializedName;

public class Region {
    @SerializedName("region_id")
    private Integer region_id;
    @SerializedName("region_name")
    private String region_name;

    public Integer getRegion_id() {
        return region_id;
    }

    public void setRegion_id(Integer region_id) {
        this.region_id = region_id;
    }

    public String getRegion_name() {
        return region_name;
    }

    public void setRegion_name(String region_name) {
        this.region_name = region_name;
    }
}
