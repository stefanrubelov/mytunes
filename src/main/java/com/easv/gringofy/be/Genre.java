package com.easv.gringofy.be;

import java.time.LocalDateTime;

public class Genre {
    private int id;
    private String title;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Genre(int id, String title, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.title = title;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Genre(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public String getTitle(){
        return title;
    }

    public int getId(){
        return id;
    }
}
