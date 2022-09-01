package com.sg.angulliamosque.models;

public class EquipmnetSubSystem {



    int id;
     String subSystemName;

    public EquipmnetSubSystem(int id) {
        this.id = id;

    }


    public EquipmnetSubSystem(int id, String subSystemName) {
        this.id = id;
        this.subSystemName = subSystemName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubSystemName() {
        return subSystemName;
    }

    public void setSubSystemName(String subSystemName) {
        this.subSystemName = subSystemName;
    }
}
