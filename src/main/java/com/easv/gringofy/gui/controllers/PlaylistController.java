package com.easv.gringofy.gui.controllers;

import com.easv.gringofy.be.Playlist;
import com.easv.gringofy.gui.MusicPlayer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import javax.naming.Context;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PlaylistController extends MusicPlayer implements Initializable {

    private Playlist playlist;

    @FXML private Label lblPlaylistName;
    @FXML private Label lblPlaylistDescription;
    @FXML private Button btnPlaylistOptions;
    @FXML private ContextMenu contextMenu;
    @FXML private HBox hboxToolsBar;

    public void setPlaylist(Playlist playlist) {
        this.playlist = playlist;
        setPlaylistInformation();
    }
    public void setPlaylistInformation() {
        lblPlaylistName.setText(playlist.getTitle());
        lblPlaylistDescription.setText(playlist.getDescription());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnPlaylistOptions.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                contextMenu.show(btnPlaylistOptions, event.getScreenX(), event.getScreenY());
            }
        });
    }
    @FXML private void delete(){

    }
    @FXML private void edit() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/easv/gringofy/views/playlist-creator.fxml"));
        Parent root = loader.load();
        PlaylistCreatorController controller = (PlaylistCreatorController) loader.getController();
        controller.setEditMode(true);
        controller.setPlaylist(playlist);
        controller.setPlaylistController(this);
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Playlist Editor");
        stage.setResizable(false);
        stage.show();
    }
    public void refresh(){

    }
}
