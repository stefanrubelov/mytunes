package com.easv.gringofy.bll;

import com.easv.gringofy.be.Album;
import com.easv.gringofy.dal.db.AlbumDAODB;

import java.util.List;

public class AlbumManager {
    AlbumDAODB albumDAODB = new AlbumDAODB();

    public List<Album> getAllAlbumsByInput(String input) {
        return albumDAODB.getAllAlbumsByInput(input);
    }
}
