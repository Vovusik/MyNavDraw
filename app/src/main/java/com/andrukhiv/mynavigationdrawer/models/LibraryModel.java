package com.andrukhiv.mynavigationdrawer.models;

import java.io.Serializable;

public class LibraryModel implements Serializable {

    private long id;
    private String image;
    private String title;
    private String author;
    private String link;

    public LibraryModel(long id, String image, String title, String author, String link) {
        this.id = id;
        this.image = image;
        this.title = title;
        this.author = author;
        this.link = link;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public String toString() {
        return "LibraryModel{" +
                "id=" + id +
                ", image='" + image + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", link='" + link + '\'' +
                '}';
    }
}
