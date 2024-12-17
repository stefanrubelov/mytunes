package com.easv.gringofy.be;

public class ArtistSong {
    private int id;
    private int artistId;
    private int songId;
    private int position;

    public ArtistSong(int id) {
        this.id = id;
    }

    public ArtistSong(int id, int artistId, int songId, int position) {
        this.id = id;
        this.artistId = artistId;
        this.songId = songId;
        this.position = position;
    }


    public int getId() {
        return id;
    }

}
