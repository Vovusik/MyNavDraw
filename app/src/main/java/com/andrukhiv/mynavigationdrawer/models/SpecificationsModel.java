package com.andrukhiv.mynavigationdrawer.models;

import androidx.lifecycle.ViewModel;

import java.io.Serializable;

public class SpecificationsModel implements Serializable {

    private long id;
    private String name;
    private String sort;
    private int sort_int;
    private String description;
    private String photoSmall;
    private String photoLarge;
    private String link;
    private String term;
    private int frost;
    private String color;
    private String growth;
    private double weight;
    private int favorite;

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public SpecificationsModel(long id, String name, String sort, int sort_int, String description, String photoSmall, String photoLarge, String link, String term, int frost, String color, String growth, double weight, int favorite) {

        this.id = id;
        this.name = name;
        this.sort = sort;
        this.sort_int = sort_int;
        this.description = description;
        this.photoSmall = photoSmall;
        this.photoLarge = photoLarge;
        this.link = link;
        this.term = term;
        this.frost = frost;
        this.color = color;
        this.growth = growth;
        this.weight = weight;
        this.favorite = favorite;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public int getSort_int() {
        return sort_int;
    }

    public void setSort_int(int sort_int) {
        this.sort_int = sort_int;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhotoSmall() {
        return photoSmall;
    }

    public void setPhotoSmall(String photoSmall) {
        this.photoSmall = photoSmall;
    }

    public String getPhotoLarge() {
        return photoLarge;
    }

    public void setPhotoLarge(String photoLarge) {
        this.photoLarge = photoLarge;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public int getFrost() {
        return frost;
    }

    public void setFrost(int frost) {
        this.frost = frost;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getGrowth() {
        return growth;
    }

    public void setGrowth(String growth) {
        this.growth = growth;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public int getFavorite() {
        return favorite;
    }

    public void setFavorite(int favorite) {
        this.favorite = favorite;
    }

    @Override
    public String toString() {
        return "SpecificationsModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", sort=" + sort +
                ", sort_int=" + sort_int +
                ", description='" + description + '\'' +
                ", photoSmall='" + photoSmall + '\'' +
                ", photoLarge='" + photoLarge + '\'' +
                ", link='" + link + '\'' +
                ", term='" + term + '\'' +
                ", frost=" + frost +
                ", color='" + color + '\'' +
                ", growth='" + growth + '\'' +
                ", weight=" + weight +
                ", favorite=" + favorite +
                '}';
    }
}

