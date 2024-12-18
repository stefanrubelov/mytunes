package com.easv.gringofy.bll;

import com.easv.gringofy.be.Genre;
import com.easv.gringofy.be.GenreSong;
import com.easv.gringofy.dal.db.GenreDAODB;
import com.easv.gringofy.exceptions.PlayerException;

import java.sql.SQLException;
import java.util.List;

public class GenreManager {
    private final GenreDAODB genreDAODB = new GenreDAODB();

    public List<Genre> getAllGenres() throws SQLException, PlayerException {
        return genreDAODB.getAllGenres();
    }

    public void insert(Genre genre) throws PlayerException {
        genreDAODB.insert(genre);
    }

    public void update(Genre genre) throws PlayerException {
        genreDAODB.update(genre);
    }

    public void delete(Genre genre) {
        genreDAODB.delete(genre);
    }
    public void addSong(Genre genre, int songId) throws PlayerException, SQLException {
        genreDAODB.addSong(genre,songId);
    }

    public void incrementPosition(GenreSong genreSong) {
        genreDAODB.incrementPosition(genreSong);
    }
    public void decrementPosition(GenreSong genreSong) {
        genreDAODB.decrementPosition(genreSong);
    }
}
