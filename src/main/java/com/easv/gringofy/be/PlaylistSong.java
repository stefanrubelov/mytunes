package com.easv.gringofy.be;

import java.time.LocalDateTime;

public class PlaylistSong extends Song {
    private int playlistId;
    private Song song;

    public PlaylistSong(int id, int duration, Genre genre, String title, String description, Artist artist, String releaseDate, String filePath, LocalDateTime createdAt, LocalDateTime updatedAt) {
        super(id, duration, genre, title, artist, releaseDate, filePath, createdAt, updatedAt);
        this.playlistId = playlistId;
    }

    public PlaylistSong(int playlistId, Song song) {
        super(song.getId(), song.getDuration(), song.getGenre(), song.getTitle(), song.getArtist(), song.getReleaseDate(), song.getFilePath(), song.getCreatedAt(), song.getUpdatedAt());
        this.playlistId = playlistId;
        this.song = song;
    }
}
