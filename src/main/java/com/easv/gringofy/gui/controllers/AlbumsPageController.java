package com.easv.gringofy.gui.controllers;

import com.easv.gringofy.be.Album;
import com.easv.gringofy.bll.AlbumManager;
import com.easv.gringofy.exceptions.PlayerException;
import com.easv.gringofy.gui.MusicPlayer;
import com.easv.gringofy.gui.NodeBuilder;
import com.easv.gringofy.bll.SongQueue;
import com.easv.gringofy.gui.controllers.creators.AlbumCreatorController;
import com.easv.gringofy.gui.controllers.creators.SongCreatorController;
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

public class AlbumsPageController extends MusicPlayer implements ICollectionPage {

    private NodeBuilder nodeBuilder = new NodeBuilder();
    private AlbumManager albumManager = new AlbumManager();
    private PlayerModel playerModel = new PlayerModel();
    private List<Album> albums;

    @FXML
    private FlowPane flowPaneAlbumsContainer;
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
        playerModel.loadDefaultAlbums();
    }

    @Override
    public void get() throws SQLException {
        albums = playerModel.getDefaultAlbums();
    }

    @Override
    public void set() {
        albums.forEach(album -> {
            flowPaneAlbumsContainer.getChildren().add(nodeBuilder.albumToNode(album, this));
        });
    }

    @Override
    public void clear() {
        flowPaneAlbumsContainer.getChildren().clear();
    }

    @Override
    public void refresh() throws SQLException, PlayerException {
        clear();
        fetch();
        get();
        set();
    }
    public void showAlbumForm(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/easv/gringofy/views/album-creator.fxml"));
        Parent root;
        try {
            root = loader.load();
            AlbumCreatorController controller = loader.getController();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            controller.setEditMode(false);
            controller.setAlbumsPageController(this);
            stage.setTitle("Album creator");
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
