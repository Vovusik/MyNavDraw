package com.andrukhiv.mynavigationdrawer.models;


import java.io.Serializable;

public class PreparatyModel implements Serializable {

    private String nazva_preparatu;
    private String diucha_rechovina;
    private String virobnyk;
    private String spektr_dii;
    private String kratnist_zastosuvannya;
    private String stroky_do_spogivannya;

    public final String type;
    public final String[] data;

    public PreparatyModel(String type) {
        this.type = type;
        data = null;
    }

    public PreparatyModel(String nazva_preparatu,
                          String diucha_rechovina,
                          String virobnyk,
                          String spektr_dii,
                          String kratnist_zastosuvannya,
                          String stroky_do_spogivannya) {
        this.type = null;
        data = new String[] {
                nazva_preparatu,
                diucha_rechovina,
                virobnyk,
                spektr_dii,
                kratnist_zastosuvannya,
                stroky_do_spogivannya};
    }

    public PreparatyModel(String type,
                          String name,
                          String company,
                          String version,
                          String api,
                          String storage,
                          String inches,
                          String ram) {
        this.type = type;
        data = new String[] {
                name,
                company,
                version,
                api,
                storage,
                inches,
                ram };
    }

    public String getNazva_preparatu() {
        return nazva_preparatu;
    }

    public void setNazva_preparatu(String nazva_preparatu) {
        this.nazva_preparatu = nazva_preparatu;
    }

    public String getDiucha_rechovina() {
        return diucha_rechovina;
    }

    public void setDiucha_rechovina(String diucha_rechovina) {
        this.diucha_rechovina = diucha_rechovina;
    }

    public String getVirobnyk() {
        return virobnyk;
    }

    public void setVirobnyk(String virobnyk) {
        this.virobnyk = virobnyk;
    }

    public String getSpektr_dii() {
        return spektr_dii;
    }

    public void setSpektr_dii(String spektr_dii) {
        this.spektr_dii = spektr_dii;
    }

    public String getKratnist_zastosuvannya() {
        return kratnist_zastosuvannya;
    }

    public void setKratnist_zastosuvannya(String kratnist_zastosuvannya) {
        this.kratnist_zastosuvannya = kratnist_zastosuvannya;
    }

    public String getStroky_do_spogivannya() {
        return stroky_do_spogivannya;
    }

    public void setStroky_do_spogivannya(String stroky_do_spogivannya) {
        this.stroky_do_spogivannya = stroky_do_spogivannya;
    }

    public boolean isSection() {
        return data == null;
    }
}