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
import java.util.Objects;

public class Song {
    private static final String DEFAULT_SONG_PICTURE = "/com/easv/gringofy/images/defaultSongPicture.png";
    private static final String OPTIONS_PICTURE = "/com/easv/gringofy/images/tripleDots.png";

    private int id;
    private int duration;
    private Genre genre;
    private String title;
    private String artist;
    private String releaseDate;
    private String filePath;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Song(int id, int duration, Genre genre, String title, String artist, String releaseDate, String filePath, LocalDateTime createdAt, LocalDateTime updatedAt) {
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

    public Song(String title, String artist) {
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

    public String getTitle() {
        return title;
    }

    public String getArtist() {
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

    public void playSong() {
        if (filePath != null) {

        }
    }

    public String getFilePath() {
        return filePath;
    }
}
