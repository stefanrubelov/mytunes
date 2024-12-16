package com.easv.gringofy.gui.controllers;

import com.easv.gringofy.be.*;
import com.easv.gringofy.bll.AlbumManager;
import com.easv.gringofy.bll.SongManager;
import com.easv.gringofy.exceptions.PlayerException;
import com.easv.gringofy.gui.MusicPlayer;
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

public class AlbumController  extends MusicPlayer implements Initializable {

    private Album album;
    private List<Song> songs;
    private List<Song> defaultSortedSongs;
    private final AlbumManager albumManager = new AlbumManager();
    private final SongManager songManager = new SongManager();

    @FXML
    private Label lblAlbumName;

    @FXML
    private Label lblAlbumDescription;

    @FXML
    private Button btnAlbumOptions;

    @FXML
    private ContextMenu contextMenu;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.setSortingLooks();
        btnAlbumOptions.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                contextMenu.show(btnAlbumOptions, event.getScreenX(), event.getScreenY());
            }
        });
    }
    public void moveUpwards(Song song) throws PlayerException, SQLException {
        int i = defaultSortedSongs.indexOf(song);
        if(i>0) {
            Song song2 = defaultSortedSongs.get(i - 1);
            defaultSortedSongs.set(i, song2);
            defaultSortedSongs.set(i - 1, song);
            setSongs(defaultSortedSongs);
            songs = defaultSortedSongs;
            AlbumSong albumSong1 = new AlbumSong(song.getAlbumSongId());
            AlbumSong albumSong2 = new AlbumSong(song2.getAlbumSongId());
            albumManager.decrementPosition(albumSong1);
            albumManager.incrementPosition(albumSong2);
        }
    }
    public void moveDownwards(Song song) throws PlayerException, SQLException {
        int i = defaultSortedSongs.indexOf(song);
        if(i<defaultSortedSongs.size()-1) {
            Song song2 = defaultSortedSongs.get(i+1);
            defaultSortedSongs.set(i, song2);
            defaultSortedSongs.set(i + 1, song);
            setSongs(defaultSortedSongs);
            songs = defaultSortedSongs;
            AlbumSong albumSong1 = new AlbumSong(song.getAlbumSongId());
            AlbumSong albumSong2 = new AlbumSong(song2.getAlbumSongId());
            albumManager.incrementPosition(albumSong1);
            albumManager.decrementPosition(albumSong2);
        }
    }
    public void setAlbum(Album album) {
        this.album = album;
        setAlbumInformation();
        fetchSongs();
        super.setSongs(songs);
    }

    public void setAlbumInformation() {
        lblAlbumName.setText(album.getTitle());
        lblAlbumDescription.setText(album.getDescription());
    }



    private void fetchSongs() {
        try {
            songs = songManager.getAllSongsByAlbum(album.getId());
            defaultSortedSongs = new ArrayList<>(songs);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void addToQueue() {
        super.addToQueue(songs);
    }

    @FXML
    private void edit() {
    }

    @FXML
    private void delete() throws PlayerException, SQLException, IOException {
        albumManager.deleteAlbum(album);
        refreshData();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/easv/gringofy/views/home-page-view.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) lblAlbumName.getScene().getWindow();
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

    public void play() {
        super.play(songs);
    }
}
