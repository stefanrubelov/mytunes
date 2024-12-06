package com.easv.gringofy.bll;

import com.easv.gringofy.be.Playlist;
import com.easv.gringofy.be.Song;
import com.easv.gringofy.dal.db.SongDAODB;
import com.easv.gringofy.exceptions.PlayerException;

import java.sql.SQLException;
import java.util.List;

public class SongManager {
    private SongDAODB songDAODB = new SongDAODB();

    public List<Song> getAllSongsByPlaylist(int playlist_id) throws SQLException {
        return songDAODB.getAllSongsByPlaylist(playlist_id);
    }


    public List<Song> getAllSongs() throws PlayerException, SQLException {
        return songDAODB.getAllSongs();
    }

    public List<Song> getAllSongsByInput(String input) throws PlayerException, SQLException {
        return songDAODB.getAllSongsByInput(input);
    }

    public Song getSongById(int id) throws PlayerException, SQLException {
        return songDAODB.get(id);
    }

    public void insert(Song song) throws PlayerException, SQLException {
        songDAODB.insert(song);
    }
}
