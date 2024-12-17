package com.easv.gringofy.gui.controllers.creators;

import com.easv.gringofy.be.Album;
import com.easv.gringofy.be.Artist;
import com.easv.gringofy.bll.AlbumManager;
import com.easv.gringofy.bll.ArtistManager;
import com.easv.gringofy.exceptions.PlayerException;
import com.easv.gringofy.gui.controllers.AlbumController;
import com.easv.gringofy.gui.controllers.AlbumsPageController;
import com.easv.gringofy.gui.models.PlayerModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ResourceBundle;

public class AlbumCreatorController implements Initializable {
    private final AlbumManager albumManager = new AlbumManager();
    private final ArtistManager artistManager = new ArtistManager();
    private final PlayerModel playerModel = new PlayerModel();
    private AlbumsPageController albumsPageController;
    private AlbumController albumController;
    private Album album;

    private boolean editMode = false;

    @FXML
    private TextField txtFieldAlbumName;
    @FXML
    private TextArea txtAreaAlbumDescription;
    @FXML
    private TextField txtFieldAlbumYear;
    @FXML
    private ComboBox<Artist> comboBoxAlbumArtist;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            List<Artist> artists = artistManager.getAllArtists();
            comboBoxAlbumArtist.getItems().addAll(artists);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void createAlbum() throws PlayerException, SQLException {
        String name = txtFieldAlbumName.getText();
        String description = txtAreaAlbumDescription.getText();
        String releaseYear = txtFieldAlbumYear.getText();
        Artist artist = comboBoxAlbumArtist.getSelectionModel().getSelectedItem();
        LocalDateTime now = LocalDateTime.now();
        if (!editMode) {
            Album album = new Album(name, description,releaseYear, artist, now, now);
            albumManager.insert(album);
            albumsPageController.refresh();
        } else {
            Album album = new Album(this.album.getId(), name, description, releaseYear, artist);
            albumManager.update(album);
            albumController.setAlbum(album);
            refreshAlbumsData();
        }
        Stage stage = (Stage) txtFieldAlbumName.getScene().getWindow();
        stage.close();
    }

    private void refreshAlbumsData() throws SQLException, PlayerException {
        playerModel.loadDefaultAlbums();
    }
    public void setAlbumsPageController(AlbumsPageController albumsPageController) {
        this.albumsPageController = albumsPageController;
    }
    public void setAlbumController(AlbumController albumController) {
        this.albumController = albumController;
    }

    public void setAlbum(Album album) throws SQLException {
        this.album = album;
    }

    public void setCurrentParameters() {
        txtFieldAlbumName.setText(album.getTitle());
        txtAreaAlbumDescription.setText(album.getDescription());
        txtFieldAlbumYear.setText(album.getReleaseDate());
        comboBoxAlbumArtist.getSelectionModel().select(album.getArtist());
    }

    public void setEditMode(boolean editMode) {
        this.editMode = editMode;
    }


}
