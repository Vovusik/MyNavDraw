package com.andrukhiv.mynavigationdrawer.models;


import java.io.Serializable;

public class MapsModel implements Serializable {

    private long id;
    private String region;
    private int region_int;
    private String title;
    private String description;
    private String address;
    private String text_web;
    private String text_phone;
    private Double lat;
    private Double lng;
    private String navigation_position;
    private String image;

    public MapsModel(long id, String region, int region_int, String title, String description, String address, String text_web, String text_phone, Double lat, Double lng, String navigation_position, String image) {
        this.id = id;
        this.region = region;
        this.region_int = region_int;
        this.title = title;
        this.description = description;
        this.address = address;
        this.text_web = text_web;
        this.text_phone = text_phone;
        this.lat = lat;
        this.lng = lng;
        this.navigation_position = navigation_position;
        this.image = image;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public int getRegion_int() {
        return region_int;
    }

    public void setRegion_int(int region_int) {
        this.region_int = region_int;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getText_web() {
        return text_web;
    }

    public void setText_web(String text_web) {
        this.text_web = text_web;
    }

    public String getText_phone() {
        return text_phone;
    }

    public void setText_phone(String text_phone) {
        this.text_phone = text_phone;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public String getNavigation_position() {
        return navigation_position;
    }

    public void setNavigation_position(String navigation_position) {
        this.navigation_position = navigation_position;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}

