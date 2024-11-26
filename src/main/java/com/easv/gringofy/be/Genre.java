package com.easv.gringofy.be;

public class Genre {
    private int id;
    private String title;
    private String createdAt;
    private String updatedAt;

    public Genre(int id, String title, String createdAt, String updatedAt) {
        this.id = id;
        this.title = title;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
