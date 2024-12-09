package com.easv.gringofy.gui.controllers;

import com.easv.gringofy.be.Album;
import com.easv.gringofy.be.Artist;
import com.easv.gringofy.be.Genre;
import com.easv.gringofy.be.Song;
import com.easv.gringofy.bll.AlbumManager;
import com.easv.gringofy.bll.ArtistManager;
import com.easv.gringofy.bll.GenreManager;
import com.easv.gringofy.bll.SongManager;
import com.easv.gringofy.exceptions.PlayerException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ResourceBundle;

import static java.nio.file.Files.move;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class SongCreatorController implements Initializable {

    private String filePath;
    private final ArtistManager artistManager = new ArtistManager();
    private final AlbumManager albumManager = new AlbumManager();
    private final GenreManager genreManager = new GenreManager();
    private final SongManager songManager = new SongManager();
    private final static String SONGS_DIRECTORY_PATH = "src/main/resources/songs";
    @FXML
    private VBox vboxInputsContainer;
    @FXML
    private Label lblPath;
    @FXML
    private TextField txtFieldSongTitle;
    @FXML
    private TextField txtFieldSongReleaseDate;
    @FXML
    private ComboBox<Artist> comboBoxSongArtist;
    @FXML
    private ComboBox<Genre> comboBoxSongGenre;
    @FXML
    private ComboBox<Album> comboBoxSongAlbum;

    @FXML
    private void selectFile(ActionEvent event) throws IOException {
        Stage stage = (Stage) vboxInputsContainer.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select a Song");
        FileChooser.ExtensionFilter mp3Filter = new FileChooser.ExtensionFilter("MP3 Files (*.mp3)", "*.mp3");
        fileChooser.getExtensionFilters().add(mp3Filter);
        // set initial directory
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            lblPath.setText("Selected Path: " + selectedFile.getAbsolutePath());
            setFilePath(selectedFile.getAbsolutePath());
        } else {
            lblPath.setText("No file selected");
        }
    }

    private void setFilePath(String filePath) throws IOException {
        Path sourcePath = Paths.get(filePath);

        Path destinationDir = Paths.get(SONGS_DIRECTORY_PATH);

        Path destinationPath = destinationDir.resolve(sourcePath.getFileName());

        Files.move(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);

        this.filePath = destinationPath.toString();
    }

    public void createSong(ActionEvent actionEvent) {
        File mp3File = new File(filePath);
        Media media = new Media(mp3File.toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setOnReady(() -> {
            String title = txtFieldSongTitle.getText();
            String releaseDate = txtFieldSongReleaseDate.getText();
            Artist artist = comboBoxSongArtist.getSelectionModel().getSelectedItem();
            Genre genre = comboBoxSongGenre.getSelectionModel().getSelectedItem();
            Album album = comboBoxSongAlbum.getSelectionModel().getSelectedItem();
            LocalDateTime now = LocalDateTime.now();
            int durationInSeconds = (int) media.getDuration().toSeconds();
            Song song = new Song(title, durationInSeconds, genre, artist, releaseDate, filePath, now, now);
            try {
                songManager.insert(song);
                Stage stage = (Stage) vboxInputsContainer.getScene().getWindow();
                stage.close();
            } catch (PlayerException | SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            List<Genre> genres = genreManager.getAllGenres();
            List<Artist> artists = artistManager.getAllArtists();
            List<Album> albums = albumManager.getAllAlbums();
            comboBoxSongGenre.getItems().addAll(genres);
            comboBoxSongArtist.getItems().addAll(artists);
            comboBoxSongAlbum.getItems().addAll(albums);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (PlayerException e) {
            throw new RuntimeException(e);
        }
    }
}
