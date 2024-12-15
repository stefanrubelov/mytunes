package com.easv.gringofy.gui.models;

import com.easv.gringofy.be.*;
import com.easv.gringofy.bll.*;
import com.easv.gringofy.exceptions.PlayerException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PlayerModel {

    private final SongManager songManager = new SongManager();
    private final PlaylistManager playlistManager = new PlaylistManager();
    private final AlbumManager albumManager = new AlbumManager();
    private final SongPlayer songPlayer = new SongPlayer();
    private final ArtistManager artistManager = new ArtistManager();

    private static List<Song> defaultSongs = new ArrayList<>();
    private static List<Playlist> defaultPlaylists = new ArrayList<>();
    private static List<Album> defaultAlbums = new ArrayList<>();
    private static List<Artist> defaultArtists = new ArrayList<>();

    public void loadDefaultSongs() throws PlayerException, SQLException {
        defaultSongs = songManager.getAllSongs();
    }

    public void loadDefaultPlaylists() throws PlayerException, SQLException {
        defaultPlaylists = playlistManager.getAllPlaylists();
    }

    public void loadDefaultAlbums() throws PlayerException, SQLException {
        defaultAlbums = albumManager.getAllAlbums();
    }
    public void loadDefaultArtists() throws SQLException {
        defaultArtists = artistManager.getAllArtists();
    }
    public List<Song> getDefaultSongs() {
        return defaultSongs;
    }

    public List<Playlist> getDefaultPlaylists() {
        return defaultPlaylists;
    }

    public List<Album> getDefaultAlbums() {
        return defaultAlbums;
    }
    public List<Artist> getDefaultArtists() {return defaultArtists;}
    public List<Song> getAllSongsByInput(String input) throws PlayerException, SQLException {
        return songManager.getAllSongsByInput(input);
    }

    public List<Playlist> getAllPlaylistsByInput(String input) throws PlayerException, SQLException {
        return playlistManager.getAllPlaylistsByInput(input);
    }

    public List<Album> getAllAlbumsByInput(String input) throws PlayerException {
        return albumManager.getAllAlbumsByInput(input);
    }

    public void addSongToQueue(Song song) {
        songPlayer.addSongToQueue(song);
    }



}
