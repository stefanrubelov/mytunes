package com.easv.gringofy.bll;

import com.easv.gringofy.be.Album;
import com.easv.gringofy.dal.db.AlbumDAODB;
import com.easv.gringofy.exceptions.PlayerException;

import java.sql.SQLException;
import java.util.List;

public class AlbumManager {
    private final AlbumDAODB albumDAODB = new AlbumDAODB();

    public List<Album> getAllAlbumsByInput(String input) {
        try {
            return albumDAODB.getAllAlbumsByInput(input);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Album> getAllAlbums() throws PlayerException, SQLException {
        return albumDAODB.getAllAlbums();
    }

    public Album getAlbumById(int id) {
//        return albumDAODB.get(id);
        return null;
    }
}
