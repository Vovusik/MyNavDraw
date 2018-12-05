package com.andrukhiv.mynavigationdrawer.models;

import java.io.Serializable;

public class ReproductionModel implements Serializable {

    public ReproductionModel(long id, String name, String description) {

        this.id = id;
        this.name = name;
        this.description = description;

    }

    private long id;

    private String description;

    private String name;

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
}