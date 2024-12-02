package com.easv.gringofy.gui;

import com.easv.gringofy.be.Album;
import com.easv.gringofy.be.Playlist;
import com.easv.gringofy.be.Song;
import com.easv.gringofy.gui.models.PlayerModel;
import javafx.geometry.Pos;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

import java.util.Objects;

public class NodeBuilder {
    private static final String DEFAULT_SONG_PICTURE = "/com/easv/gringofy/images/defaultSongPicture.png";
    private static final String OPTIONS_PICTURE = "/com/easv/gringofy/images/tripleDots.png";
    private static final String DEFAULT_PLAYLIST_PICTURE = "/com/easv/gringofy/images/logo.png";
    private static final String DEFAULT_ALBUM_PICTURE = "/com/easv/gringofy/images/defaultAlbumPicture.png";

    public HBox songToNode(Song song) {
        HBox hbox = new HBox();
        hbox.getStyleClass().add("song-node");
        hbox.setAlignment(Pos.CENTER_LEFT);

        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(DEFAULT_SONG_PICTURE)));
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(35);
        imageView.setFitHeight(35);
        Image optionsImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream(OPTIONS_PICTURE)));
        ImageView optionsImageView = new ImageView(optionsImage);
        optionsImageView.setFitHeight(25);
        optionsImageView.setFitWidth(25);
        HBox imageWrapper = new HBox();
        imageWrapper.prefWidth(35);
        imageWrapper.prefHeight(35);
        imageWrapper.getChildren().add(optionsImageView);
        imageWrapper.setAlignment(Pos.CENTER);
        // Clip the ImageView to a rounded rectangle, so that image is not square (cannot use border-radius or background radius)
        Rectangle clip = new Rectangle(35, 35);
        clip.setArcWidth(10);
        clip.setArcHeight(10);
        imageView.setClip(clip);

        // VBox for text (title and artist name)
        VBox vbox = new VBox();
        Label titleLabel = new Label(song.getTitle());
        Label artistLabel = new Label(song.getArtist().getName());

        titleLabel.getStyleClass().add("song-node-title");
        vbox.getStyleClass().add("song-node-text");
        artistLabel.getStyleClass().add("song-node-artist");
        optionsImageView.getStyleClass().add("song-node-options");

        vbox.getChildren().addAll(titleLabel, artistLabel);
        hbox.getChildren().addAll(imageView, vbox, imageWrapper);

        ContextMenu contextMenu = new ContextMenu();
        // Add menu items
        MenuItem item1 = new MenuItem("Add to playlist");
        MenuItem item2 = new MenuItem("Add to favorites");
        MenuItem item3 = new MenuItem("Add to queue");
        contextMenu.getItems().addAll(item1, item2, item3);

        // Set actions for menu items
        item1.setOnAction(event -> System.out.println("Option 1 selected"));
        item2.setOnAction(event -> System.out.println("Option 2 selected"));
        item3.setOnAction(event -> System.out.println("Option 3 selected"));

        // Show the context menu on left-click
        imageWrapper.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                contextMenu.show(imageView, event.getScreenX(), event.getScreenY());
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