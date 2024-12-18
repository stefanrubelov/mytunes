package com.easv.gringofy.gui;

import com.easv.gringofy.be.Song;
import com.easv.gringofy.bll.SongQueue;
import com.easv.gringofy.exceptions.PlayerException;
import com.easv.gringofy.gui.models.PlayerModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

public class MusicPlayer implements Initializable {

    protected static final int DEFAULT_SORTING = 0;
    protected static final int ALPHABETICAL_TITLE_SORTING = 1;
    protected static final int REVERSE_ALPHABETICAL_TITLE_SORTING = 2;
    protected static final int RELEASE_DATE_SORTING = 3;
    protected static final int REVERSE_RELEASE_DATE_SORTING = 4;
    protected static final int DURATION_SORTING = 5;
    protected static final int REVERSE_DURATION_SORTING = 6;
    protected static final int ALPHABETICAL_ARTIST_SORTING = 7;
    protected static final int REVERSE_ALPHABETICAL_ARTIST_SORTING = 8;

    protected static final ImageView TRIANGLE_POINTING_DOWNWARDS = new ImageView(new Image("com/easv/gringofy/images/trianglePointingDownwards.png"));
    protected static final ImageView TRIANGLE_POINTING_UPWARDS = new ImageView(new Image("com/easv/gringofy/images/trianglePointingUpwards.png"));
    protected static final VolumeSlider volumeSlider = new VolumeSlider(0, 1, 1);

    private final PlayerModel playerModel = new PlayerModel();
    private final NodeBuilder nodeBuilder = new NodeBuilder();

    private int currentSortingMethod = DEFAULT_SORTING;

    @FXML
    protected Button buttonSwitchState;
    @FXML
    protected StackPane stackPaneToolsContainer;
    @FXML
    protected ProgressBar progressBar;

