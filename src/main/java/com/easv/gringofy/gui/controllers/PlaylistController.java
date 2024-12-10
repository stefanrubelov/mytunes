package com.easv.gringofy.gui.controllers;

import com.easv.gringofy.be.Playlist;
import com.easv.gringofy.be.Song;
import com.easv.gringofy.bll.PlaylistManager;
import com.easv.gringofy.bll.SongManager;
import com.easv.gringofy.exceptions.PlayerException;
import com.easv.gringofy.gui.MusicPlayer;
import com.easv.gringofy.gui.NodeBuilder;
import com.easv.gringofy.gui.SongQueue;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

import static java.util.Collections.sort;

public class PlaylistController extends MusicPlayer implements Initializable {

    private Playlist playlist;
    private final PlaylistManager playlistManager = new PlaylistManager();
    private final SongManager songManager = new SongManager();
    private final NodeBuilder nodeBuilder = new NodeBuilder();
    private List<Song> songs;
    private int currentSortingMethod = 0;

    private static final int ALPHABETICAL_TITLE_SORTING = 1;
    private static final int REVERSE_ALPHABETICAL_TITLE_SORTING = 2;
    private static final int RELEASE_DATE_SORTING = 3;
    private static final int REVERSE_RELEASE_DATE_SORTING = 4;
    private static final int DURATION_SORTING = 5;
    private static final int REVERSE_DURATION_SORTING = 6;
    private static final int ALPHABETICAL_ARTIST_SORTING = 7;
    private static final int REVERSE_ALPHABETICAL_ARTIST_SORTING = 8;
    private static final ImageView TRIANGLE_POINTING_DOWNWARDS = new ImageView(new Image("com/easv/gringofy/images/trianglePointingDownwards.png"));
    private static final ImageView TRIANGLE_POINTING_UPWARDS = new ImageView(new Image("com/easv/gringofy/images/trianglePointingUpwards.png"));

