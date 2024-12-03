package com.easv.gringofy.gui;

import com.easv.gringofy.be.Album;
import com.easv.gringofy.be.Playlist;
import com.easv.gringofy.be.PlaylistSong;
import com.easv.gringofy.be.Song;
import com.easv.gringofy.bll.PlaylistManager;
import com.easv.gringofy.exceptions.PlayerException;
import com.easv.gringofy.gui.controllers.PlaylistController;
import com.easv.gringofy.gui.controllers.PlaylistController;
import com.easv.gringofy.gui.models.PlayerModel;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.io.IOException;
import java.util.Objects;

public class NodeBuilder {
    private static final String DEFAULT_SONG_PICTURE = "/com/easv/gringofy/images/defaultSongPicture.png";
    private static final String OPTIONS_PICTURE = "/com/easv/gringofy/images/tripleDots.png";
    private static final String DEFAULT_PLAYLIST_PICTURE = "/com/easv/gringofy/images/logo.png";
    private static final String DEFAULT_ALBUM_PICTURE = "/com/easv/gringofy/images/defaultAlbumPicture.png";
    private static final String PLAY_SONG_ICON = "/com/easv/gringofy/images/playSongIcon.png";
    private final PlayerModel playerModel = new PlayerModel();
    private final PlaylistManager playlistManager = new PlaylistManager();

