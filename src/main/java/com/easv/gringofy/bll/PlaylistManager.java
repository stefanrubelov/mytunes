package com.easv.gringofy.bll;

import com.easv.gringofy.be.Playlist;
import com.easv.gringofy.be.Song;
import com.easv.gringofy.dal.db.PlaylistDAODB;
import com.easv.gringofy.dal.db.SongDAODB;
import com.easv.gringofy.exceptions.PlayerException;

import java.util.List;

public class PlaylistManager {
    PlaylistDAODB playlistDAODB = new PlaylistDAODB();

    public List<Playlist> getAllPlaylists() {
        return null;
    }
    public List<Playlist> getAllPlaylistsByInput(String input) throws PlayerException {
        return playlistDAODB.getAllPlaylistsByInput(input);
    }
}
