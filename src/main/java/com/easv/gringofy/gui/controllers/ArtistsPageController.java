package com.easv.gringofy.gui.controllers;

import com.easv.gringofy.be.Artist;
import com.easv.gringofy.bll.PlaylistManager;
import com.easv.gringofy.exceptions.PlayerException;
import com.easv.gringofy.gui.MusicPlayer;
import com.easv.gringofy.gui.NodeBuilder;
import com.easv.gringofy.gui.SongQueue;
import com.easv.gringofy.gui.interfaces.ICollectionPage;
import com.easv.gringofy.gui.models.PlayerModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class ArtistsPageController extends MusicPlayer  implements ICollectionPage {

    private NodeBuilder nodeBuilder = new NodeBuilder();
    private PlaylistManager playlistManager = new PlaylistManager();
    private PlayerModel playerModel = new PlayerModel();
    private List<Artist> artists;

    @FXML
    private FlowPane flowPanePlaylistsContainer;

    @FXML
    private void showArtistForm(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/easv/gringofy/views/artist-creator.fxml"));
        Parent root = loader.load();
        ArtistCreatorController controller = (ArtistCreatorController) loader.getController();
        controller.setArtistsPageController(this);
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Artist Creator");
        stage.setResizable(false);
        stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        progressBar.progressProperty().bind(SongQueue.getProgressProperty());
        try {
            get();
            set();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void fetch() throws SQLException, PlayerException {
        playerModel.loadDefaultArtists();
    }

    @Override
    public void get() throws SQLException {
        artists = playerModel.getDefaultArtists();
    }
    @Override
    public void set() {
        artists.forEach(artist -> {
            flowPanePlaylistsContainer.getChildren().add(nodeBuilder.artistToNode(artist));
        });
    }

    @Override
    public void clear() {
        flowPanePlaylistsContainer.getChildren().clear();
    }

    @Override
    public void refresh() throws SQLException, PlayerException {
        clear();
        fetch();
        get();
        set();
    }
}
