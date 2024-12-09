package com.easv.gringofy.gui;

import com.easv.gringofy.be.Song;
import javafx.event.ActionEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

public class SongQueue {

    private static final List<Song> songQueue = new LinkedList<>();
    private static MediaPlayer mediaPlayer;
    private static int currentIndex = 0;

    public static void playPreviousSong() {
        if(currentIndex > 0) {
            currentIndex--;
            if(mediaPlayer!=null) mediaPlayer.stop();
            Song song = songQueue.get(currentIndex);
            playCurrentSong(song);
        }
    }

    public static void playCurrentSong(Song song) {
        File file = new File(song.getFilePath());
        Media media = new Media(file.toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setOnEndOfMedia(() -> {
            playCurrentSong(songQueue.get(currentIndex));
            currentIndex++;
        });
        mediaPlayer.play();

    }
    public static void playNextSong() {
        if(currentIndex < songQueue.size()-1) {
            currentIndex++;
            if(mediaPlayer!=null) mediaPlayer.stop();
            Song song = songQueue.get(currentIndex);
            playCurrentSong(song);
        }
    }
    public static void forcePlay(Song song) {
        if(mediaPlayer!=null) mediaPlayer.stop();
        File file = new File(song.getFilePath());
        Media media = new Media(file.toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
    }
    public static boolean switchState() {
        if(mediaPlayer != null) {
            if (mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
                mediaPlayer.pause();
            } else {
                mediaPlayer.play();
            }
            return true;
        }
        if(!songQueue.isEmpty()) {
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
    public static void main(String[] args) {
        switchState();
    }
}
