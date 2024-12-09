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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class HomePageController extends MusicPlayer implements Initializable {

    List<Song> defaultSongs = new ArrayList<>();
    List<Playlist> defaultPlaylists = new ArrayList<>();
    List<Album> defaultAlbums = new ArrayList<>();

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
        try {
            playerModel.loadDefaultSongs();
            playerModel.loadDefaultPlaylists();
            playerModel.loadDefaultAlbums();
            showDefaultNodes();
        } catch (PlayerException | SQLException e) {
            throw new RuntimeException(e);
        }

        Debounce debouncer = new Debounce(200);

        txtFieldSearchBar.textProperty().addListener((observable, oldValue, newValue) -> {
            debouncer.debounce(() -> Platform.runLater(() -> {
                try {
                    if (newValue.length() >= 3) {
                        fetchSearch(newValue);
                    } else if (oldValue.length() > newValue.length() && oldValue.length() >= 3) {
                        showDefaultNodes();
                    }
                } catch (PlayerException | SQLException e) {
                    throw new RuntimeException(e);
                }
            }));
        });
    }

    public void showDefaultNodes() throws PlayerException, SQLException {
        clearSections();
        playerModel.getDefaultSongs().forEach(song -> {flowPaneHomeSongs.getChildren().add(nodeBuilder.songToNode(song, buttonSwitchState));});
        playerModel.getDefaultPlaylists().forEach(playlist -> {hboxHomePlaylists.getChildren().add(nodeBuilder.playlistToNode(playlist));});
        playerModel.getDefaultAlbums().forEach(album -> {flowPaneHomeAlbums.getChildren().add(nodeBuilder.albumToNode(album));});
    }

    private void search(String input) throws PlayerException, SQLException {

        Debounce debouncer = new Debounce(100);
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
                clearSections();
                songs.forEach(song -> flowPaneHomeSongs.getChildren().add(nodeBuilder.songToNode(song, buttonSwitchState)));
                albums.forEach(album -> flowPaneHomeAlbums.getChildren().add(nodeBuilder.albumToNode(album)));
                playlists.forEach(playlist -> hboxHomePlaylists.getChildren().add(nodeBuilder.playlistToNode(playlist)));
            });
        } catch (PlayerException | SQLException e) {
            e.printStackTrace();
            // Handle exceptions appropriately
        }
    }

    @FXML
    private void showSongCreatorWindow(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/easv/gringofy/views/song-creator.fxml"));
        Parent root = loader.load();
//        PlaylistCreatorController controller = (PlaylistCreatorController) loader.getController();
//        controller.setPlaylistsPageController(this);
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Song Creator");
        stage.setResizable(false);
        stage.show();
    }
}
