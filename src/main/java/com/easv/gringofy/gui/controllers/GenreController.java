package com.easv.gringofy.gui.controllers;

import com.easv.gringofy.be.*;
import com.easv.gringofy.bll.GenreManager;
import com.easv.gringofy.bll.PlaylistManager;
import com.easv.gringofy.bll.SongManager;
import com.easv.gringofy.gui.MusicPlayer;
import com.easv.gringofy.gui.controllers.creators.GenreCreatorController;
import com.easv.gringofy.gui.controllers.creators.PlaylistCreatorController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class GenreController extends MusicPlayer implements Initializable {

    private Genre genre;
    private final GenreManager genreManager = new GenreManager();
    private final SongManager songManager = new SongManager();
    private List<Song> songs;
    private List<Song> defaultSortedSongs;

    @FXML
    private Label lblGenreName;
    @FXML
    private ImageView imgGenre;
    @FXML
    private Button btnGenreOptions;
    @FXML
    private ContextMenu contextMenu;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.setSortingLooks();
        super.initialize(location, resources);
        btnGenreOptions.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                contextMenu.show(btnGenreOptions, event.getScreenX(), event.getScreenY());
            }
        });
    }

    public void setGenre(Genre genre) throws SQLException {
        this.genre = genre;
        setGenreInformation();
        fetchSongs();
        super.setSongs(songs);
    }

    private void fetchSongs() throws SQLException {
        songs = songManager.getAllSongsByGenre(genre);
        defaultSortedSongs = new ArrayList<>(songs);
    }

    private void setGenreInformation() {
        lblGenreName.setText(genre.getName());
        File file = new File(genre.getPath());
        Image image = new Image(file.toURI().toString());
        imgGenre.setImage(image);

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
    private void edit() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/easv/gringofy/views/genre-creator.fxml"));
        Parent root = loader.load();
        GenreCreatorController controller = loader.getController();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        controller.setEditMode(true);
        controller.setGenre(genre);
        controller.setGenreController(this);
        controller.setCurrentParameters();
        stage.setTitle("Genre Editor");
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    private void delete() {
        genreManager.delete(genre);
    }

    @FXML
    private void sortByTitleOrArtist() {
        super.sortByTitleOrArtist(songs, defaultSortedSongs);
    }

    @FXML
    private void sortByReleaseDate() {
        super.sortByReleaseDate(songs, defaultSortedSongs);
    }

    @FXML
    private void sortByDuration() {
        super.sortByDuration(songs, defaultSortedSongs);
    }
    public void moveUpwards(Song song) {
        int i = defaultSortedSongs.indexOf(song);
        if(i>0) {
            Song song2 = defaultSortedSongs.get(i - 1);
            defaultSortedSongs.set(i, song2);
            defaultSortedSongs.set(i - 1, song);
            setSongs(defaultSortedSongs);
            songs = defaultSortedSongs;
            GenreSong genreSong1 = new GenreSong(song.getGenreSongId());
            GenreSong genreSong2 = new GenreSong(song2.getGenreSongId());
            genreManager.decrementPosition(genreSong1);
            genreManager.incrementPosition(genreSong2);
        }
    }
    public void moveDownwards(Song song){
        int i = defaultSortedSongs.indexOf(song);
        if(i<defaultSortedSongs.size()-1) {
            Song song2 = defaultSortedSongs.get(i+1);
            defaultSortedSongs.set(i, song2);
            defaultSortedSongs.set(i + 1, song);
            setSongs(defaultSortedSongs);
            songs = defaultSortedSongs;
            GenreSong genreSong1 = new GenreSong(song.getGenreSongId());
            GenreSong genreSong2 = new GenreSong(song2.getGenreSongId());
            genreManager.incrementPosition(genreSong1);
            genreManager.decrementPosition(genreSong2);
        }
    }
}
