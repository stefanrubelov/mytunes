package com.easv.gringofy.gui.controllers;

import com.easv.gringofy.be.Album;
import com.easv.gringofy.be.Genre;
import com.easv.gringofy.be.Playlist;
import com.easv.gringofy.be.Song;
import com.easv.gringofy.bll.AlbumManager;
import com.easv.gringofy.bll.PlaylistManager;
import com.easv.gringofy.bll.SongManager;
import com.easv.gringofy.exceptions.PlayerException;
import com.easv.gringofy.gui.NodeBuilder;
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
    NodeBuilder nodeBuilder = new NodeBuilder();
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
        txtFieldSearchBar.textProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue.length() > newValue.length() && newValue.length() <= 3) {
                showDefaultSections();
            } else if (newValue.length() > 3) {
                try {
                    search(newValue);
                } catch (PlayerException e) {
                    throw new RuntimeException(e);
                }
            }
        });
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

    public void testNodes() {
        LocalDateTime now = LocalDateTime.now();
        Image image = new Image(getClass().getResourceAsStream("/com/easv/gringofy/images/logo.png"));
        Playlist playlist = new Playlist("Cool Songs", "Playlist with cool songs", now, now, image);
        hboxHomePlaylists.getChildren().addAll(nodeBuilder.playlistToNode(playlist),nodeBuilder.playlistToNode(playlist),nodeBuilder.playlistToNode(playlist),nodeBuilder.playlistToNode(playlist),nodeBuilder.playlistToNode(playlist),nodeBuilder.playlistToNode(playlist),nodeBuilder.playlistToNode(playlist),nodeBuilder.playlistToNode(playlist),nodeBuilder.playlistToNode(playlist),nodeBuilder.playlistToNode(playlist),nodeBuilder.playlistToNode(playlist),nodeBuilder.playlistToNode(playlist));

        Song song = new Song("Cool song", "Some Artist");
        flowPaneHomeSongs.getChildren().addAll(nodeBuilder.songToNode(song),nodeBuilder.songToNode(song),nodeBuilder.songToNode(song),nodeBuilder.songToNode(song),nodeBuilder.songToNode(song),nodeBuilder.songToNode(song),nodeBuilder.songToNode(song),nodeBuilder.songToNode(song),nodeBuilder.songToNode(song),nodeBuilder.songToNode(song),nodeBuilder.songToNode(song),nodeBuilder.songToNode(song),nodeBuilder.songToNode(song),nodeBuilder.songToNode(song),nodeBuilder.songToNode(song),nodeBuilder.songToNode(song),nodeBuilder.songToNode(song),nodeBuilder.songToNode(song),nodeBuilder.songToNode(song),nodeBuilder.songToNode(song));

        Album album = new Album("Cool Album");
        flowPaneHomeAlbums.getChildren().addAll(nodeBuilder.albumToNode(album),nodeBuilder.albumToNode(album),nodeBuilder.albumToNode(album),nodeBuilder.albumToNode(album),nodeBuilder.albumToNode(album),nodeBuilder.albumToNode(album),nodeBuilder.albumToNode(album),nodeBuilder.albumToNode(album),nodeBuilder.albumToNode(album),nodeBuilder.albumToNode(album),nodeBuilder.albumToNode(album),nodeBuilder.albumToNode(album),nodeBuilder.albumToNode(album),nodeBuilder.albumToNode(album));
    }

    private void search(String input) throws PlayerException {
        clearSections();
        getNewSections(input);
    }

    private void clearSections() {
        flowPaneHomeSongs.getChildren().clear();
        flowPaneHomeAlbums.getChildren().clear();
        hboxHomePlaylists.getChildren().clear();
    }

    private void getNewSections(String input) throws PlayerException {
        List<Song> songs = playerModel.getAllSongsByInput(input);
        List<Playlist> playlists = playerModel.getAllPlaylistsByInput(input);
        List<Album> albums = playerModel.getAllAlbumsByInput(input);
        songs.forEach(song -> flowPaneHomeSongs.getChildren().add(nodeBuilder.songToNode(song)));
        playlists.forEach(playlist -> flowPaneHomeSongs.getChildren().add(nodeBuilder.playlistToNode(playlist)));
        albums.forEach(album -> flowPaneHomeAlbums.getChildren().add(nodeBuilder.albumToNode(album)));
    }

    private void showDefaultSections() {
        flowPaneHomeSongs.getChildren().clear();
        flowPaneHomeAlbums.getChildren().clear();
        hboxHomePlaylists.getChildren().clear();
        testNodes();
    }
}
