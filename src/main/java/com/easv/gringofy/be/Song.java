package com.easv.gringofy.be;

import java.time.LocalDateTime;

public class Song {
    private int id;
    private int duration;
    private Genre genre;
    private String title;
    private String description;
    private String artist;
    private String releaseDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Song(int id, int duration, Genre genre, String title,String description, String artist,String releaseDate, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.artist = artist;
        this.genre = genre;
        this.duration = duration;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
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

    protected String getDescription() {
        return description;
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
}
