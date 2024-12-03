package com.easv.gringofy.gui.controllers;

import com.easv.gringofy.be.Playlist;
import com.easv.gringofy.gui.MusicPlayer;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class PlaylistController extends MusicPlayer {

    private Playlist playlist;

    @FXML private Label lblPlaylistName;
    @FXML private Label lblPlaylistDescription;


    public void setPlaylist(Playlist playlist) {
        this.playlist = playlist;
        setPlaylistInformation();
    }
    public void setPlaylistInformation() {
        lblPlaylistName.setText(playlist.getTitle());
        lblPlaylistDescription.setText(playlist.getDescription());
    }
}
