package com.easv.gringofy.gui.controllers;

import com.easv.gringofy.be.Artist;
import com.easv.gringofy.be.Song;
import com.easv.gringofy.bll.ArtistManager;
import com.easv.gringofy.bll.SongManager;
import com.easv.gringofy.exceptions.PlayerException;
import com.easv.gringofy.gui.MusicPlayer;
import com.easv.gringofy.gui.SongQueue;
import com.easv.gringofy.gui.models.PlayerModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ArtistController extends MusicPlayer implements Initializable {

    private List<Song> songs;
    private List<Song> defaultSortedSongs;
    private Artist artist;
    private static final SongManager songManager = new SongManager();
    private static final ArtistManager artistManager = new ArtistManager();

    @FXML
    private Label lblArtistName;
    @FXML
    private Label lblArtistDescription;
    @FXML
    private Button btnArtistOptions;
    @FXML
    private ContextMenu contextMenu;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        progressBar.progressProperty().bind(SongQueue.getProgressProperty());
        TRIANGLE_POINTING_DOWNWARDS.setFitHeight(10);
        TRIANGLE_POINTING_DOWNWARDS.setFitWidth(10);
        TRIANGLE_POINTING_UPWARDS.setFitHeight(10);
        TRIANGLE_POINTING_UPWARDS.setFitWidth(10);
        btnArtistOptions.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                contextMenu.show(btnArtistOptions, event.getScreenX(), event.getScreenY());
            }
        });
    }
    private void fetchSongs() {
        try {
            songs = songManager.getAllSongsByArtist(artist.getId());
            defaultSortedSongs = new ArrayList<>(songs);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void setArtist(Artist artist) {
        this.artist = artist;
        setArtistInformation();
        fetchSongs();
        setSongs(songs);
    }
    private void setArtistInformation() {
        lblArtistName.setText(artist.getName());
        lblArtistDescription.setText(artist.getDescription());
    }

    @FXML
    private void play(ActionEvent actionEvent) {
        super.play(songs);
    }

    @FXML
    private void addToQueue(ActionEvent actionEvent) {
        super.addToQueue(songs);
    }

    @FXML
    private void edit(ActionEvent actionEvent) {
    }

    @FXML
    private void delete(ActionEvent actionEvent) throws PlayerException, SQLException, IOException {
        artistManager.deleteArtist(artist);
        System.out.println("delete");
        refreshData();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/easv/gringofy/views/home-page-view.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) lblArtistName.getScene().getWindow();
        stage.setScene(scene);
    }

    @FXML
    private void sortByTitle(ActionEvent actionEvent) {
        super.sortByTitle(songs, defaultSortedSongs);
    }

    @FXML
    private void sortByReleaseDate(ActionEvent actionEvent) {
        super.sortByReleaseDate(songs, defaultSortedSongs);
    }

    @FXML
    private void sortByDuration(ActionEvent actionEvent) {
        super.sortByDuration(songs, defaultSortedSongs);
    }

}
