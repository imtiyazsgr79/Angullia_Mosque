package com.sg.angulliamosque.models;

public class EquipmentSystem {
    int id;
    String systemName,category;

    public EquipmentSystem(int id) {
        this.id = id;

    }

    public EquipmentSystem(int id, String systemName) {
        this.id = id;
        this.systemName = systemName;
    }

    public EquipmentSystem(int id, String systemName, String category) {
        this.id = id;
        this.systemName = systemName;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public String getSystemName() {
        return systemName;
    }

    public String getCategory() {
        return category;
    }
}
