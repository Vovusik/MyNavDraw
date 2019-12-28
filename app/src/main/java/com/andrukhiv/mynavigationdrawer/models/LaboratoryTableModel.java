package com.andrukhiv.mynavigationdrawer.models;

public class LaboratoryTableModel {

    private int id;
    private String specific_gravity_juice;
    private String sugar_content_juice;
    private String fortress_future_wine;


    public LaboratoryTableModel(int id, String specific_gravity_juice, String sugar_content_juice, String fortress_future_wine) {

        this.id = id;
        this.specific_gravity_juice = specific_gravity_juice;
        this.sugar_content_juice = sugar_content_juice;
        this.fortress_future_wine = fortress_future_wine;
    }

    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }


    public String getSpecific_gravity_juice() {
        return specific_gravity_juice;
    }


    public void setSpecific_gravity_juice(String specific_gravity_juice) {
        this.specific_gravity_juice = specific_gravity_juice;
    }


    public String getSugar_content_juice() {
        return sugar_content_juice;
    }


    public void setSugar_content_juice(String sugar_content_juice) {
        this.sugar_content_juice = sugar_content_juice;
    }


    public String getFortress_future_wine() {
        return fortress_future_wine;
    }


    public void setFortress_future_wine(String fortress_future_wine) {
        this.fortress_future_wine = fortress_future_wine;
    }
}