    public HBox songToNode(Song song) {
        // Creates the container for the node
        HBox hbox = new HBox();
        Image songImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream(DEFAULT_SONG_PICTURE)));
        ImageView songImageView = new ImageView(songImage);
        songImageView.setFitWidth(35);
        songImageView.setFitHeight(35);
        Image playSongIcon = new Image(Objects.requireNonNull(getClass().getResourceAsStream(PLAY_SONG_ICON)));
        ImageView playSongIconView = new ImageView(playSongIcon);
        playSongIconView.setFitWidth(35);
        playSongIconView.setFitHeight(35);
        StackPane songImageWrapper = new StackPane(songImageView, playSongIconView);
        Image optionsImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream(OPTIONS_PICTURE)));
        ImageView optionsImageView = new ImageView(optionsImage);
        optionsImageView.setFitHeight(25);
        optionsImageView.setFitWidth(25);
        HBox imageWrapper = new HBox();
        imageWrapper.prefWidth(35);
        imageWrapper.prefHeight(35);
        imageWrapper.getChildren().add(optionsImageView);
        imageWrapper.setAlignment(Pos.CENTER);

        // Clip the ImageView to a rounded rectangle, so that songImage is not square (cannot use border-radius or background radius)
        Rectangle clip = new Rectangle(35, 35);
        clip.setArcWidth(10);
        clip.setArcHeight(10);
        songImageView.setClip(clip);

        // VBox for text (title and artist name)
        VBox vbox = new VBox();
        Label titleLabel = new Label(song.getTitle());
        Label artistLabel = new Label(song.getArtist().getName());

        vbox.getChildren().addAll(titleLabel, artistLabel);
        // Spacer is created to push the image wrapper maximally to the right
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        hbox.getChildren().addAll(songImageWrapper, vbox, spacer, imageWrapper);

        // Add menu items
        ContextMenu songMenu = new ContextMenu();
        Label hoverItem = new Label("Add to playlist");
        CustomMenuItem item1 = new CustomMenuItem(hoverItem);
        MenuItem item2 = new MenuItem("Add to favorites");
        MenuItem item3 = new MenuItem("Add to queue");
        songMenu.getItems().addAll(item1, item2, item3);

        // Menu for the available playlists
        ContextMenu playlistsMenu = new ContextMenu();
        List<Playlist> playlists = playerModel.getDefaultPlaylists();
        playlists.forEach(playlist -> {
            MenuItem menuItem = new MenuItem(playlist.toString());
            menuItem.setOnAction(event -> {
                PlaylistSong playlistSong = new PlaylistSong(playlist.getId(), song.getId());
                try {
                    playlistManager.addSong(playlist, song);
                } catch (PlayerException e) {
                    throw new RuntimeException(e);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });
            playlistsMenu.getItems().add(menuItem);
        });


        // Set actions for menu items
//        item2.setOnAction(event -> System.out.println("Add the song to the playlist")); // to implement
        item3.setOnAction(event -> playerModel.addSongToQueue(song));
        hoverItem.setOnMouseEntered(event -> {
            playlistsMenu.show(hoverItem, Side.LEFT, -10, -8);
        });
        // Show the context menu on left-click
        imageWrapper.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                songMenu.show(songImageView, event.getScreenX(), event.getScreenY());
            }
        });

        hbox.getStyleClass().add("song-node");
        songImageWrapper.getStyleClass().add("song--node-image-wrapper");
        songImageView.getStyleClass().add("song-node-image");
        playSongIconView.getStyleClass().add("song-node-play-icon");
        titleLabel.getStyleClass().add("song-node-title");
        vbox.getStyleClass().add("song-node-text");
        artistLabel.getStyleClass().add("song-node-artist");
        optionsImageView.getStyleClass().add("song-node-options");
        songMenu.getStyleClass().add("song-node-menu");
        imageWrapper.getStyleClass().add("song-node-options-wrapper");
        playlistsMenu.getStyleClass().add("song-node-playlists");
        return hbox;
    }

    public VBox playlistToNode(Playlist playlist) {
        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        vbox.getStyleClass().add("playlist-node");
        ImageView imageView = new ImageView();
        if (playlist.getImage() != null) {
            imageView.setImage(playlist.getImage());
        } else {
            Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(DEFAULT_PLAYLIST_PICTURE)));
            imageView.setImage(image);
        }
        imageView.setFitWidth(100);
        imageView.setFitHeight(100);
        Label titleLabel = new Label(playlist.getTitle());
        vbox.getChildren().addAll(imageView, titleLabel);
        vbox.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/easv/gringofy/views/playlist-view.fxml"));
                try {
                    Parent root = loader.load();
                    PlaylistController controller = (PlaylistController) loader.getController();
                    controller.setPlaylist(playlist);
                    Scene scene = new Scene(root);
                    Stage stage = (Stage) vbox.getScene().getWindow();
                    stage.setScene(scene);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
        });
        return vbox;
    }

    public HBox albumToNode(Album album) {
        HBox hbox = new HBox();
        hbox.setAlignment(Pos.CENTER_LEFT);
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(DEFAULT_ALBUM_PICTURE)));
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(50);
        imageView.setFitHeight(50);
        Label titleLabel = new Label(album.getTitle());
        hbox.getStyleClass().add("album-node");
        titleLabel.getStyleClass().add("album-node-title");
        hbox.getChildren().addAll(imageView, titleLabel);
        return hbox;
    }

    public HBox songToPlaylistSongNode(Song song, int index) {
        HBox hbox = new HBox();
        hbox.setAlignment(Pos.CENTER_LEFT);
        Label songIdLabel = new Label(String.valueOf(index));
        ImageView imageView = new ImageView();
        imageView.setFitWidth(35);
        imageView.setFitHeight(35);
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(DEFAULT_SONG_PICTURE)));
        imageView.setImage(image);
        VBox vbox = new VBox();
        Label titleLabel = new Label(song.getTitle());
        Label artistLabel = new Label(song.getArtist().getName());
        vbox.getChildren().addAll(titleLabel, artistLabel);

        Label releasedDateLabel = new Label(song.getReleaseDate());
        Label durationLabel = new Label(formatTime(song.getDuration()));

        Image optionsImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream(OPTIONS_PICTURE)));
        ImageView optionsImageView = new ImageView(optionsImage);
        optionsImageView.setFitHeight(25);
        optionsImageView.setFitWidth(25);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        hbox.getStyleClass().add("playlist-song-node");
        releasedDateLabel.getStyleClass().add("song-released-date-label");
        songIdLabel.getStyleClass().add("song-id-label");
        artistLabel.getStyleClass().add("song-artist-label");
        durationLabel.getStyleClass().add("song-duration-label");
        optionsImageView.getStyleClass().add("song-options-image-view");
        HBox.setMargin(durationLabel, new Insets(0, 20, 0, 82));
        hbox.getChildren().addAll(songIdLabel, imageView, vbox, spacer, releasedDateLabel, durationLabel, optionsImageView);

        return hbox;
    }

    public String formatTime(int totalSeconds) {
        int minutes = totalSeconds / 60;
        int seconds = totalSeconds % 60;
        return String.format("%d:%02d", minutes, seconds);
    }
}