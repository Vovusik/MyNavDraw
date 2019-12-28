package com.andrukhiv.mynavigationdrawer.models;

import java.io.Serializable;

public class LaboratoryModel implements Serializable {


    private long id;
    private String name;
    private String instruction;


    public LaboratoryModel(long id, String name, String instruction) {
        this.id = id;
        this.name = name;
        this.instruction = instruction;
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


    public String getInstruction() {
        return instruction;
    }


    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }
}
