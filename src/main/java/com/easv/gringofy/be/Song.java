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
    private Artist artist;
    private String releaseDate;
    private String filePath;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Song(int id, int duration, Genre genre, String title, Artist artist, String releaseDate, String filePath, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.filePath = filePath;
        this.genre = genre;
        this.duration = duration;
        this.releaseDate = releaseDate;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Song(String title, Artist artist) {
        this.title = title;
        this.artist = artist;
    }


    public int getDuration() {
        return duration;
    }

    public Genre getGenre() {
        return genre;
    }

    public String getTitle() {
        return title;
    }

    public Artist getArtist() {
        return artist;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void playSong() {
        if (filePath != null) {

        }
    }

    public String getFilePath() {
        return filePath;
    }

    public int getId() {
        return id;
    }
}
