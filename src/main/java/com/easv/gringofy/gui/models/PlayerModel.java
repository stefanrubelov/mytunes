package com.easv.gringofy.gui.models;

import com.easv.gringofy.be.Album;
import com.easv.gringofy.be.Playlist;
import com.easv.gringofy.be.Song;
import com.easv.gringofy.bll.AlbumManager;
import com.easv.gringofy.bll.PlaylistManager;
import com.easv.gringofy.bll.SongManager;
import com.easv.gringofy.exceptions.PlayerException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.sql.SQLException;
import java.util.List;

public class PlayerModel {

    private SongManager songManager = new SongManager();
    private PlaylistManager playlistManager = new PlaylistManager();
    private AlbumManager albumManager = new AlbumManager();

    @FXML
    public void playPreviousSong(ActionEvent actionEvent) {

    }

    @FXML
    public void playCurrentSong(ActionEvent actionEvent) {

    }

    @FXML
    public void playNextSong(ActionEvent actionEvent) {

    }

    public List<Song> getSongs() {
        return songManager.getAllSongs();
    }

    public List<Song> getAllSongsByInput(String input) throws PlayerException, SQLException {
        return songManager.getAllSongsByInput(input);
    }

    public List<Playlist> getAllPlaylistsByInput(String input) throws PlayerException {
        return playlistManager.getAllPlaylistsByInput(input);
    }

    public List<Album> getAllAlbumsByInput(String input) throws PlayerException {
        return albumManager.getAllAlbumsByInput(input);
    }

    public List<Playlist> getPlaylists() {
        return playlistManager.getAllPlaylists();
    }
}
