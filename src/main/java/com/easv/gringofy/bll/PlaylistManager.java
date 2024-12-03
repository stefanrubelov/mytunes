package com.easv.gringofy.bll;

import com.easv.gringofy.be.Playlist;
import com.easv.gringofy.be.Song;
import com.easv.gringofy.dal.db.PlaylistDAODB;
import com.easv.gringofy.dal.db.SongDAODB;
import com.easv.gringofy.exceptions.PlayerException;

import java.sql.SQLException;
import java.util.List;

public class PlaylistManager {
    private PlaylistDAODB playlistDAODB = new PlaylistDAODB();

    public List<Playlist> getAllPlaylists() throws SQLException {
        return playlistDAODB.getAllPlaylists();
    }

    public List<Playlist> getAllPlaylistsByInput(String input) throws PlayerException, SQLException {
        return playlistDAODB.getAllPlaylistsByInput(input);
    }
    public void insert(Playlist playlist) throws PlayerException, SQLException {
        playlistDAODB.insert(playlist);
    }
}