    @FXML
    private Label lblPlaylistName;
    @FXML
    private Label lblPlaylistDescription;
    @FXML
    private Button btnPlaylistOptions;
    @FXML
    private ContextMenu contextMenu;
    @FXML
    private VBox vboxSongsContainer;
    @FXML
    private HBox hboxToolsBar;
    @FXML
    private HBox hboxTitleContainer;
    @FXML
    private HBox hboxReleaseDateContainer;
    @FXML
    private HBox hboxDurationContainer;
    @FXML
    private Button btnSongTitle;

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
        TRIANGLE_POINTING_DOWNWARDS.setFitHeight(10);
        TRIANGLE_POINTING_DOWNWARDS.setFitWidth(10);
        TRIANGLE_POINTING_UPWARDS.setFitHeight(10);
        TRIANGLE_POINTING_UPWARDS.setFitWidth(10);
        btnPlaylistOptions.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                contextMenu.show(btnPlaylistOptions, event.getScreenX(), event.getScreenY());
            }
        });
    }

    private void fetchSongs() {
        try {
            songs = songManager.getAllSongsByPlaylist(playlist.getId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setSongs() {
        vboxSongsContainer.getChildren().clear();
        AtomicInteger i = new AtomicInteger(1);
        songs.forEach(song -> {
            vboxSongsContainer.getChildren().add(nodeBuilder.songToPlaylistSongNode(song, playlist, i.get()));
            i.getAndIncrement();
        });
    }

    @FXML
    private void delete() throws PlayerException, IOException, SQLException {
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

    @FXML
    private void edit() throws IOException {
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

    @FXML
    private void addToQueue() {
        songs.forEach(song -> SongQueue.addSong(song));
    }

    private void clearIndications() {
        hboxTitleContainer.getChildren().remove(TRIANGLE_POINTING_DOWNWARDS);
        hboxTitleContainer.getChildren().remove(TRIANGLE_POINTING_UPWARDS);
        hboxDurationContainer.getChildren().remove(TRIANGLE_POINTING_DOWNWARDS);
        hboxDurationContainer.getChildren().remove(TRIANGLE_POINTING_UPWARDS);
        hboxReleaseDateContainer.getChildren().remove(TRIANGLE_POINTING_DOWNWARDS);
        hboxReleaseDateContainer.getChildren().remove(TRIANGLE_POINTING_UPWARDS);
        btnSongTitle.setText("Title");
    }

    @FXML
    private void sortByTitleOrArtist() {
        clearIndications();
        if (currentSortingMethod == ALPHABETICAL_TITLE_SORTING) {
            songs.sort(new Comparator<Song>() {
                @Override
                public int compare(Song o1, Song o2) {
                    return o2.getTitle().compareTo(o1.getTitle());
                }
            });
            currentSortingMethod = REVERSE_ALPHABETICAL_TITLE_SORTING;
            hboxTitleContainer.getChildren().add(TRIANGLE_POINTING_DOWNWARDS);
            btnSongTitle.setText("Title");
        } else if (currentSortingMethod == REVERSE_ALPHABETICAL_TITLE_SORTING) {
            songs.sort(new Comparator<Song>() {
                @Override
                public int compare(Song o1, Song o2) {
                    return o1.getArtist().getName().compareTo(o2.getArtist().getName());
                }
            });
            currentSortingMethod = ALPHABETICAL_ARTIST_SORTING;
            hboxTitleContainer.getChildren().add(TRIANGLE_POINTING_UPWARDS);
            btnSongTitle.setText("Artist");
        } else if (currentSortingMethod == ALPHABETICAL_ARTIST_SORTING) {
            songs.sort(new Comparator<Song>() {
                @Override
                public int compare(Song o1, Song o2) {
                    return o2.getArtist().getName().compareTo(o1.getArtist().getName());
                }
            });
            currentSortingMethod = REVERSE_ALPHABETICAL_ARTIST_SORTING;
            hboxTitleContainer.getChildren().add(TRIANGLE_POINTING_DOWNWARDS);
            btnSongTitle.setText("Artist");
        } else {
            songs.sort(new Comparator<Song>() {
                @Override
                public int compare(Song o1, Song o2) {
                    return o1.getTitle().compareTo(o2.getTitle());
                }
            });
            currentSortingMethod = ALPHABETICAL_TITLE_SORTING;
            hboxTitleContainer.getChildren().add(TRIANGLE_POINTING_UPWARDS);
            btnSongTitle.setText("Title");
        }
        setSongs();
    }

    @FXML
    private void sortByReleaseDate() {
        clearIndications();
        if (currentSortingMethod == RELEASE_DATE_SORTING) {
            songs.sort(new Comparator<Song>() {
                @Override
                public int compare(Song o1, Song o2) {
                    return o2.getReleaseDate().compareTo(o1.getReleaseDate());
                }
            });
            currentSortingMethod = REVERSE_RELEASE_DATE_SORTING;
            hboxReleaseDateContainer.getChildren().add(TRIANGLE_POINTING_DOWNWARDS);
        } else {
            songs.sort(new Comparator<Song>() {
                @Override
                public int compare(Song o1, Song o2) {
                    return o1.getReleaseDate().compareTo(o2.getReleaseDate());
                }
            });
            currentSortingMethod = RELEASE_DATE_SORTING;
            hboxReleaseDateContainer.getChildren().add(TRIANGLE_POINTING_UPWARDS);
        }
        setSongs();
    }

    @FXML
    private void sortByDuration() {
        clearIndications();
        if (currentSortingMethod == DURATION_SORTING) {
            songs.sort(new Comparator<Song>() {
                @Override
                public int compare(Song o1, Song o2) {
                    return o2.getDuration() - o1.getDuration();
                }
            });
            currentSortingMethod = REVERSE_DURATION_SORTING;
            hboxDurationContainer.getChildren().add(TRIANGLE_POINTING_DOWNWARDS);
        } else {
            songs.sort(new Comparator<Song>() {
                @Override
                public int compare(Song o1, Song o2) {
                    return o1.getDuration() - o2.getDuration();
                }
            });
            currentSortingMethod = DURATION_SORTING;
            hboxDurationContainer.getChildren().add(TRIANGLE_POINTING_UPWARDS);
        }
        setSongs();
    }

    @FXML
    private void playPlaylist() {
        SongQueue.forcePlay(songs.getFirst());
        int i = songs.size();
        for (int j = 1; j < i; j++) {
            SongQueue.addSong(songs.get(j));
        }
    }
}
