package com.andrukhiv.mynavigationdrawer.models;

import java.io.Serializable;


public class PreparationDialogModel implements Serializable {


    private int id;
    private String shorter;
    private String longer;


    public PreparationDialogModel(int id, String shorter, String longer) {
        this.id = id;
        this.shorter = shorter;
        this.longer = longer;
    }


    public int getId() {
        return id;
    }


    public String getShorter() {
        return shorter;
    }


    public String getLonger() {
        return longer;
    }
}