    @FXML
    private HBox hboxTitleContainer;
    @FXML
    private HBox hboxReleaseDateContainer;
    @FXML
    private HBox hboxDurationContainer;
    @FXML
    private Button btnSongTitle;
    @FXML
    private VBox vboxSongsContainer;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setVolumeSlider();
        progressBar.progressProperty().bind(SongQueue.getProgressProperty());
    }

    @FXML
    protected void goToHomePageView(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/easv/gringofy/views/home-page-view.fxml"));
        Parent root = loader.load();
        MusicPlayer controller = loader.getController();
        controller.changeSwitchStateButton();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }

    @FXML
    protected void goToGenresView(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/easv/gringofy/views/genres-view.fxml"));
        Parent root = loader.load();
        MusicPlayer controller = loader.getController();
        controller.changeSwitchStateButton();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }

    @FXML
    protected void goToPlaylistsView(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/easv/gringofy/views/playlists-view.fxml"));
        Parent root = loader.load();
        MusicPlayer controller = loader.getController();
        controller.changeSwitchStateButton();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }

    @FXML
    protected void goToArtistsView(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/easv/gringofy/views/artists-view.fxml"));
        Parent root = loader.load();
        MusicPlayer controller = loader.getController();
        controller.changeSwitchStateButton();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }

    @FXML
    protected void goToAlbumsView(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/easv/gringofy/views/albums-view.fxml"));
        Parent root = loader.load();
        MusicPlayer controller = loader.getController();
        controller.changeSwitchStateButton();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }

    @FXML
    protected void playSong() {
        if (SongQueue.switchState()) {
            changeSwitchStateButton2();
        }
    }

    @FXML
    protected void playPreviousSong() {
        if (SongQueue.playPreviousSong()) {
            changeSwitchStateButton2();
        }
    }

    @FXML
    protected void playNextSong() {
        if (SongQueue.playNextSong()) {
            changeSwitchStateButton2();
        }
    }

    protected void play(List<Song> songs) {
        if (!songs.isEmpty()) {
            SongQueue.forcePlay(songs.getFirst());
            if (buttonSwitchState.getStyleClass().remove("play-button")) {
                buttonSwitchState.getStyleClass().add("pause-button");
            }
            int i = songs.size();
            for (int j = 1; j < i; j++) {
                SongQueue.addSong(songs.get(j));
            }
        }
    }

    protected void addToQueue(List<Song> songs) {
        songs.forEach(SongQueue::addSong);
    }

    protected void changeSwitchStateButton() {
        System.out.println(SongQueue.getState());
        if (SongQueue.getState()) {
            if (buttonSwitchState.getStyleClass().remove("play-button")) {
                buttonSwitchState.getStyleClass().add("pause-button");
            }
        } else {
            if (buttonSwitchState.getStyleClass().remove("pause-button")) {
                buttonSwitchState.getStyleClass().add("play-button");
            }
        }
    }

    protected void changeSwitchStateButton2() { // Made by trial and error, I have no idea why it doesn't work with just one function and have no idea why it works now.
        System.out.println(SongQueue.getState());
        if (SongQueue.getState()) {
            if (buttonSwitchState.getStyleClass().remove("pause-button")) {
                buttonSwitchState.getStyleClass().add("play-button");
            }
        } else {

            if (buttonSwitchState.getStyleClass().remove("play-button")) {
                buttonSwitchState.getStyleClass().add("pause-button");
            }
        }
    }

    protected void setSongs(List<Song> songs) {
        vboxSongsContainer.getChildren().clear();
        AtomicInteger i = new AtomicInteger(1);
        songs.forEach(song -> {
            vboxSongsContainer.getChildren().add(nodeBuilder.songToPlaylistSongNode(song, i.get(), this));
            i.getAndIncrement();
        });
    }

    protected void clearIndications() {
        hboxTitleContainer.getChildren().remove(TRIANGLE_POINTING_DOWNWARDS);
        hboxTitleContainer.getChildren().remove(TRIANGLE_POINTING_UPWARDS);
        hboxDurationContainer.getChildren().remove(TRIANGLE_POINTING_DOWNWARDS);
        hboxDurationContainer.getChildren().remove(TRIANGLE_POINTING_UPWARDS);
        hboxReleaseDateContainer.getChildren().remove(TRIANGLE_POINTING_DOWNWARDS);
        hboxReleaseDateContainer.getChildren().remove(TRIANGLE_POINTING_UPWARDS);
        btnSongTitle.setText("Title");
    }

    protected void sortByTitleOrArtist(List<Song> songs, List<Song> defaultSortedSongs) {
        clearIndications();
        if (currentSortingMethod == ALPHABETICAL_TITLE_SORTING) {
            songs.sort((o1, o2) -> o2.getTitle().compareTo(o1.getTitle()));
            currentSortingMethod = REVERSE_ALPHABETICAL_TITLE_SORTING;
            hboxTitleContainer.getChildren().add(TRIANGLE_POINTING_DOWNWARDS);
            btnSongTitle.setText("Title");
        } else if (currentSortingMethod == REVERSE_ALPHABETICAL_TITLE_SORTING) {
            songs.sort(Comparator.comparing(o -> o.getArtist().getName()));
            currentSortingMethod = ALPHABETICAL_ARTIST_SORTING;
            hboxTitleContainer.getChildren().add(TRIANGLE_POINTING_UPWARDS);
            btnSongTitle.setText("Artist");
        } else if (currentSortingMethod == ALPHABETICAL_ARTIST_SORTING) {
            songs.sort((o1, o2) -> o2.getArtist().getName().compareTo(o1.getArtist().getName()));
            currentSortingMethod = REVERSE_ALPHABETICAL_ARTIST_SORTING;
            hboxTitleContainer.getChildren().add(TRIANGLE_POINTING_DOWNWARDS);
            btnSongTitle.setText("Artist");
        } else if (currentSortingMethod == REVERSE_ALPHABETICAL_ARTIST_SORTING) {
            songs = defaultSortedSongs;
            currentSortingMethod = DEFAULT_SORTING;
        } else {
            songs.sort(Comparator.comparing(Song::getTitle));
            currentSortingMethod = ALPHABETICAL_TITLE_SORTING;
            hboxTitleContainer.getChildren().add(TRIANGLE_POINTING_UPWARDS);
            btnSongTitle.setText("Title");
        }
        setSongs(songs);
    }

    protected void sortByTitle(List<Song> songs, List<Song> defaultSortedSongs) {
        clearIndications();
        if (currentSortingMethod == ALPHABETICAL_TITLE_SORTING) {
            songs.sort((o1, o2) -> o2.getTitle().compareTo(o1.getTitle()));
            currentSortingMethod = REVERSE_ALPHABETICAL_TITLE_SORTING;
            hboxTitleContainer.getChildren().add(TRIANGLE_POINTING_DOWNWARDS);
            btnSongTitle.setText("Title");
        } else if (currentSortingMethod == REVERSE_ALPHABETICAL_TITLE_SORTING) {
            songs = defaultSortedSongs;
            currentSortingMethod = DEFAULT_SORTING;
        } else {
            songs.sort(Comparator.comparing(Song::getTitle));
            currentSortingMethod = ALPHABETICAL_TITLE_SORTING;
            hboxTitleContainer.getChildren().add(TRIANGLE_POINTING_UPWARDS);
        }
        setSongs(songs);
    }

    protected void sortByReleaseDate(List<Song> songs, List<Song> defaultSortedSongs) {
        clearIndications();
        if (currentSortingMethod == RELEASE_DATE_SORTING) {
            songs.sort((o1, o2) -> o2.getReleaseDate().compareTo(o1.getReleaseDate()));
            hboxReleaseDateContainer.getChildren().add(TRIANGLE_POINTING_DOWNWARDS);
            currentSortingMethod = REVERSE_RELEASE_DATE_SORTING;
        } else if (currentSortingMethod == REVERSE_RELEASE_DATE_SORTING) {
            currentSortingMethod = DEFAULT_SORTING;
            songs = defaultSortedSongs;
        } else {
            songs.sort(Comparator.comparing(Song::getReleaseDate));
            hboxReleaseDateContainer.getChildren().add(TRIANGLE_POINTING_UPWARDS);
            currentSortingMethod = RELEASE_DATE_SORTING;
        }
        setSongs(songs);
    }

    protected void sortByDuration(List<Song> songs, List<Song> defaultSortedSongs) {
        clearIndications();
        if (currentSortingMethod == DURATION_SORTING) {
            songs.sort((o1, o2) -> o2.getDuration() - o1.getDuration());
            currentSortingMethod = REVERSE_DURATION_SORTING;
            hboxDurationContainer.getChildren().add(TRIANGLE_POINTING_DOWNWARDS);
        } else if (currentSortingMethod == REVERSE_DURATION_SORTING) {
            songs = defaultSortedSongs;
            currentSortingMethod = DEFAULT_SORTING;
        } else {
            songs.sort(Comparator.comparingInt(Song::getDuration));
            currentSortingMethod = DURATION_SORTING;
            hboxDurationContainer.getChildren().add(TRIANGLE_POINTING_UPWARDS);
        }
        setSongs(songs);
    }

    protected void refreshData() throws PlayerException, SQLException {
        playerModel.loadDefaultPlaylists();
        playerModel.loadDefaultAlbums();
        playerModel.loadDefaultSongs();
        playerModel.loadDefaultArtists();
    }

    public int getCurrentSortingMethod() {
        return currentSortingMethod;
    }

    protected void setSortingLooks() {
        progressBar.progressProperty().bind(SongQueue.getProgressProperty());
        TRIANGLE_POINTING_DOWNWARDS.setFitHeight(10);
        TRIANGLE_POINTING_DOWNWARDS.setFitWidth(10);
        TRIANGLE_POINTING_UPWARDS.setFitHeight(10);
        TRIANGLE_POINTING_UPWARDS.setFitWidth(10);
    }

    protected void setVolumeSlider() {
        volumeSlider.setTranslateX(350);
        volumeSlider.setTranslateY(0);
        stackPaneToolsContainer.getChildren().add(volumeSlider);

        volumeSlider.volumeValueProperty().addListener((_, _, newValue) -> SongQueue.setVolume(newValue));
    }
}
