package com.easv.gringofy.bll;

import com.easv.gringofy.be.Album;
import com.easv.gringofy.be.AlbumSong;
import com.easv.gringofy.be.PlaylistSong;
import com.easv.gringofy.be.Song;
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

    public void incrementPosition(AlbumSong albumSong) {
        albumDAODB.incrementPosition(albumSong);
    }

    public void decrementPosition(AlbumSong albumSong) {
        albumDAODB.decrementPosition(albumSong);
    }

    public void addSong(Album album, int songId) throws SQLException {
        albumDAODB.addSong(album, songId);
    }
    public void removeSong(AlbumSong albumSong) {
        albumDAODB.removeSong(albumSong);
    }
    public void deleteAlbum(Album album) {
        albumDAODB.delete(album);
    }

    public void insert(Album album) throws PlayerException {
        albumDAODB.insert(album);
    }

    public void update(Album album) {
        albumDAODB.update(album);
    }

}
