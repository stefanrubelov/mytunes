package com.easv.gringofy.bll;

import com.easv.gringofy.be.Song;
import com.easv.gringofy.dal.db.SongDAODB;
import com.easv.gringofy.exceptions.PlayerException;

import java.util.List;

public class SongManager {
    private SongDAODB songDAODB = new SongDAODB();


    public List<Song> getAllSongs() {
        return null;
    }

    public List<Song> getAllSongsByInput(String input) throws PlayerException {
        return songDAODB.getAllSongsByInput(input);
    }

}
