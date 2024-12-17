package com.easv.gringofy.be;

public class PlaylistSong {
    private int id;
    private int playlistId;
    private int songId;
    private int position;

    public PlaylistSong(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getPlaylistId() {
        return playlistId;
    }

    public int getSongId() {
        return songId;
    }

    public int getPosition() {
        return position;
    }
}
