package com.easv.gringofy.gui.controllers;

import com.easv.gringofy.be.Playlist;
import com.easv.gringofy.bll.PlaylistManager;
import com.easv.gringofy.dal.db.QueryBuilder;
import com.easv.gringofy.exceptions.PlayerException;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class PlaylistCreatorController {

    private PlaylistManager playlistManager = new PlaylistManager();
    private PlaylistsPageController playlistsPageController;
    private PlaylistController playlistController;
    private Playlist playlist;

    private boolean editMode = false;

    @FXML
    private TextField txtFieldPlaylistName;
    @FXML
    private TextArea txtAreaPlaylistDescription;


    @FXML
    private void createPlaylist() throws PlayerException, SQLException {
        String name = txtFieldPlaylistName.getText();
        String description = txtAreaPlaylistDescription.getText();
        LocalDateTime now = LocalDateTime.now();
        if (!editMode) {
            Playlist playlist = new Playlist(name, description, now, now);
            playlistManager.insert(playlist);
            playlistsPageController.refresh();
        } else {
            Playlist playlist = new Playlist(this.playlist.getId(), name, description);
            playlistManager.update(playlist);
            playlistController.setPlaylist(playlist);
        }
        Stage stage = (Stage) txtFieldPlaylistName.getScene().getWindow();
        stage.close();
    }

    public void setPlaylistsPageController(PlaylistsPageController playlistsPageController) {
        this.playlistsPageController = playlistsPageController;
    }

    public void setPlaylistController(PlaylistController playlistController) {
        this.playlistController = playlistController;
    }

    public void setEditMode(boolean editMode) {
        this.editMode = editMode;
    }

    public void setPlaylist(Playlist playlist) {
        this.playlist = playlist;
    }
}
