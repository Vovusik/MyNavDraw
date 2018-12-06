package com.andrukhiv.mynavigationdrawer.models;

import java.io.Serializable;

public class FormationModel implements Serializable {

    private long id;
    private String name;
    private String photo_1;
    private String photo_2;
    private String photo_3;
    private String photo_4;
    private String photo_5;
    private String photo_6;
    private String description_1;
    private String description_2;
    private String description_3;
    private String description_5;
    private String description_6;
    private String description_7;


    public FormationModel(long id, String name, String photo_1, String photo_2, String photo_3, String photo_4, String photo_5, String photo_6, String description_1, String description_2, String description_3, String description_5, String description_6, String description_7) {
        this.id = id;
        this.name = name;
        this.photo_1 = photo_1;
        this.photo_2 = photo_2;
        this.photo_3 = photo_3;
        this.photo_4 = photo_4;
        this.photo_5 = photo_5;
        this.photo_6 = photo_6;
        this.description_1 = description_1;
        this.description_2 = description_2;
        this.description_3 = description_3;
        this.description_5 = description_5;
        this.description_6 = description_6;
        this.description_7 = description_7;
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

    public String getPhoto_1() {
        return photo_1;
    }

    public void setPhoto_1(String photo_1) {
        this.photo_1 = photo_1;
    }

    public String getPhoto_2() {
        return photo_2;
    }

    public void setPhoto_2(String photo_2) {
        this.photo_2 = photo_2;
    }

    public String getPhoto_3() {
        return photo_3;
    }

    public void setPhoto_3(String photo_3) {
        this.photo_3 = photo_3;
    }

    public String getPhoto_4() {
        return photo_4;
    }

    public void setPhoto_4(String photo_4) {
        this.photo_4 = photo_4;
    }

    public String getPhoto_5() {
        return photo_5;
    }

    public void setPhoto_5(String photo_5) {
        this.photo_5 = photo_5;
    }

    public String getPhoto_6() {
        return photo_6;
    }

    public void setPhoto_6(String photo_6) {
        this.photo_6 = photo_6;
    }

    public String getDescription_1() {
        return description_1;
    }

    public void setDescription_1(String description_1) {
        this.description_1 = description_1;
    }

    public String getDescription_2() {
        return description_2;
    }

    public void setDescription_2(String description_2) {
        this.description_2 = description_2;
    }

    public String getDescription_3() {
        return description_3;
    }

    public void setDescription_3(String description_3) {
        this.description_3 = description_3;
    }

    public String getDescription_5() {
        return description_5;
    }

    public void setDescription_5(String description_5) {
        this.description_5 = description_5;
    }

    public String getDescription_6() {
        return description_6;
    }

    public void setDescription_6(String description_6) {
        this.description_6 = description_6;
    }

    public String getDescription_7() {
        return description_7;
    }

    public void setDescription_7(String description_7) {
        this.description_7 = description_7;
    }


    @Override
    public String toString() {
        return "FormationModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", photo_1='" + photo_1 + '\'' +
                ", photo_2='" + photo_2 + '\'' +
                ", photo_3='" + photo_3 + '\'' +
                ", photo_4='" + photo_4 + '\'' +
                ", photo_5='" + photo_5 + '\'' +
                ", photo_6='" + photo_6 + '\'' +
                ", description_1='" + description_1 + '\'' +
                ", description_2='" + description_2 + '\'' +
                ", description_3='" + description_3 + '\'' +
                ", description_5='" + description_5 + '\'' +
                ", description_6='" + description_6 + '\'' +
                ", description_7='" + description_7 + '\'' +
                '}';
    }
}