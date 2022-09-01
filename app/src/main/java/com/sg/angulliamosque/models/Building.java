package com.sg.angulliamosque.models;

public class Building {
    int id;
    String build_name,build_desc,priority_id,zone_id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBuild_name() {
        return build_name;
    }


    public Building(int id) {
        this.id = id;
    }

    public Building(int id, String build_name, String build_desc, String priority_id, String zone_id) {
        this.id = id;
        this.build_name=build_name;

    }
}
