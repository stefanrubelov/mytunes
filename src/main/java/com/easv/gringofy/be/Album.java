package com.easv.gringofy.be;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.image.*;
import javafx.scene.layout.VBox;
import javafx.scene.shape.*;

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

    public void addSong(int position, Song song) {
        this.songs.put(position, song);
    }

    public HBox toNode() {
        HBox hbox = new HBox();
        hbox.getStyleClass().add("album-node");
        hbox.setAlignment(Pos.CENTER_LEFT);
        Image image = new Image(getClass().getResourceAsStream("/com/easv/gringofy/images/defaultAlbumPicture.png"));
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(50);
        imageView.setFitHeight(50);
        Label titleLabel = new Label(title);
        titleLabel.getStyleClass().add("album-node-title");
        hbox.getChildren().addAll(imageView, titleLabel);
        return hbox;
    }
}
