package com.easv.gringofy.be;

import javafx.scene.Node;

import java.time.LocalDateTime;

public class Song {
    private int id;
    private int duration;
    private Genre genre;
    private String title;
    private Artist artist;
    private String releaseDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Song(int id, int duration, Genre genre, String title, Artist artist, String releaseDate, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.genre = genre;
        this.duration = duration;
        this.releaseDate = releaseDate;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public int getId(){ return id; }

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
}
