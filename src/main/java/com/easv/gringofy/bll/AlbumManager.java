package com.easv.gringofy.bll;

import com.easv.gringofy.be.Album;
import com.easv.gringofy.dal.db.AlbumDAODB;

import java.util.List;

public class AlbumManager {
    private AlbumDAODB albumDAODB = new AlbumDAODB();

    public List<Album> getAllAlbumsByInput(String input) {
        return albumDAODB.getAllAlbumsByInput(input);
    }

    public Album getAlbumById(int id) {
//        return albumDAODB.get(id);
        return null;
    }
}
