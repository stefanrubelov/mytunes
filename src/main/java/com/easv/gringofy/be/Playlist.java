package com.easv.gringofy.be;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Playlist {
    private Map<Integer, Song> songs = new LinkedHashMap<>();
    private String title;

    public Playlist(String title) {
        this.title = title;
    }
    public void addSong(int position, Song song) {
        songs.put(position, song);
    }
}
