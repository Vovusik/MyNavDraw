package com.andrukhiv.mynavigationdrawer.models;


import java.io.Serializable;

public class KitchenModel implements Serializable {
    private long id;
    private final String name;
    private final String imageGlassIcon;
    private final String imageBackground;
    private String description;
    private String components;
    private String recipe;


    public KitchenModel(long id, String name, String imageGlassIcon, String imageBackground, String description, String components, String recipe) {
        this.id = id;
        this.name = name;
        this.imageGlassIcon = imageGlassIcon;
        this.imageBackground = imageBackground;
       // this.colorPlaceholder = colorPlaceholder;
        this.description = description;
        this.components = components;
        this.recipe = recipe;
    }

    public String getName() {
        return name;
    }

    public String getImageGlassIcon() {
        return imageGlassIcon;
    }

    public String getImageBackground() {
        return imageBackground;
    }



    public String getDescription() {
        return description;
    }

    public String getComponents() {
        return components;
    }

    public String getRecipe() {
        return recipe;
    }
}
