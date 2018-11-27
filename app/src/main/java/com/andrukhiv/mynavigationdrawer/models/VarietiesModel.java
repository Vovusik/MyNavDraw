package com.andrukhiv.mynavigationdrawer.models;

import android.arch.lifecycle.ViewModel;

import com.andrukhiv.mynavigationdrawer.R;

import java.io.Serializable;

public class VarietiesModel implements Serializable {


    public VarietiesModel(long id,
                          String name,
                          String description,
                          String photoSmall,
                          String photoLarge,
                          String link,
                          int favorite,
                          int sorty) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.photoSmall = photoSmall;
        this.photoLarge = photoLarge;
        this.link = link;
        this.favorite = favorite;
        this.sorty = sorty;
    }

    private long id;
    private String name;
    private String description;
    private String photoSmall;
    private String photoLarge;
    private String link;
    private int favorite;
    private int sorty;

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

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getFavorite() {
        return favorite;
    }

    public void setFavorite(int favorite) {
        this.favorite = favorite;
    }

    public int getSorty() {
        return sorty;
    }

    public void setSorty(int sorty) {
        this.sorty = sorty;
    }


    @Override
    public String toString() {
        return "VarietiesTable{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", photoSmall='" + photoSmall + '\'' +
                ", photoLarge='" + photoLarge + '\'' +
                ", link=" + link +
                ", favorite=" + favorite +
                ", sorty=" + sorty +
                '}';
    }
}
