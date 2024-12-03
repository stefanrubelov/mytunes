package com.easv.gringofy.gui.controllers;

import com.easv.gringofy.be.*;
import com.easv.gringofy.bll.AlbumManager;
import com.easv.gringofy.bll.PlaylistManager;
import com.easv.gringofy.bll.SongManager;
import com.easv.gringofy.exceptions.PlayerException;
import com.easv.gringofy.gui.MusicPlayer;
import com.easv.gringofy.gui.NodeBuilder;
import com.easv.gringofy.gui.models.PlayerModel;
import com.easv.gringofy.utils.Debounce;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class HomePageController extends MusicPlayer implements Initializable {

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
            if (oldValue.length() > newValue.length() && newValue.length() <= 3 && oldValue.length() >= 3) {
                try {
                    showDefaultSections();
                } catch (PlayerException | SQLException e) {
                    throw new RuntimeException(e);
                }
            } else if (newValue.length() >= 3) {
                try {
                    search(newValue);
                } catch (PlayerException | SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        try {
            startNodes();
        } catch (PlayerException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void startNodes() throws PlayerException, SQLException {
        LocalDateTime now = LocalDateTime.now();
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/easv/gringofy/images/logo.png")));
        Playlist playlist = new Playlist("Cool Songs", "Playlist with cool songs", now, now, image);
        hboxHomePlaylists.getChildren().addAll(nodeBuilder.playlistToNode(playlist), nodeBuilder.playlistToNode(playlist), nodeBuilder.playlistToNode(playlist), nodeBuilder.playlistToNode(playlist), nodeBuilder.playlistToNode(playlist), nodeBuilder.playlistToNode(playlist), nodeBuilder.playlistToNode(playlist), nodeBuilder.playlistToNode(playlist), nodeBuilder.playlistToNode(playlist), nodeBuilder.playlistToNode(playlist), nodeBuilder.playlistToNode(playlist), nodeBuilder.playlistToNode(playlist));
//        List<Song> songs = songManager.getAllSongs();
//        songs.forEach(song -> {flowPaneHomeSongs.getChildren().add(nodeBuilder.songToNode(song));});
        Song song = songManager.getSongById(3);
        flowPaneHomeSongs.getChildren().addAll(nodeBuilder.songToNode(song), nodeBuilder.songToNode(song), nodeBuilder.songToNode(song), nodeBuilder.songToNode(song), nodeBuilder.songToNode(song), nodeBuilder.songToNode(song), nodeBuilder.songToNode(song), nodeBuilder.songToNode(song), nodeBuilder.songToNode(song), nodeBuilder.songToNode(song), nodeBuilder.songToNode(song), nodeBuilder.songToNode(song), nodeBuilder.songToNode(song), nodeBuilder.songToNode(song), nodeBuilder.songToNode(song), nodeBuilder.songToNode(song), nodeBuilder.songToNode(song), nodeBuilder.songToNode(song), nodeBuilder.songToNode(song), nodeBuilder.songToNode(song));

//        Album album = albumManager.getAlbumById(1);
        Album album = new Album("Cool Album");
        flowPaneHomeAlbums.getChildren().addAll(nodeBuilder.albumToNode(album), nodeBuilder.albumToNode(album), nodeBuilder.albumToNode(album), nodeBuilder.albumToNode(album), nodeBuilder.albumToNode(album), nodeBuilder.albumToNode(album), nodeBuilder.albumToNode(album), nodeBuilder.albumToNode(album), nodeBuilder.albumToNode(album), nodeBuilder.albumToNode(album), nodeBuilder.albumToNode(album), nodeBuilder.albumToNode(album), nodeBuilder.albumToNode(album), nodeBuilder.albumToNode(album));
    }

    private void search(String input) throws PlayerException, SQLException {
        clearSections();

        Debounce debouncer = new Debounce(200);
        debouncer.debounce(() -> {
            try {
                fetchSearch(input);
            } catch (PlayerException | SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void clearSections() {
        flowPaneHomeSongs.getChildren().clear();
        flowPaneHomeAlbums.getChildren().clear();
        hboxHomePlaylists.getChildren().clear();
    }

    private void fetchSearch(String input) throws PlayerException, SQLException {

        try {
            List<Song> songs = playerModel.getAllSongsByInput(input);
            List<Album> albums = playerModel.getAllAlbumsByInput(input);
            List<Playlist> playlists = playerModel.getAllPlaylistsByInput(input);

            Platform.runLater(() -> {
                flowPaneHomeSongs.getChildren().clear();

                songs.forEach(song -> flowPaneHomeSongs.getChildren().add(nodeBuilder.songToNode(song)));
                albums.forEach(album -> flowPaneHomeAlbums.getChildren().add(nodeBuilder.albumToNode(album)));
                playlists.forEach(playlist -> flowPaneHomeSongs.getChildren().add(nodeBuilder.playlistToNode(playlist)));
            });
        } catch (PlayerException | SQLException e) {
            e.printStackTrace();
            // Handle exceptions appropriately
        }
    }

    private void showDefaultSections() throws PlayerException, SQLException {
        flowPaneHomeSongs.getChildren().clear();
        flowPaneHomeAlbums.getChildren().clear();
        hboxHomePlaylists.getChildren().clear();
        startNodes();
    }
}
