package com.easv.gringofy.gui.models;

import com.easv.gringofy.be.Album;
import com.easv.gringofy.be.Playlist;
import com.easv.gringofy.be.PlaylistSong;
import com.easv.gringofy.be.Song;
import com.easv.gringofy.bll.AlbumManager;
import com.easv.gringofy.bll.PlaylistManager;
import com.easv.gringofy.bll.SongManager;
import com.easv.gringofy.bll.SongPlayer;
import com.easv.gringofy.exceptions.PlayerException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PlayerModel {

    private final SongManager songManager = new SongManager();
    private final PlaylistManager playlistManager = new PlaylistManager();
    private final AlbumManager albumManager = new AlbumManager();
    private final SongPlayer songPlayer = new SongPlayer();

    private static List<Song> defaultSongs = new ArrayList<>();
    private static List<Playlist> defaultPlaylists = new ArrayList<>();
    private static List<Album> defaultAlbums = new ArrayList<>();

    @FXML
    public void playPreviousSong(ActionEvent actionEvent) {

    }

    @FXML
    public void playCurrentSong(ActionEvent actionEvent) {

    }

    @FXML
    public void playNextSong(ActionEvent actionEvent) {

    }
    public void loadDefaultSongs() throws PlayerException, SQLException {
        defaultSongs = songManager.getAllSongs();
    }
    public void loadDefaultPlaylists() throws PlayerException, SQLException {
        defaultPlaylists = playlistManager.getAllPlaylists();
    }
    public void loadDefaultAlbums() throws PlayerException, SQLException {
        defaultAlbums = albumManager.getAllAlbums();
    }
    public List<Song> getDefaultSongs() {
        return defaultSongs;
    }
    public List<Playlist> getDefaultPlaylists() {
        return defaultPlaylists;
    }
    public List<Album> getDefaultAlbums() {
        return defaultAlbums;
    }
    public List<Song> getAllSongsByInput(String input) throws PlayerException, SQLException {
        return songManager.getAllSongsByInput(input);
    }

    public List<Playlist> getAllPlaylistsByInput(String input) throws PlayerException, SQLException {
        return playlistManager.getAllPlaylistsByInput(input);
    }

    public List<Album> getAllAlbumsByInput(String input) throws PlayerException {
        return albumManager.getAllAlbumsByInput(input);
    }
    public void addSongToQueue(Song song){
        songPlayer.addSongToQueue(song);
    }

}
