package com.easv.gringofy.be;

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

    public Playlist(String title, String description, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.title = title;
        this.description = description;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
    public void addSong(int position, Song song) {
        songs.put(position, song);
    }
    public void updateSong(LocalDateTime date) {
        this.updatedAt = date;
    }
}
