package com.easv.gringofy.gui.controllers;

import com.easv.gringofy.be.Artist;
import com.easv.gringofy.be.ArtistSong;
import com.easv.gringofy.be.Song;
import com.easv.gringofy.bll.ArtistManager;
import com.easv.gringofy.bll.SongManager;
import com.easv.gringofy.exceptions.PlayerException;
import com.easv.gringofy.gui.MusicPlayer;
import com.easv.gringofy.gui.controllers.creators.ArtistCreatorController;
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
        super.setSortingLooks();
        super.initialize(location, resources);
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
    private void play() {
        super.play(songs);
    }

    @FXML
    private void addToQueue() {
        super.addToQueue(songs);
    }

    @FXML
    private void edit() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/easv/gringofy/views/artist-creator.fxml"));
        Parent root;
        try {
            root = loader.load();
            ArtistCreatorController controller = loader.getController();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            controller.setArtistController(this);
            controller.setEditMode(true);
            controller.setArtist(artist);
            controller.setCurrentParameters();
            stage.setTitle("Artist editor");
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void delete() throws PlayerException, SQLException, IOException {
        artistManager.deleteArtist(artist);
        refreshData();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/easv/gringofy/views/home-page-view.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) lblArtistName.getScene().getWindow();
        stage.setScene(scene);
    }

    @FXML
    private void sortByTitle() {
        super.sortByTitle(songs, defaultSortedSongs);
    }

    @FXML
    private void sortByReleaseDate() {
        super.sortByReleaseDate(songs, defaultSortedSongs);
    }

    @FXML
    private void sortByDuration() {
        super.sortByDuration(songs, defaultSortedSongs);
    }

    public void moveUpwards(Song song) throws SQLException {
        int i = defaultSortedSongs.indexOf(song);
        if(i>0) {
            Song song2 = defaultSortedSongs.get(i - 1);
            defaultSortedSongs.set(i, song2);
            defaultSortedSongs.set(i - 1, song);
            setSongs(defaultSortedSongs);
            songs = defaultSortedSongs;
            ArtistSong artistSong1 = new ArtistSong(song.getArtistSongId());
            ArtistSong artistSong2 = new ArtistSong(song2.getArtistSongId());
            artistManager.decrementPosition(artistSong1);
            artistManager.incrementPosition(artistSong2);
        }
    }
    public void moveDownwards(Song song) throws SQLException {
        int i = defaultSortedSongs.indexOf(song);
        if(i<defaultSortedSongs.size()-1) {
            Song song2 = defaultSortedSongs.get(i+1);
            defaultSortedSongs.set(i, song2);
            defaultSortedSongs.set(i + 1, song);
            setSongs(defaultSortedSongs);
            songs = defaultSortedSongs;
            ArtistSong artistSong1 = new ArtistSong(song.getArtistSongId());
            ArtistSong artistSong2 = new ArtistSong(song2.getArtistSongId());
            artistManager.incrementPosition(artistSong1);
            artistManager.decrementPosition(artistSong2);
        }
    }
}
