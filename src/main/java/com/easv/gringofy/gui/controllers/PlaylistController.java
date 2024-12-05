package com.easv.gringofy.gui.controllers;

import com.easv.gringofy.be.Playlist;
import com.easv.gringofy.be.Song;
import com.easv.gringofy.bll.PlaylistManager;
import com.easv.gringofy.bll.SongManager;
import com.easv.gringofy.exceptions.PlayerException;
import com.easv.gringofy.gui.MusicPlayer;
import com.easv.gringofy.gui.NodeBuilder;
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
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

public class PlaylistController extends MusicPlayer implements Initializable {

    private Playlist playlist;
    private final PlaylistManager playlistManager = new PlaylistManager();
    private final SongManager songManager = new SongManager();
    private final NodeBuilder nodeBuilder = new NodeBuilder();
    private List<Song> songs;

    @FXML private Label lblPlaylistName;
    @FXML private Label lblPlaylistDescription;
    @FXML private Button btnPlaylistOptions;
    @FXML private ContextMenu contextMenu;
    @FXML private VBox vboxSongsContainer;
    @FXML private HBox hboxToolsBar;

    public void setPlaylist(Playlist playlist) {
        this.playlist = playlist;
        setPlaylistInformation();
        fetchSongs();
        setSongs();
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
    private void fetchSongs(){
        try {
            songs = songManager.getAllSongsByPlaylist(playlist.getId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private void setSongs(){
        AtomicInteger i = new AtomicInteger(1);
        songs.forEach(song -> {
            vboxSongsContainer.getChildren().add(nodeBuilder.songToPlaylistSongNode(song,playlist, i.get()));
            i.getAndIncrement();
        });
    }
    @FXML private void delete() throws PlayerException, IOException, SQLException {
        List<Song> songs = songManager.getAllSongsByPlaylist(playlist.getId());
        playlistManager.delete(playlist);
        songs.forEach(song -> {
        });
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/easv/gringofy/views/playlists-view.fxml"));
        Parent root = loader.load();
        PlaylistsPageController controller = (PlaylistsPageController) loader.getController();
        Scene scene = new Scene(root);
        Stage stage = (Stage) lblPlaylistName.getScene().getWindow();
        stage.setScene(scene);
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
}
