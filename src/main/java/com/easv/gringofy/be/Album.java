package com.easv.gringofy.be;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.image.*;

import java.util.HashMap;
import java.util.Map;

public class Album {
    private int id;
    private String title;
    private String description;
    private String createdAt;
    private String updatedAt;
    private Map<Integer, Song> songs;

    public Album(int id, String title, String description, String createdAt, String updatedAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.songs = new HashMap<>();
    }

    public Album(String title) {
        this.title = title;
    }

    public Album(int id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public void addSong(int position, Song song) {
        this.songs.put(position, song);
    }
    public String getTitle(){
        return title;
    }
}
