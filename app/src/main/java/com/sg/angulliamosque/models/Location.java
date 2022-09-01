package com.sg.angulliamosque.models;

public class Location {
    int id;
    String loc_id,loc_desc,priorityID,costCenterID,streetName;
    double lat,lng;

    public Location(int id) {
        this.id = id;
    }

    public Location(int id, String loc_id) {
        this.id = id;
        this.loc_id = loc_id;
    }

    public int getId() {
        return id;
    }

    public String getLoc_id() {
        return loc_id;
    }

    public String getLoc_desc() {
        return loc_desc;
    }

    public String getPriorityID() {
        return priorityID;
    }

    public String getCostCenterID() {
        return costCenterID;
    }

    public String getStreetName() {
        return streetName;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }
}
