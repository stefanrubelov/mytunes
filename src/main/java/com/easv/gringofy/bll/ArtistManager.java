package com.easv.gringofy.bll;

import com.easv.gringofy.be.Artist;
import com.easv.gringofy.dal.db.ArtistDAODB;

import java.sql.SQLException;
import java.util.List;

public class ArtistManager {

    private final ArtistDAODB artistDAODB = new ArtistDAODB();

    public List<Artist> getAllArtists() throws SQLException {
        return artistDAODB.getAll();
    }
}
