package com.easv.gringofy.gui.models;

import com.easv.gringofy.be.*;
import com.easv.gringofy.bll.*;
import com.easv.gringofy.exceptions.PlayerException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PlayerModel {

    private final SongManager songManager = new SongManager();
    private final PlaylistManager playlistManager = new PlaylistManager();
    private final AlbumManager albumManager = new AlbumManager();
    private final ArtistManager artistManager = new ArtistManager();
    private final GenreManager genreManager = new GenreManager();

    private static List<Song> defaultSongs = new ArrayList<>();
    private static List<Playlist> defaultPlaylists = new ArrayList<>();
    private static List<Album> defaultAlbums = new ArrayList<>();
    private static List<Artist> defaultArtists = new ArrayList<>();
    private static List<Genre> defaultGenres = new ArrayList<>();

    public void loadDefaultSongs() throws PlayerException, SQLException {
        defaultSongs = songManager.getAllSongs();
    }

    public void loadDefaultPlaylists() throws SQLException {
        defaultPlaylists = playlistManager.getAllPlaylists();
    }

    public void loadDefaultAlbums() throws PlayerException, SQLException {
        defaultAlbums = albumManager.getAllAlbums();
    }

    public void loadDefaultArtists() throws SQLException {
        defaultArtists = artistManager.getAllArtists();
    }

    public void loadDefaultGenres() throws PlayerException, SQLException {
        defaultGenres = genreManager.getAllGenres();
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

    public List<Artist> getDefaultArtists() {
        return defaultArtists;
    }

    public List<Genre> getDefaultGenres() {
        return defaultGenres;
    }

    public List<Song> getAllSongsByInput(String input) throws PlayerException, SQLException {
        return songManager.getAllSongsByInput(input);
    }

    public List<Playlist> getAllPlaylistsByInput(String input) throws PlayerException, SQLException {
        return playlistManager.getAllPlaylistsByInput(input);
    }

    public List<Album> getAllAlbumsByInput(String input) throws PlayerException {
        return albumManager.getAllAlbumsByInput(input);
    }


}
