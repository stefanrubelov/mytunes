package com.easv.gringofy.be;

import java.time.LocalDateTime;

public class PlaylistSong extends Song {
    private int playlistId;
    private Song song;

<<<<<<< HEAD
    public PlaylistSong(int id, int duration, Genre genre, String title, Artist artist, String releaseDate, LocalDateTime createdAt, LocalDateTime updatedAt) {
        super(id, duration, genre, title, artist, releaseDate, createdAt, updatedAt);
=======
    public PlaylistSong(int id, int duration, Genre genre, String title, String description, String artist, String releaseDate, String filePath, LocalDateTime createdAt, LocalDateTime updatedAt) {
        super(id, duration, genre, title, artist, releaseDate, filePath, createdAt, updatedAt);
>>>>>>> 80a522dd65b437747a6a3b90fe04afd09f1accf5
        this.playlistId = playlistId;
    }

    public PlaylistSong(int playlistId, Song song) {
<<<<<<< HEAD
        super(song.getId(), song.getDuration(), song.getGenre(), song.getTitle(), song.getArtist(), song.getReleaseDate(), song.getCreatedAt(), song.getUpdatedAt());
=======
        super(song.getId(), song.getDuration(), song.getGenre(), song.getTitle(), song.getArtist(), song.getReleaseDate(), song.getFilePath(), song.getCreatedAt(), song.getUpdatedAt());
>>>>>>> 80a522dd65b437747a6a3b90fe04afd09f1accf5
        this.playlistId = playlistId;
        this.song = song;
    }
}
