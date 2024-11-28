package com.easv.gringofy.bll;

import com.easv.gringofy.be.Song;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class SongPlayer {
    private final Queue<Song> songQueue = new LinkedList<>();
    private MediaPlayer mediaPlayer;

    public void addSongToQueue(Song song) {
        songQueue.add(song);
    }

    public void addMultipleSongsToQueue(List<Song> songs) {
        songQueue.addAll(songs);
    }

    public void playSong(String filePath) {
        // Stop the current song if playing
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }

        String uri = Paths.get(filePath).toUri().toString();
        Media media = new Media(uri);
        mediaPlayer = new MediaPlayer(media);
        // Play the next song after the current song ends
        mediaPlayer.setOnEndOfMedia(this::playNext);
        mediaPlayer.play();
    }

    public void playNext() {
        if (!songQueue.isEmpty()) {
            Song song = songQueue.poll();
            String filePath = song.getFilePath(); // Get and remove the next song
            playSong(filePath);
        }
    }
}
