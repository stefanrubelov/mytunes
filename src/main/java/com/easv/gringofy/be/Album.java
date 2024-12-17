package com.easv.gringofy.be;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.image.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Album {
    private int id;
    private String title;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Map<Integer, Song> songs;
    private String releaseDate;
    private Artist artist;

    public Album(int id, String title, String description, LocalDateTime createdAt, LocalDateTime updatedAt) {
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

    public Album(int id, String title, String description, Artist artist) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.artist = artist;
    }

    public Album(int id, String title, String description, String releaseDate, Artist artist) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.releaseDate = releaseDate;
        this.artist = artist;
    }

    public Album(String name, String description, String releaseDate, Artist artist, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.title = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.artist = artist;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }



    public void addSong(int position, Song song) {
        this.songs.put(position, song);
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return title;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public Artist getArtist() {
        return artist;
    }
}
