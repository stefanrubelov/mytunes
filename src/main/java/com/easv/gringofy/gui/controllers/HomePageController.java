package com.easv.gringofy.gui.controllers;

import com.easv.gringofy.be.Album;
import com.easv.gringofy.be.Playlist;
import com.easv.gringofy.be.Song;
import com.easv.gringofy.gui.models.PlayerModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;

public class HomePageController implements Initializable {

    PlayerModel playerModel = new PlayerModel();

    @FXML private ListView<Album> lstViewHomeAlbums;
    @FXML private ListView<Song> lstViewHomeSongs;
    @FXML private ListView<Playlist> lstViewHomePlaylists;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void playPreviousSong(ActionEvent actionEvent) {
        playerModel.playPreviousSong(actionEvent);
    }

    public void playCurrentSong(ActionEvent actionEvent) {
        playerModel.playCurrentSong(actionEvent);
    }

    public void playNextSong(ActionEvent actionEvent) {
        playerModel.playNextSong(actionEvent);
    }
}
