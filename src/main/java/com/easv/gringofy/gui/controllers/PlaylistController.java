package com.easv.gringofy.gui.controllers;

import com.easv.gringofy.be.Playlist;
import com.easv.gringofy.be.PlaylistSong;
import com.easv.gringofy.be.Song;
import com.easv.gringofy.bll.PlaylistManager;
import com.easv.gringofy.bll.SongManager;
import com.easv.gringofy.exceptions.PlayerException;
import com.easv.gringofy.gui.MusicPlayer;
import com.easv.gringofy.gui.NodeBuilder;
import com.easv.gringofy.bll.SongQueue;
import com.easv.gringofy.gui.controllers.creators.PlaylistCreatorController;
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

import static java.util.Collections.sort;

public class PlaylistController extends MusicPlayer implements Initializable {

    private Playlist playlist;
    private final PlaylistManager playlistManager = new PlaylistManager();
    private final SongManager songManager = new SongManager();
    private final NodeBuilder nodeBuilder = new NodeBuilder();
    private List<Song> songs;
    private List<Song> defaultSortedSongs;

    @FXML
    private Label lblPlaylistName;
    @FXML
    private Label lblPlaylistDescription;
    @FXML
    private Button btnPlaylistOptions;
    @FXML
    private ContextMenu contextMenu;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.setSortingLooks();
        btnPlaylistOptions.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                contextMenu.show(btnPlaylistOptions, event.getScreenX(), event.getScreenY());
            }
        });
    }

    public void setPlaylist(Playlist playlist) {
        this.playlist = playlist;
        setPlaylistInformation();
        fetchSongs();
        super.setSongs(songs);
    }

    public void setPlaylistInformation() {
        lblPlaylistName.setText(playlist.getTitle());
        lblPlaylistDescription.setText(playlist.getDescription());
    }



    private void fetchSongs() {
        try {
            songs = songManager.getAllSongsByPlaylist(playlist.getId());
            defaultSortedSongs = new ArrayList<>(songs);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @FXML
    private void delete() throws PlayerException, IOException, SQLException {
        playlistManager.delete(playlist);
        refreshData();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/easv/gringofy/views/playlists-view.fxml"));
        Parent root = loader.load();
        PlaylistsPageController controller = loader.getController();
        Scene scene = new Scene(root);
        Stage stage = (Stage) lblPlaylistName.getScene().getWindow();
        stage.setScene(scene);
    }

    @FXML
    private void edit() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/easv/gringofy/views/playlist-creator.fxml"));
        Parent root = loader.load();
        PlaylistCreatorController controller = (PlaylistCreatorController) loader.getController();
        controller.setEditMode(true);
        controller.setPlaylist(playlist);
        controller.setPlaylistController(this);
        controller.setCurrentParameters();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Playlist Editor");
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    private void addToQueue() {
        super.addToQueue(songs);
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

    @FXML
    private void play() {
        super.play(songs);
    }
    public void moveUpwards(Song song) throws PlayerException, SQLException {
        int i = defaultSortedSongs.indexOf(song);
        if(i>0) {
            Song song2 = defaultSortedSongs.get(i - 1);
            defaultSortedSongs.set(i, song2);
            defaultSortedSongs.set(i - 1, song);
            setSongs(defaultSortedSongs);
            songs = defaultSortedSongs;
            PlaylistSong playlistSong1 = new PlaylistSong(song.getPlaylistSongId());
            PlaylistSong playlistSong2 = new PlaylistSong(song2.getPlaylistSongId());
            playlistManager.decrementPosition(playlistSong1);
            playlistManager.incrementPosition(playlistSong2);
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
            PlaylistSong playlistSong1 = new PlaylistSong(song.getPlaylistSongId());
            PlaylistSong playlistSong2 = new PlaylistSong(song2.getPlaylistSongId());
            playlistManager.incrementPosition(playlistSong1);
            playlistManager.decrementPosition(playlistSong2);
        }
    }
}
