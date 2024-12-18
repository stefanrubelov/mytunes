package com.easv.gringofy.be;

import java.io.InputStream;
import java.time.LocalDateTime;

public class Genre {
    private int id;
    private String title;
    private String path;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Genre(int id, String title, String path, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.title = title;
        this.path = path;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Genre(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public Genre(String name, String path, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.title = name;
        this.path = path;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Genre(String name, String path) {
        this.title = name;
        this.path = path;
    }

    public String getTitle() {
        return title;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return title;
    }

    public String getPath() {
        return path;
    }

    public String getName() {
        return title;
    }
}

