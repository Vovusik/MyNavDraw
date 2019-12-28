package com.andrukhiv.mynavigationdrawer.models;


public class BugModel {

    private String imageId;
    private String description;
    private String category;
    private int category_id;

    public BugModel(String imageId, String description, String category, int category_id) {
        this.imageId = imageId;
        this.description = description;
        this.category = category;
        this.category_id = category_id;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }





//    static ArrayList<BugModel> getBugMildew() {
//
//        ArrayList<BugModel> places = new ArrayList<>();
//
//        places.add(new BugModel("http://media.globalchampionstour.com/cache/750x429/assets/monaco_2016.jpg", "Монако", "В Столице суверенного княжества Монако живет больше миллионеров, чем настройщиков роялей", 1));
//        places.add(new BugModel("http://www.pragueczechtravel.com/images/prague_banner.jpg", "Прага", "Культурная столица восточной европы - город, который хорош в любое время года", 1));
//
//
//        return places;
//    }







}
