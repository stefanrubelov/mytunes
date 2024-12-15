package com.easv.gringofy.gui;

import com.easv.gringofy.be.Album;
import com.easv.gringofy.be.Playlist;
import com.easv.gringofy.be.PlaylistSong;
import com.easv.gringofy.be.Song;
import com.easv.gringofy.bll.PlaylistManager;
import com.easv.gringofy.bll.SongManager;
import com.easv.gringofy.exceptions.PlayerException;
import com.easv.gringofy.gui.controllers.ArtistController;
import com.easv.gringofy.gui.controllers.PlaylistController;
import com.easv.gringofy.gui.controllers.SongCreatorController;
import com.easv.gringofy.gui.models.PlayerModel;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class NodeBuilder {
    private static final String DEFAULT_SONG_PICTURE = "/com/easv/gringofy/images/defaultSongPicture.png";
    private static final String OPTIONS_PICTURE = "/com/easv/gringofy/images/tripleDots.png";
    private static final String DEFAULT_PLAYLIST_PICTURE = "/com/easv/gringofy/images/logo.png";
    private static final String DEFAULT_ALBUM_PICTURE = "/com/easv/gringofy/images/defaultAlbumPicture.png";
    private static final String PLAY_SONG_ICON = "/com/easv/gringofy/images/playSongIcon.png";
    private static final String ARROW_UP_ICON = "/com/easv/gringofy/images/triangleUp.png";
    private static final String ARROW_DOWN_ICON = "/com/easv/gringofy/images/triangleDown.png";
    private static final int DEFAULT_SORTING = 0;
    private final PlayerModel playerModel = new PlayerModel();
    private final PlaylistManager playlistManager = new PlaylistManager();
    private final SongManager songManager = new SongManager();

    public HBox songToNode(Song song, Button switchStateButton) {
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
        MenuItem item4 = new MenuItem("Delete song");
        MenuItem item5 = new MenuItem("Edit Song");
        songMenu.getItems().addAll(item1, item2, item3, item4, item5);

        // Menu for the available playlists
        ContextMenu playlistsMenu = new ContextMenu();
        addPlaylists(song, playlistsMenu);


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

        // Set actions for menu items
//        item2.setOnAction(event -> System.out.println("Add the song to the playlist")); // to implement
        item3.setOnAction(event -> {
            SongQueue.addSong(song);
        });
        item4.setOnAction(event -> {
            try {
                songManager.delete(song.getId());
                FlowPane parent = (FlowPane) hbox.getParent();
                parent.getChildren().remove(hbox);
                playerModel.loadDefaultSongs();
            } catch (PlayerException | SQLException e) {
                throw new RuntimeException(e);
            }
        });
        item5.setOnAction(event -> {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/easv/gringofy/views/song-creator.fxml"));
            Parent root = null;
            try {
                root = loader.load();
                SongCreatorController controller = (SongCreatorController) loader.getController();
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);
                controller.setEditMode(true);
                controller.setSong(song);
                controller.setCurrentParameters();
                stage.setTitle("Song editor");
                stage.setResizable(false);
                stage.show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        });
        hoverItem.setOnMouseEntered(event -> playlistsMenu.show(hoverItem, Side.LEFT, -10, -8));

        // Show the context menu on left-click
        imageWrapper.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                songMenu.show(songImageView, event.getScreenX(), event.getScreenY());
            }
        });
        songImageWrapper.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                SongQueue.forcePlay(song);
                if (switchStateButton.getStyleClass().remove("play-button")) {
                    switchStateButton.getStyleClass().add("pause-button");
                }
            }
        });
        artistLabel.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/easv/gringofy/views/artist-view.fxml"));
                try {
                    Parent root = loader.load();
                    ArtistController controller = (ArtistController) loader.getController();
                    controller.setArtist(song.getArtist());
                    controller.changeSwitchStateButton();
                    Scene scene = new Scene(root);
                    Stage stage = (Stage) vbox.getScene().getWindow();
                    stage.setScene(scene);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
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
                    controller.changeSwitchStateButton();
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

    public HBox songToPlaylistSongNode(Song song, int index, MusicPlayer controller) {
        HBox hbox = new HBox();
        hbox.setAlignment(Pos.CENTER_LEFT);
        Label songIdLabel = new Label(String.valueOf(index));

        ImageView imageView = new ImageView();
        imageView.setFitWidth(35);
        imageView.setFitHeight(35);
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(DEFAULT_SONG_PICTURE)));
        imageView.setImage(image);
        VBox vbox = new VBox();
        vbox.setPrefWidth(150);
        Label titleLabel = new Label(song.getTitle());
        Label artistLabel = new Label(song.getArtist().getName());
        vbox.getChildren().addAll(titleLabel, artistLabel);

        VBox arrowsContainer = new VBox();
        arrowsContainer.setAlignment(Pos.CENTER);
        Image arrowUp = new Image(Objects.requireNonNull(getClass().getResourceAsStream(ARROW_UP_ICON)));
        Image arrowDown = new Image(Objects.requireNonNull(getClass().getResourceAsStream(ARROW_DOWN_ICON)));
        ImageView arrowUpImageView = new ImageView(arrowUp);
        ImageView arrowDownImageView = new ImageView(arrowDown);
        arrowUpImageView.setVisible(false);
        arrowDownImageView.setVisible(false);
        arrowUpImageView.setFitWidth(10);
        arrowUpImageView.setFitHeight(10);
        arrowDownImageView.setFitWidth(10);
        arrowDownImageView.setFitHeight(10);
        arrowsContainer.getChildren().addAll(arrowUpImageView, arrowDownImageView);

        Label releasedDateLabel = new Label(song.getReleaseDate());
        Label durationLabel = new Label(formatTime(song.getDuration()));


        HBox optionsImageContainer = new HBox();
        optionsImageContainer.setPrefHeight(35);
        optionsImageContainer.setPrefWidth(35);
        Image optionsImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream(OPTIONS_PICTURE)));
        ImageView optionsImageView = new ImageView(optionsImage);
        optionsImageView.setFitHeight(25);
        optionsImageView.setFitWidth(25);
        optionsImageContainer.getChildren().add(optionsImageView);
        optionsImageContainer.setAlignment(Pos.CENTER);

        ContextMenu songMenu = new ContextMenu();

        Label hoverItem = new Label("Add to playlist");
        CustomMenuItem item1 = new CustomMenuItem(hoverItem);
        MenuItem item2 = new MenuItem("Add to favorites");
        MenuItem item3 = new MenuItem("Delete from playlist");
        MenuItem item4 = new MenuItem("Add to queue");

        ContextMenu playlistsMenu = new ContextMenu();
        addPlaylists(song, playlistsMenu);

        songMenu.getItems().addAll(item1, item2, item3, item4);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        hbox.getStyleClass().add("playlist-song-node");
        arrowsContainer.getStyleClass().add("arrows-container");
        arrowUpImageView.getStyleClass().add("arrow-icon");
        arrowDownImageView.getStyleClass().add("arrow-icon");
        releasedDateLabel.getStyleClass().add("song-released-date-label");
        songIdLabel.getStyleClass().add("song-id-label");
        artistLabel.getStyleClass().add("song-artist-label");
        durationLabel.getStyleClass().add("song-duration-label");
        optionsImageView.getStyleClass().add("song-options-image-view");
        HBox.setMargin(durationLabel, new Insets(0, 20, 0, 85));
        if(controller instanceof PlaylistController) {
            hbox.getChildren().addAll(songIdLabel, imageView, vbox, arrowsContainer, spacer, releasedDateLabel, durationLabel, optionsImageContainer);
        }
        else{
            hbox.getChildren().addAll(songIdLabel, imageView, vbox, spacer, releasedDateLabel, durationLabel, optionsImageContainer);
        }

        optionsImageContainer.setOnMouseClicked(event -> songMenu.show(optionsImageView, event.getScreenX(), event.getScreenY()));

        hoverItem.setOnMouseEntered(event -> playlistsMenu.show(hoverItem, Side.LEFT, -10, -8));
        item3.setOnAction(event -> {
            try {
                PlaylistSong playlistSong = new PlaylistSong(song.getPlaylistSongId());
                playlistManager.removePlaylistSong(playlistSong);
                VBox parent = (VBox) hbox.getParent();
                parent.getChildren().remove(hbox);
            } catch (PlayerException | SQLException e) {
                throw new RuntimeException(e);
            }
        });
        item4.setOnAction(event -> {
            SongQueue.addSong(song);
        });
        arrowUpImageView.setOnMouseClicked(event -> {
            assert controller instanceof PlaylistController;
            PlaylistController playlistController = (PlaylistController) controller;
            try {
                playlistController.moveUpwards(song);
            } catch (PlayerException | SQLException e) {
                throw new RuntimeException(e);
            }
        });
        arrowDownImageView.setOnMouseClicked(event -> {
            assert controller instanceof PlaylistController;
            PlaylistController playlistController = (PlaylistController) controller;
            try {
                playlistController.moveDownwards(song);
            } catch (PlayerException | SQLException e) {
                throw new RuntimeException(e);
            }
        });
        hbox.setOnMouseEntered(event -> {
            if(controller instanceof PlaylistController && controller.getCurrentSortingMethod() == DEFAULT_SORTING) {
                arrowDownImageView.setVisible(true);
                arrowUpImageView.setVisible(true);
            }
        });
        hbox.setOnMouseExited(event -> {
            if(controller instanceof PlaylistController) {
                arrowDownImageView.setVisible(false);
                arrowUpImageView.setVisible(false);
            }
        });
        return hbox;
    }

    public String formatTime(int totalSeconds) {
        int minutes = totalSeconds / 60;
        int seconds = totalSeconds % 60;
        return String.format("%d:%02d", minutes, seconds);
    }

    private void addPlaylists(Song song, ContextMenu contextMenu) {
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
            contextMenu.getItems().add(menuItem);
        });
    }
}