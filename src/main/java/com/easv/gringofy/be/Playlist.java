package com.easv.gringofy.be;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.time.LocalDateTime;
import java.util.*;

public class Playlist {

    private static final String DEFAULT_PLAYLIST_PICTURE = "/com/easv/gringofy/images/logo.png";

    private Map<Integer, Song> songs = new LinkedHashMap<>();
    private int id;
    private String title;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Image image;

    public Playlist(String title, String description, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.title = title;
        this.description = description;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Playlist(String title, String description, LocalDateTime createdAt, LocalDateTime updatedAt, Image image) {
        this.title = title;
        this.description = description;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.image = image;
    }

    public Playlist(int id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public void addSong(int position, Song song) {
        songs.put(position, song);
    }

    public void updateSong(LocalDateTime date) {
        this.updatedAt = date;
    }

    public Image getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public int getId(){
        return id;
    }

}
