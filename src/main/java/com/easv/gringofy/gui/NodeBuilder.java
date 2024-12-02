package com.easv.gringofy.gui;

import com.easv.gringofy.be.Album;
import com.easv.gringofy.be.Playlist;
import com.easv.gringofy.be.Song;
import com.easv.gringofy.gui.models.PlayerModel;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;

import java.util.Objects;

public class NodeBuilder {
    private static final String DEFAULT_SONG_PICTURE = "/com/easv/gringofy/images/defaultSongPicture.png";
    private static final String OPTIONS_PICTURE = "/com/easv/gringofy/images/tripleDots.png";
    private static final String DEFAULT_PLAYLIST_PICTURE = "/com/easv/gringofy/images/logo.png";
    private static final String DEFAULT_ALBUM_PICTURE = "/com/easv/gringofy/images/defaultAlbumPicture.png";
    private static final String PLAY_SONG_ICON = "/com/easv/gringofy/images/playSongIcon.png";
    private PlayerModel playerModel = new PlayerModel();

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
        hbox.getChildren().addAll(songImageWrapper, vbox,spacer,imageWrapper);

        // Add menu items
        ContextMenu songMenu = new ContextMenu();
        Label hoverItem = new Label("Add to playlist");
        CustomMenuItem item1 = new CustomMenuItem(hoverItem);
        MenuItem item2 = new MenuItem("Add to favorites");
        MenuItem item3 = new MenuItem("Add to queue");
        songMenu.getItems().addAll(item1, item2, item3);

        // Menu for the available playlists
        ContextMenu playlistsMenu = new ContextMenu();
        playlistsMenu.getItems().addAll(new MenuItem("TEST")); // Just a test item - remove later
//        List<Playlist> playlists= playlistManager.getAllPlaylists(); // Needs implementation of method
//        playlists.forEach(playlist -> {playlistsMenu.getItems().add(new MenuItem(playlist.toString()));}); // Needs implementation of method

        // Set actions for menu items
        //item2.setOnAction(event -> System.out.println("Add the song to the playlist")); // to implement
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
}