package com.easv.gringofy.bll;

import com.easv.gringofy.be.Song;
import javafx.application.Platform;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

public class SongQueue {

    private static final List<Song> songQueue = new LinkedList<>();
    private static MediaPlayer mediaPlayer;
    private static int currentIndex = 0;
    private static final SimpleDoubleProperty progressProperty = new SimpleDoubleProperty();
    private final SongManager songManager = new SongManager();

    public static SimpleDoubleProperty getProgressProperty() {
        return progressProperty;
    }
    private static void updateProgressBar() {
        Platform.runLater(() -> {
            double currentTime = mediaPlayer.getCurrentTime().toSeconds();
            double totalTime = mediaPlayer.getTotalDuration().toSeconds();
            double progress = currentTime / totalTime;
            progressProperty.set(progress);
        });
    }
    public static boolean playPreviousSong() {
        if (currentIndex > 0) {
            currentIndex--;
            if (mediaPlayer != null) mediaPlayer.stop();
            Song song = songQueue.get(currentIndex);
            playCurrentSong(song);
            return true;
        }
        return false;
    }

    public static void playCurrentSong(Song song) {
        File file = new File(song.getFilePath());
        Media media = new Media(file.toURI().toString());
        mediaPlayer = new MediaPlayer(media);

        mediaPlayer.currentTimeProperty().addListener((_, _, _) -> updateProgressBar());

        mediaPlayer.setOnEndOfMedia(() -> {
            currentIndex++;
            if(songQueue.size() > currentIndex) {
                playCurrentSong(songQueue.get(currentIndex));
            }
        });
        mediaPlayer.play();

    }

    public static boolean playNextSong() {
        if (currentIndex < songQueue.size() - 1) {
            currentIndex++;
            if (mediaPlayer != null) mediaPlayer.stop();
            Song song = songQueue.get(currentIndex);
            playCurrentSong(song);
            return true;
        }
        return false;
    }

    public static void forcePlay(Song song) {
        if (mediaPlayer != null) mediaPlayer.stop();
        songQueue.clear();
        songQueue.add(song);
        File file = new File(song.getFilePath());
        Media media = new Media(file.toURI().toString());
        mediaPlayer = new MediaPlayer(media);

        mediaPlayer.currentTimeProperty().addListener((_, _, _) -> updateProgressBar());
        mediaPlayer.play();
    }

    public static boolean switchState() {
        if (mediaPlayer != null) {
            if (mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
                mediaPlayer.pause();
            } else {
                mediaPlayer.play();
            }
            return true;
        }
        if (!songQueue.isEmpty()) {
            playCurrentSong(songQueue.get(currentIndex));
            return true;
        }
        return false;
    }

    public static boolean getState() {
        return mediaPlayer != null && mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING;
    }

    public static void addSong(Song song) {
        songQueue.add(song);
    }

    public static void setVolume(Number newValue) {
        mediaPlayer.setVolume(newValue.doubleValue());
    }
}
