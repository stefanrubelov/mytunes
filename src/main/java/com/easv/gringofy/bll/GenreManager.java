package com.easv.gringofy.bll;

import com.easv.gringofy.be.Genre;
import com.easv.gringofy.dal.db.GenreDAODB;
import com.easv.gringofy.exceptions.PlayerException;

import java.sql.SQLException;
import java.util.List;

public class GenreManager {
    private final GenreDAODB genreDAODB = new GenreDAODB();

    public List<Genre> getAllGenres() throws SQLException, PlayerException {
        return genreDAODB.getAllGenres();
    }
}
