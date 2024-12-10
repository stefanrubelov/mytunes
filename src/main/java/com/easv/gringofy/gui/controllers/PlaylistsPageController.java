package com.easv.gringofy.gui.controllers;

import com.easv.gringofy.be.Playlist;
import com.easv.gringofy.bll.PlaylistManager;
import com.easv.gringofy.gui.MusicPlayer;
import com.easv.gringofy.gui.NodeBuilder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;

public class PlaylistsPageController extends MusicPlayer implements Initializable {

    NodeBuilder nodeBuilder = new NodeBuilder();
    PlaylistManager playlistManager = new PlaylistManager();

    List<Playlist> playlists;

    @FXML
    FlowPane flowPanePlaylistsContainer;

    public void showPlaylistForm(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/easv/gringofy/views/playlist-creator.fxml"));
        Parent root = loader.load();
        PlaylistCreatorController controller = (PlaylistCreatorController) loader.getController();
        controller.setPlaylistsPageController(this);
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Playlist Creator");
        stage.setResizable(false);
        stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            fetchPlaylists();
            setPlaylists();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void fetchPlaylists() throws SQLException {
        playlists = playlistManager.getAllPlaylists();
    }

    private void setPlaylists() {
        playlists.forEach(playlist -> {
            flowPanePlaylistsContainer.getChildren().add(nodeBuilder.playlistToNode(playlist));
        });
    }

    private void clearPlaylists() {
        flowPanePlaylistsContainer.getChildren().clear();
    }

    public void refreshPlaylists() throws SQLException {
        clearPlaylists();
        fetchPlaylists();
        setPlaylists();
    }
}
