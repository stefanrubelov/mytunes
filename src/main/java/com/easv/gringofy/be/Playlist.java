package com.easv.gringofy.be;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Playlist {
    private Map<Integer, Song> songs = new LinkedHashMap<>();
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
    public void addSong(int position, Song song) {
        songs.put(position, song);
    }
    public void updateSong(LocalDateTime date) {
        this.updatedAt = date;
    }

    public VBox toNode() {
        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        vbox.getStyleClass().add("playlist-node");
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(100);
        imageView.setFitHeight(100);
        Label titleLabel = new Label(title);
        vbox.getChildren().addAll(imageView, titleLabel);
        return vbox;
    }
}
