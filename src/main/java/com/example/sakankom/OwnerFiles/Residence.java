package com.example.sakankom.OwnerFiles;

public class Residence {
    private String residenceID;
    private String ownerID;
    private String location;
    private String residenceName;

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    private String ownerName;

    public Residence(String residenceID, String ownerID, String location, String residenceName, String ownerName) {
        this.residenceID = residenceID;
        this.ownerID = ownerID;
        this.location = location;
        this.residenceName = residenceName;
        this.ownerName = ownerName;
    }

    public String getResidenceID() {
        return residenceID;
    }

    public void setResidenceID(String residenceID) {
        this.residenceID = residenceID;
    }

    public String getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(String ownerID) {
        this.ownerID = ownerID;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getResidenceName() {
        return residenceName;
    }

    public void setResidenceName(String residenceName) {
        this.residenceName = residenceName;
    }
}
