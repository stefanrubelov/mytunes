package com.easv.gringofy.bll;

import com.easv.gringofy.be.Song;
import com.easv.gringofy.dal.db.SongDAODB;
import com.easv.gringofy.exceptions.PlayerException;

import java.sql.SQLException;
import java.util.List;

public class SongManager {
    private final SongDAODB songDAODB = new SongDAODB();

    public List<Song> getAllSongsByPlaylist(int playlist_id) throws SQLException {
        return songDAODB.getAllSongsByPlaylist(playlist_id);
    }
    public List<Song> getAllSongsByArtist(int artist_id) throws SQLException {
        return songDAODB.getAllSongsByArtist(artist_id);
    }
    public List<Song> getAllSongs() throws PlayerException, SQLException {
        return songDAODB.getAllSongs();
    }

    public List<Song> getAllSongsByInput(String input) throws PlayerException, SQLException {
        return songDAODB.getAllSongsByInput(input);
    }

    public void insert(Song song) throws PlayerException, SQLException {
        songDAODB.insert(song);
    }

    public void delete(int id) throws PlayerException {
        songDAODB.delete(id);
    }

    public void update(Song song) throws PlayerException {
        songDAODB.update(song);
    }
    public int getCurrentId() throws SQLException {
        return songDAODB.getCurrentId();
    }

    public List<Song> getAllSongsByAlbum(int id) throws SQLException {
        return songDAODB.getAllSongsByAlbum(id);
    }
}
