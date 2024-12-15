package com.easv.gringofy.bll;

import com.easv.gringofy.be.Artist;
import com.easv.gringofy.be.Song;
import com.easv.gringofy.dal.db.ArtistDAODB;
import com.easv.gringofy.exceptions.PlayerException;

import java.sql.SQLException;
import java.util.List;

public class ArtistManager {

    private final ArtistDAODB artistDAODB = new ArtistDAODB();

    public List<Artist> getAllArtists() throws SQLException {
        return artistDAODB.getAll();
    }
    public void deleteArtist(Artist artist) throws SQLException, PlayerException {
        artistDAODB.delete(artist.getId());
    }
    public void addSong(Artist artist, int song_id) throws SQLException {
        artistDAODB.addSong(artist, song_id);
    }
}
