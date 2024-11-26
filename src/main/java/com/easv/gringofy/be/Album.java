package com.easv.gringofy.be;

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
    public void addSong(int position, Song song) {
        this.songs.put(position, song);
    }
}
