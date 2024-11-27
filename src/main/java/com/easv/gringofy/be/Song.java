package com.easv.gringofy.be;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

import java.time.LocalDateTime;

public class Song {
    private int id;
    private int duration;
    private Genre genre;
    private String title;
    private String artist;
    private String releaseDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Song(int id, int duration, Genre genre, String title, String artist, String releaseDate, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.genre = genre;
        this.duration = duration;
        this.releaseDate = releaseDate;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
    public Song( String title, String artist) {
        this.title = title;
        this.artist = artist;
    }


    protected int getId() {
        return id;
    }

    protected int getDuration() {
        return duration;
    }

    protected Genre getGenre() {
        return genre;
    }

    protected String getTitle() {
        return title;
    }

    protected String getArtist() {
        return artist;
    }

    protected String getReleaseDate() {
        return releaseDate;
    }

    protected LocalDateTime getCreatedAt() {
        return createdAt;
    }

    protected LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public HBox toNode() {
        HBox hbox = new HBox();
        hbox.getStyleClass().add("song-node");
        hbox.setAlignment(Pos.CENTER);

        Image image = new Image(getClass().getResourceAsStream("/com/easv/gringofy/images/defaultSongPicture.png"));
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(35);
        imageView.setFitHeight(35);

        // Clip the ImageView to a rounded rectangle, so that image is not square (cannot use border-radius or background radius)
        Rectangle clip = new Rectangle(35, 35);
        clip.setArcWidth(10);
        clip.setArcHeight(10);
        imageView.setClip(clip);

        // VBox for text (title and artist name)
        VBox vbox = new VBox();
        vbox.getStyleClass().add("song-node-text");
        Label titleLabel = new Label(title);
        titleLabel.getStyleClass().add("song-node-title");
        Label artistLabel = new Label(artist);
        artistLabel.getStyleClass().add("song-node-artist");
        vbox.getChildren().addAll(titleLabel, artistLabel);

        hbox.getChildren().addAll(imageView, vbox);
        return hbox;
    }

}
