package com.easv.gringofy.be;

public class AlbumSong {
    private int id;
    private int albumId;
    private int songId;
    private int position;

    public AlbumSong(int id, int albumId, int songId) {
        this.id = id;
        this.albumId = albumId;
        this.songId = songId;
    }

    public AlbumSong(int id, int albumId, int songId, int position) {
        this.id = id;
        this.albumId = albumId;
        this.songId = songId;
        this.position = position;
    }

    public AlbumSong(int id) {
        this.id = id;
    }

    public AlbumSong(int albumId, int songId) {
        this.albumId = albumId;
        this.songId = songId;
    }

    public int getId() {
        return id;
    }

    public int getAlbumId() {
        return albumId;
    }

    public int getSongId() {
        return songId;
    }

    public int getPosition() {
        return position;
    }
}
