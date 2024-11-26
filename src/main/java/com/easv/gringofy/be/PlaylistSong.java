package com.easv.gringofy.be;

import java.time.LocalDateTime;

public class PlaylistSong extends Song {
    private int playlistId;
    private Song song;
    public PlaylistSong(int id, int duration, Genre genre, String title,String description, String artist, LocalDateTime createdAt, LocalDateTime updatedAt) {
        super(id, duration, genre, title, description, artist, createdAt, updatedAt);
        this.playlistId = playlistId;
    }
    public PlaylistSong(int playlistId, Song song) {
        super();
        this.playlistId = playlistId;
        this.song = song;
    }
}
