package com.sg.angulliamosque.models;

public class CreateAssetRequest {
    Long id;
    double life;
    String dateOfTagging;
    String assetstatus;
    String assetNo;
    String assettype;
    String subLocation;
    String make;
    String coolingCapacity, typeOfFcu, airFlow;
    String cuRef, cuLocation, weightt, dimension, electricPowerSupply, isolatorSize, remarks, equipmentCode;
    String supplierName, supplierAddress, contactNo, fcuRef;
    String qty, fanRef, staticPressure, dia, warrantyPeriod, power, fullLoadCurrent, cpRef, powerRequirement;
    String serialNumber;
    String name;
    String dateOfAquistion;
    String cost;
    String user;
    String intallationDate;
    Building building;
    Location location;
    EquipmentSystem equipmentSystem;
    EquipmnetSubSystem subtype;
    String brandd;
    String description;
    String unit;

    int frequency;

    //update
    public CreateAssetRequest(int frequency, String unit, String description, String brandd, String dateOfTagging, Long id, double life,
                              String assetstatus, String assetNo, String assettype, String subLocation, String make,
                              String coolingCapacity, String typeOfFcu, String airFlow, String cuRef, String cuLocation, String weightt,
                              String dimension, String electricPowerSupply, String isolatorSize, String remarks, String equipmentCode,
                              String supplierName, String supplierAddress, String contactNo, String fcuRef, String qty, String fanRef,
                              String staticPressure, String dia, String warrantyPeriod, String power, String fullLoadCurrent,
                              String cpRef, String powerRequirement, String serialNumber, String name, String dateOfAquistion,
                              String cost, String user, String intallationDate, Building building, Location location, EquipmentSystem equipmentSystem, EquipmnetSubSystem subtype) {

        this.frequency = frequency;
        this.unit = unit;
        this.description = description;
        this.brandd = brandd;
        this.dateOfTagging = dateOfTagging;
        this.id = id;
        this.life = life;
        this.assetstatus = assetstatus;
        this.assetNo = assetNo;
        this.assettype = assettype;
        this.subLocation = subLocation;
        this.make = make;
        this.coolingCapacity = coolingCapacity;
        this.typeOfFcu = typeOfFcu;
        this.airFlow = airFlow;
        this.cuRef = cuRef;
        this.cuLocation = cuLocation;
        this.weightt = weightt;
        this.dimension = dimension;
        this.electricPowerSupply = electricPowerSupply;
        this.isolatorSize = isolatorSize;
        this.remarks = remarks;
        this.equipmentCode = equipmentCode;
        this.supplierName = supplierName;
        this.supplierAddress = supplierAddress;
        this.contactNo = contactNo;
        this.fcuRef = fcuRef;
        this.qty = qty;
        this.fanRef = fanRef;
        this.staticPressure = staticPressure;
        this.dia = dia;
        this.warrantyPeriod = warrantyPeriod;
        this.power = power;
        this.fullLoadCurrent = fullLoadCurrent;
        this.cpRef = cpRef;
        this.powerRequirement = powerRequirement;
        this.serialNumber = serialNumber;
        this.name = name;
        this.dateOfAquistion = dateOfAquistion;
        this.cost = cost;
        this.user = user;
        this.intallationDate = intallationDate;
        this.building = building;
        this.location = location;
        this.equipmentSystem = equipmentSystem;
        this.subtype = subtype;
    }

    //for create
    public CreateAssetRequest(int frequency, String unit, String description, String brandd, String assetstatus, double life, String assetNo, String assettype, String subLocation, String make, String coolingCapacity,
                              String typeOfFcu, String airFlow, String cuRef, String cuLocation, String weightt, String dimension,
                              String electricPowerSupply, String isolatorSize, String remarks, String equipmentCode, String supplierName,
                              String supplierAddress, String contactNo, String fcuRef, String qty, String fanRef, String staticPressure,
                              String dia, String warrantyPeriod, String power, String fullLoadCurrent, String cpRef,
                              String powerRequirement, String serialNumber, String name, String dateOfAquistion,
                              String cost, String user, String intallationDate,
                              Building building, Location location, EquipmentSystem equipmentSystem, EquipmnetSubSystem subtype) {

        this.frequency = frequency;
        this.unit = unit;
        this.description = description;
        this.brandd = brandd;
        this.assetstatus = assetstatus;
        this.life = life;
        this.assetNo = assetNo;
        this.assettype = assettype;
        this.subLocation = subLocation;
        this.make = make;
        this.coolingCapacity = coolingCapacity;
        this.typeOfFcu = typeOfFcu;
        this.airFlow = airFlow;
        this.cuRef = cuRef;
        this.cuLocation = cuLocation;
        this.weightt = weightt;
        this.dimension = dimension;
        this.electricPowerSupply = electricPowerSupply;
        this.isolatorSize = isolatorSize;
        this.remarks = remarks;
        this.equipmentCode = equipmentCode;
        this.supplierName = supplierName;
        this.supplierAddress = supplierAddress;
        this.contactNo = contactNo;
        this.fcuRef = fcuRef;
        this.qty = qty;
        this.fanRef = fanRef;
        this.staticPressure = staticPressure;
        this.dia = dia;
        this.warrantyPeriod = warrantyPeriod;
        this.power = power;
        this.fullLoadCurrent = fullLoadCurrent;
        this.cpRef = cpRef;
        this.powerRequirement = powerRequirement;
        this.serialNumber = serialNumber;
        this.name = name;
        this.dateOfAquistion = dateOfAquistion;
        this.cost = cost;
        this.user = user;
        this.intallationDate = intallationDate;
        this.building = building;
        this.location = location;
        this.equipmentSystem = equipmentSystem;
        this.subtype = subtype;
    }
}
