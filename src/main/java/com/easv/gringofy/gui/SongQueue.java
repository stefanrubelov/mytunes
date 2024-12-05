package com.easv.gringofy.gui;

import com.easv.gringofy.be.Song;
import javafx.event.ActionEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class SongQueue {

    private static final List<Song> songQueue = new LinkedList<>();

    public static void playPreviousSong(ActionEvent actionEvent) {

    }

    public static void playCurrentSong(ActionEvent actionEvent) {

    }

    public static void playNextSong(ActionEvent actionEvent) {

    }
    public static void start(ActionEvent actionEvent) {
        String filepath = songQueue.getFirst().getFilePath();
        Media media = new Media(new File(filepath).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
    }

    public static void addSong(Song song) {
        songQueue.add(song);
    }
}
