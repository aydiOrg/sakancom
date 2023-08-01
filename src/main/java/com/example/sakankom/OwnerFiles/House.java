package com.example.sakankom.OwnerFiles;

public class House {
    public House(String name, String imageSrc, int price, String res, int floor) {
        this.name = name;
        this.imageSrc = imageSrc;
        this.price = price;
        this.res = res;
        this.floor = floor;
    }
    public House(String name, String imageSrc, int price, String res) {
        this.name = name;
        this.imageSrc = imageSrc;
        this.price = price;
        this.res = res;
    }
    public House(String houseID, int floor){
        this.houseID = houseID;
        this.floor = floor;
    }
    public House() {
    }

    private String name;
    public String houseID;
    public String getID(){
        return name.split(" ")[1];
    }
    private String imageSrc;
    private int price;

    public String getResName() {
        return resName;
    }

    public void setResName(String resName) {
        this.resName = resName;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    private String res;
    private String resName;
    private int floor;


    public String getRes() {
        return res;
    }

    public void setRes(String res) {
        this.res = res;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageSrc() {
        return imageSrc;
    }

    public void setImageSrc(String imageSrc) {
        this.imageSrc = imageSrc;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }


}