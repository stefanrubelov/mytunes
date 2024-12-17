package com.easv.gringofy.gui.controllers.creators;

import com.easv.gringofy.be.Artist;
import com.easv.gringofy.bll.ArtistManager;
import com.easv.gringofy.exceptions.PlayerException;
import com.easv.gringofy.gui.controllers.ArtistController;
import com.easv.gringofy.gui.controllers.ArtistsPageController;
import com.easv.gringofy.gui.models.PlayerModel;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.time.LocalDateTime;

public class ArtistCreatorController {

    private Artist artist;
    private ArtistsPageController artistsPageController;
    private ArtistController artistController;
    private boolean editMode = false;

    private final PlayerModel playerModel = new PlayerModel();
    private final ArtistManager artistManager = new ArtistManager();

    @FXML
    private TextField txtFieldArtistName;
    @FXML
    private TextArea txtAreaArtistDescription;

    public void createArtist() throws PlayerException, SQLException {
        String name = txtFieldArtistName.getText();
        String description = txtAreaArtistDescription.getText();
        LocalDateTime now = LocalDateTime.now();
        if (!editMode) {
            Artist artist = new Artist(name, description, now, now);
            artistManager.insert(artist);
            artistsPageController.refresh();
        } else {
            Artist artist = new Artist(this.artist.getId(), name, description);
            artistManager.update(artist);
            artistController.setArtist(artist);
            refreshArtistsData();
        }
        Stage stage = (Stage) txtFieldArtistName.getScene().getWindow();
        stage.close();
    }

    private void refreshArtistsData() throws SQLException {
        playerModel.loadDefaultArtists();
    }
    public void setArtistsPageController(ArtistsPageController artistsPageController) {
        this.artistsPageController = artistsPageController;
    }
    public void setArtistController(ArtistController artistController) {
        this.artistController = artistController;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public void setCurrentParameters() {
        txtFieldArtistName.setText(artist.getName());
        txtAreaArtistDescription.setText(artist.getDescription());
    }

    public void setEditMode(boolean editMode) {
        this.editMode = editMode;
    }
}
