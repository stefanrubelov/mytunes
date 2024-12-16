package com.easv.gringofy.gui.controllers.creators;

import com.easv.gringofy.be.Playlist;
import com.easv.gringofy.bll.PlaylistManager;
import com.easv.gringofy.exceptions.PlayerException;
import com.easv.gringofy.gui.controllers.PlaylistController;
import com.easv.gringofy.gui.controllers.PlaylistsPageController;
import com.easv.gringofy.gui.models.PlayerModel;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.time.LocalDateTime;

public class PlaylistCreatorController {

    private final PlaylistManager playlistManager = new PlaylistManager();
    private final PlayerModel playerModel = new PlayerModel();
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
            refreshPlaylistsData();
        }
        Stage stage = (Stage) txtFieldPlaylistName.getScene().getWindow();
        stage.close();
    }

    private void refreshPlaylistsData() throws SQLException {
        playerModel.loadDefaultPlaylists();
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

    public void setCurrentParameters() {
        txtFieldPlaylistName.setText(playlist.getTitle());
        txtAreaPlaylistDescription.setText(playlist.getDescription());
    }
}
