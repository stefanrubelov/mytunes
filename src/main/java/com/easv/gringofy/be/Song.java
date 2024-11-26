package com.easv.gringofy.be;

public class Song {
    private int id;
    private String title;
    private String description;
    private String artist;
    private Genre genre;
    private String releaseDate;
    private String createdAt;
    private String updatedAt;
    private int duration;

    public Song(String title, String artist, Genre genre, int duration) {
        this.title = title;
        this.artist = artist;
        this.genre = genre;
        this.duration = duration;
    }

    public Song() {

    }
}
