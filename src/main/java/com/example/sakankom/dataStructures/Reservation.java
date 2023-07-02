package com.example.sakankom.dataStructures;

import java.util.Date;

public class Reservation {
    private int house_id;
    private int tenant_id;
    private int price;
    private String payyingDate;
    private String reservationDate;
    private  String isValid;

    //setters


    public void setHouse_id(int house_id) {
        this.house_id = house_id;
    }

    public void setTenant_id(int tenant_id) {
        this.tenant_id = tenant_id;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setPayyingDate(String payyingDate) {
        this.payyingDate = payyingDate;
    }

    public void setReservationDate(String reservationDate) {
        this.reservationDate = reservationDate;
    }

    public void setIsValid(String isValid) {
        this.isValid = isValid;
    }

    //getters


    public int getHouse_id() {
        return house_id;
    }

    public int getTenant_id() {
        return tenant_id;
    }

    public int getPrice() {
        return price;
    }

    public String getPayyingDate() {
        return payyingDate;
    }

    public String getReservationDate() {
        return reservationDate;
    }

    public String getIsValid() {
        return isValid;
    }
}
