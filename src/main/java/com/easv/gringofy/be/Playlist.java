package com.easv.gringofy.be;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Playlist {
    private Map<Integer, Song> songs = new LinkedHashMap<>();
    private String title;
    private String description;
    private String createdAt;
    private String updatedAt;

    public Playlist(String title, String description, String createdAt, String updatedAt) {
        this.title = title;
        this.description = description;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
    public void addSong(int position, Song song) {
        songs.put(position, song);
    }
}
