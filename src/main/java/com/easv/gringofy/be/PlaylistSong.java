package com.easv.gringofy.be;

public class PlaylistSong extends Song {
    private int playlistId;
    private Song song;
    public PlaylistSong(String title, String artist, Genre genre, int duration, int playlistId) {
        super(title, artist, genre, duration);
        this.playlistId = playlistId;
    }
    public PlaylistSong(int playlistId, Song song) {
        super();
        this.playlistId = playlistId;
        this.song = song;
    }
}
