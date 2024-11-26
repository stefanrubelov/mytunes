package com.easv.gringofy.gui.models;

import com.easv.gringofy.be.Album;
import com.easv.gringofy.be.Playlist;
import com.easv.gringofy.be.Song;
import com.easv.gringofy.bll.AlbumManager;
import com.easv.gringofy.bll.PlaylistManager;
import com.easv.gringofy.bll.SongManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

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
    public List<Song> getSongs(){
        return songManager.getAllSongs();
    }
    public List<Playlist> getPlaylists(){
        return playlistManager.getAllPlaylists();
    }
    public List<Album> getAlbums(){
        return albumManager.getAllAlbums();
    }
}
