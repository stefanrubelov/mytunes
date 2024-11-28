package com.easv.gringofy.gui.controllers;

import com.easv.gringofy.be.Album;
import com.easv.gringofy.be.Genre;
import com.easv.gringofy.be.Playlist;
import com.easv.gringofy.be.Song;
import com.easv.gringofy.bll.AlbumManager;
import com.easv.gringofy.bll.PlaylistManager;
import com.easv.gringofy.bll.SongManager;
import com.easv.gringofy.exceptions.PlayerException;
import com.easv.gringofy.gui.models.PlayerModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class HomePageController implements Initializable {

    PlayerModel playerModel = new PlayerModel();
    SongManager songManager = new SongManager();
    PlaylistManager playlistManager = new PlaylistManager();
    AlbumManager albumManager = new AlbumManager();

    @FXML
    private FlowPane flowPaneHomeAlbums;
    @FXML
    private FlowPane flowPaneHomeSongs;
    @FXML
    private HBox hboxHomePlaylists;
    @FXML
    private TextField txtFieldSearchBar;

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

    @FXML private void search(ActionEvent actionEvent) throws PlayerException {
        String input = txtFieldSearchBar.getText();
        if (input.length() > 2) {
            clearSections();
            getNewSections(input);
        }
        else{
            showDefaultSections();
        }
    }

    private void clearSections(){
        flowPaneHomeSongs.getChildren().clear();
        flowPaneHomeAlbums.getChildren().clear();
        hboxHomePlaylists.getChildren().clear();
    }
    private void getNewSections(String input) throws PlayerException {
        List<Song> songs = playerModel.getAllSongsByInput(input);
        List<Playlist> playlists = playerModel.getAllPlaylistsByInput(input);
        List<Album> albums = playerModel.getAllAlbumsByInput(input);
        songs.forEach(song -> flowPaneHomeSongs.getChildren().add(song.toNode()));
        playlists.forEach(playlist -> flowPaneHomeSongs.getChildren().add(playlist.toNode()));
        albums.forEach(album -> flowPaneHomeAlbums.getChildren().add(album.toNode()));
    }
    private void showDefaultSections(){
        flowPaneHomeSongs.getChildren().clear();
        flowPaneHomeAlbums.getChildren().clear();
        hboxHomePlaylists.getChildren().clear();
    }
}
