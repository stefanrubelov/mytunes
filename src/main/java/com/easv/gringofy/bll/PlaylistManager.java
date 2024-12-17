package com.easv.gringofy.bll;

import com.easv.gringofy.be.Playlist;
import com.easv.gringofy.be.PlaylistSong;
import com.easv.gringofy.be.Song;
import com.easv.gringofy.dal.db.PlaylistDAODB;
import com.easv.gringofy.dal.db.SongDAODB;
import com.easv.gringofy.exceptions.PlayerException;

import java.sql.SQLException;
import java.util.List;

public class PlaylistManager {
    private final PlaylistDAODB playlistDAODB = new PlaylistDAODB();

    public List<Playlist> getAllPlaylists() throws SQLException {
        return playlistDAODB.getAllPlaylists();
    }

    public List<Playlist> getAllPlaylistsByInput(String input) throws PlayerException, SQLException {
        return playlistDAODB.getAllPlaylistsByInput(input);
    }

    public void insert(Playlist playlist) throws PlayerException, SQLException {
        playlistDAODB.insert(playlist);
    }

    public void update(Playlist playlist) throws PlayerException, SQLException {
        playlistDAODB.update(playlist);
    }

    public void delete(Playlist playlist) throws PlayerException, SQLException {
        playlistDAODB.delete(playlist);
    }

    public void addSong(Playlist playlist, Song song) throws PlayerException, SQLException {
        playlistDAODB.addSong(playlist, song);
    }

    public void removePlaylistSong(PlaylistSong playlistSong) throws PlayerException, SQLException {
        playlistDAODB.removeSong(playlistSong);
    }
    public void incrementPosition(PlaylistSong playlistSong) {
        playlistDAODB.incrementPosition(playlistSong);
    }
    public void decrementPosition(PlaylistSong playlistSong){
        playlistDAODB.decrementPosition(playlistSong);
    }
}
