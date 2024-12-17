package com.easv.gringofy.gui.controllers;

import com.easv.gringofy.be.*;
import com.easv.gringofy.exceptions.PlayerException;
import com.easv.gringofy.gui.MusicPlayer;
import com.easv.gringofy.gui.NodeBuilder;
import com.easv.gringofy.gui.controllers.creators.SongCreatorController;
import com.easv.gringofy.gui.models.PlayerModel;
import com.easv.gringofy.utils.Debounce;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class HomePageController extends MusicPlayer implements Initializable {

    private final PlayerModel playerModel = new PlayerModel();
    private final NodeBuilder nodeBuilder = new NodeBuilder();

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
            super.initialize(location, resources);
            showDefaultNodes();
        } catch (PlayerException | SQLException e) {
            throw new RuntimeException(e);
        }

        Debounce debouncer = new Debounce(200);

        txtFieldSearchBar.textProperty().addListener((_, oldValue, newValue) -> debouncer.debounce(() -> Platform.runLater(() -> {
            try {
                if (newValue.length() >= 3) {
                    fetchSearch(newValue);
                } else if (oldValue.length() > newValue.length() && oldValue.length() >= 3) {
                    showDefaultNodes();
                }
            } catch (PlayerException | SQLException e) {
                throw new RuntimeException(e);
            }
        })));
    }

    public void showDefaultNodes() throws PlayerException, SQLException {
        clearSections();
        playerModel.getDefaultSongs().forEach(song -> flowPaneHomeSongs.getChildren().add(nodeBuilder.songToNode(song, buttonSwitchState)));
        playerModel.getDefaultPlaylists().forEach(playlist -> hboxHomePlaylists.getChildren().add(nodeBuilder.playlistToNode(playlist)));
        playerModel.getDefaultAlbums().forEach(album -> flowPaneHomeAlbums.getChildren().add(nodeBuilder.albumToNode(album, this)));
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
                albums.forEach(album -> flowPaneHomeAlbums.getChildren().add(nodeBuilder.albumToNode(album, this)));
                playlists.forEach(playlist -> hboxHomePlaylists.getChildren().add(nodeBuilder.playlistToNode(playlist)));
            });
        } catch (PlayerException | SQLException e) {
            e.printStackTrace();
            // Handle exceptions appropriately
        }
    }

    @FXML
    private void showSongCreatorWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/easv/gringofy/views/song-creator.fxml"));
        Parent root = loader.load();
        SongCreatorController controller = loader.getController();
        controller.setController(this);
        controller.setEditMode(false);
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Song Creator");
        stage.setResizable(false);
        stage.show();
    }
}
