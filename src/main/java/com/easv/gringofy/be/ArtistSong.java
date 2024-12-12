package com.easv.gringofy.be;

public class ArtistSong {
    private int id;
    private int artistId;
    private int songId;
    private int position;

    public ArtistSong(int id, int artistId, int songId) {
        this.id = id;
        this.artistId = artistId;
        this.songId = songId;
    }

    public ArtistSong(int id) {
        this.id = id;
    }

    public ArtistSong(int id, int artistId, int songId, int position) {
        this.id = id;
        this.artistId = artistId;
        this.songId = songId;
        this.position = position;
    }

    public ArtistSong(int artistId, int songId) {
        this.artistId = artistId;
        this.songId = songId;
    }

    public int getId() {
        return id;
    }

    public int getArtistId() {
        return artistId;
    }

    public int getSongId() {
        return songId;
    }

    public int getPosition() {
        return position;
    }
}
