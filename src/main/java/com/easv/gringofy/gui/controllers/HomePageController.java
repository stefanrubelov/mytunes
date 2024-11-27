package com.easv.gringofy.gui.controllers;

import com.easv.gringofy.be.Album;
import com.easv.gringofy.be.Genre;
import com.easv.gringofy.be.Playlist;
import com.easv.gringofy.be.Song;
import com.easv.gringofy.gui.models.PlayerModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.ResourceBundle;

public class HomePageController implements Initializable {

    PlayerModel playerModel = new PlayerModel();

    @FXML
    private FlowPane flowPaneHomeAlbums;
    @FXML
    private FlowPane flowPaneHomeSongs;
    @FXML
    private HBox hboxHomePlaylists;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        testNodes();
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

    public void testNodes(){
        LocalDateTime now = LocalDateTime.now();
        Image image = new Image(getClass().getResourceAsStream("/com/easv/gringofy/images/logo.png"));
        Playlist playlist = new Playlist("Cool Songs", "Playlist with cool songs", now, now, image);
        hboxHomePlaylists.getChildren().addAll(playlist.toNode(), playlist.toNode(), playlist.toNode(), playlist.toNode(), playlist.toNode(), playlist.toNode(), playlist.toNode(), playlist.toNode());

        Song song = new Song("Cool song", "Some Artist");
        flowPaneHomeSongs.getChildren().addAll(song.toNode(), song.toNode(), song.toNode(), song.toNode(), song.toNode(), song.toNode(), song.toNode(), song.toNode(), song.toNode(), song.toNode(), song.toNode(), song.toNode(), song.toNode(), song.toNode());

        Album album = new Album("Cool Album");
        flowPaneHomeAlbums.getChildren().addAll(album.toNode(), album.toNode(), album.toNode(), album.toNode(), album.toNode(), album.toNode(), album.toNode(), album.toNode(), album.toNode());
    }
}
