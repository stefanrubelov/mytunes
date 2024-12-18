package com.easv.gringofy.gui.controllers;

import com.easv.gringofy.be.Playlist;
import com.easv.gringofy.bll.PlaylistManager;
import com.easv.gringofy.gui.MusicPlayer;
import com.easv.gringofy.gui.NodeBuilder;
import com.easv.gringofy.gui.controllers.creators.PlaylistCreatorController;
import com.easv.gringofy.gui.interfaces.ICollectionPage;
import com.easv.gringofy.gui.models.PlayerModel;
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

public class PlaylistsPageController extends MusicPlayer implements Initializable, ICollectionPage {

    private final NodeBuilder nodeBuilder = new NodeBuilder();
    private PlaylistManager playlistManager = new PlaylistManager();
    private final PlayerModel playerModel = new PlayerModel();
    private List<Playlist> playlists;

    @FXML
    private FlowPane flowPanePlaylistsContainer;

    public void showPlaylistForm() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/easv/gringofy/views/playlist-creator.fxml"));
        Parent root = loader.load();
        PlaylistCreatorController controller = loader.getController();
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
        super.initialize(location, resources);
        try {
            get();
            set();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void fetch() throws SQLException {
        playerModel.loadDefaultPlaylists();
    }

    @Override
    public void get() throws SQLException {
        playlists = playerModel.getDefaultPlaylists();
    }
    @Override
    public void set() {
        playlists.forEach(playlist -> flowPanePlaylistsContainer.getChildren().add(nodeBuilder.playlistToNode(playlist)));
    }

    @Override
    public void clear() {
        flowPanePlaylistsContainer.getChildren().clear();
    }

    public void refresh() throws SQLException {
        clear();
        fetch();
        get();
        set();
    }
    public void setPlaylistManager(PlaylistManager playlistManager) {
        this.playlistManager = playlistManager;
    }
}
