package com.easv.gringofy.dal.db;

import com.easv.gringofy.be.Artist;
import com.easv.gringofy.be.Genre;
import com.easv.gringofy.be.Song;
import com.easv.gringofy.exceptions.PlayerException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SongDAODB {
    QueryBuilder queryBuilder = new QueryBuilder();
    GenreDAODB genreData = new GenreDAODB();
    ArtistDAODB artistData = new ArtistDAODB();

    public List<Song> getAllSongs() throws PlayerException, SQLException {
        QueryBuilder queryBuilder = new QueryBuilder();
        List<Song> songs = new ArrayList<>();
        Song song = null;
        ResultSet resultSet = queryBuilder
                .from("songs")
                .select("songs.*, artists.name AS artist_name, artists.description AS artist_description, genres.title AS genre_title")
                .join("artists", "songs.artist_id = artists.id", "")
                .join("genres", "songs.genre_id = genres.id", "")
                .get();
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            song = mapModel(resultSet, id);
            songs.add(song);
        }
        return songs;
    }

    public Song get(int id) throws PlayerException, SQLException {
        QueryBuilder queryBuilder = new QueryBuilder();
        Song song = null;

        ResultSet resultSet = queryBuilder
                .from("songs")
                .select("songs.*, artists.name AS artist_name, artists.description AS artist_description, genres.title AS genre_title")
                .join("artists", "songs.artist_id = artists.id", "")
                .join("genres", "songs.genre_id = genres.id", "")
                .where("songs.id", "=", id)
                .get();

        if (resultSet.next()) {
            song = mapModel(resultSet, id);
        }
        return song;
    }

    public void delete(int id) throws PlayerException {
        QueryBuilder queryBuilder = new QueryBuilder();
        queryBuilder
                .from("songs")
                .where("id", "=", id)
                .delete();
    }

    public void update(Song song) throws PlayerException {
        queryBuilder
                .table("songs")
                .where("id", "=", song.getId())
                .set("title", song.getTitle())
                .set("release_date", song.getReleaseDate())
                .set("duration", song.getDuration())
                .set("artist_id", song.getArtist().getId())
                .set("genre_id", song.getGenre().getId())
                .update();
    }

    public void insert(Song song) throws PlayerException {
        queryBuilder
                .table("songs")
                .insert("title", song.getTitle())
                .insert("release_date", song.getReleaseDate())
                .insert("duration", song.getDuration())
                .insert("artist_id", song.getArtist().getId())
                .insert("genre_id", song.getGenre().getId())
                .insert("path", song.getFilePath())
                .save();
    }

    public List<Song> getAllSongsByAlbum(int album_id) throws SQLException {
        List<Song> songs = new ArrayList<>();
        ResultSet resultSet = queryBuilder
                .select("songs.id")
                .select("songs.duration")
                .select("songs.title")
                .select("songs.release_date")
                .select("songs.created_at")
                .select("songs.updated_at")
                .select("songs.path")
                .select("artists.id AS artist_id")
                .select("artists.name AS artist_name")
                .select("artists.description AS artist_description")
                .select("genres.id AS genre_id")
                .select("genres.title AS genre_title")
                .select("album_song.id AS album_song_id")
                .from("album_song")
                .join("songs", "album_song.song_id = songs.id", "")
                .join("artists", "songs.artist_id = artists.id", "")
                .join("genres", "songs.genre_id = genres.id", "")
                .where("album_song.album_id", "=", album_id)
                .orderBy("album_song.position", "asc")
                .get();

        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            Song song = mapModel(resultSet, id);
            song.setAlbumSongId(resultSet.getInt("album_song_id"));
            songs.add(song);
        }

        return songs;
    }

    public List<Song> getAllSongsByPlaylist(int playlist_id) throws SQLException {
        List<Song> songs = new ArrayList<>();
        ResultSet resultSet = queryBuilder
                .select("songs.id")
                .select("songs.duration")
                .select("songs.title")
                .select("songs.release_date")
                .select("songs.created_at")
                .select("songs.updated_at")
                .select("songs.path")
                .select("artists.id AS artist_id")
                .select("artists.name AS artist_name")
                .select("artists.description AS artist_description")
                .select("genres.id AS genre_id")
                .select("genres.title AS genre_title")
                .select("playlist_song.id AS playlist_song_id")
                .from("playlist_song")
                .join("songs", "playlist_song.song_id = songs.id", "")
                .join("artists", "songs.artist_id = artists.id", "")
                .join("genres", "songs.genre_id = genres.id", "")
                .where("playlist_song.playlist_id", "=", playlist_id)
                .orderBy("playlist_song.position", "asc")
                .get();

        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            Song song = mapModel(resultSet, id);
            song.setPlaylistSongId(resultSet.getInt("playlist_song_id"));
            songs.add(song);
        }

        return songs;
    }
    public List<Song> getAllSongsByGenre(int genre_id) throws SQLException {
        List<Song> songs = new ArrayList<>();
        ResultSet resultSet = queryBuilder
                .select("songs.id")
                .select("songs.duration")
                .select("songs.title")
                .select("songs.release_date")
                .select("songs.created_at")
                .select("songs.updated_at")
                .select("songs.path")
                .select("artists.id AS artist_id")
                .select("artists.name AS artist_name")
                .select("artists.description AS artist_description")
                .select("genres.id AS genre_id")
                .select("genres.title AS genre_title")
                .select("genre_song.id AS genre_song_id")
                .from("genre_song")
                .join("songs", "genre_song.song_id = songs.id", "")
                .join("genres", "genre_song.genre_id = genres.id", "") // Corrected JOIN for genres
                .join("artists", "songs.artist_id = artists.id", "")   // Added JOIN for artists
                .where("genre_song.genre_id", "=", genre_id)
                .orderBy("genre_song.position", "asc")
                .get();

        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            Song song = mapModel(resultSet, id);
            song.setGenreSongId(resultSet.getInt("genre_song_id"));
            songs.add(song);
        }

        return songs;
    }
    public List<Song> getAllSongsByArtist(int artist_id) throws SQLException {
        List<Song> songs = new ArrayList<>();
        ResultSet resultSet = queryBuilder
                .select("songs.id")
                .select("songs.duration")
                .select("songs.title")
                .select("songs.release_date")
                .select("songs.created_at")
                .select("songs.updated_at")
                .select("songs.path")
                .select("artists.id AS artist_id")
                .select("artists.name AS artist_name")
                .select("artists.description AS artist_description")
                .select("genres.id AS genre_id")
                .select("genres.title AS genre_title")
                .select("artist_song.id AS artist_song_id")
                .from("artist_song")
                .join("songs", "artist_song.song_id = songs.id", "")
                .join("artists", "songs.artist_id = artists.id", "")
                .join("genres", "songs.genre_id = genres.id", "")
                .where("artist_song.artist_id", "=", artist_id)
                .orderBy("artist_song.position", "asc")
                .get();

        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            Song song = mapModel(resultSet, id);
            song.setArtistSongId(resultSet.getInt("artist_song_id"));
            songs.add(song);
        }

        return songs;
    }

    public List<Song> getAllSongsByInput(String input) throws PlayerException, SQLException {
        QueryBuilder queryBuilder = new QueryBuilder();
        List<Song> songs = new ArrayList<>();
        Song song = null;
        if (input.length() >= 3) {
            ResultSet resultSet = queryBuilder
                    .from("songs")
                    .select("songs.*, artists.name AS artist_name, artists.description AS artist_description, genres.title AS genre_title")
                    .join("artists", "songs.artist_id = artists.id", "")
                    .join("genres", "songs.genre_id = genres.id", "")
                    .where("songs.title", "LIKE", '%' + input + '%')
                    .get();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                song = mapModel(resultSet, id);
                songs.add(song);
            }
            return songs;
        }
        return null;
    }

    public Song mapModel(ResultSet resultSet, int id) throws SQLException {
        int duration = resultSet.getInt("duration");
        String title = resultSet.getString("title");
        Artist artist = new Artist(resultSet.getInt("artist_id"), resultSet.getString("artist_name"), resultSet.getString("artist_description"));
        Genre genre = new Genre(resultSet.getInt("genre_id"), resultSet.getString("genre_title"));
        String releaseDate = resultSet.getString("release_date");
        String path = resultSet.getString("path");
        LocalDateTime createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
        LocalDateTime updatedAt = resultSet.getTimestamp("updated_at").toLocalDateTime();

        return new Song(id, duration, genre, title, artist, releaseDate, path, createdAt, updatedAt);
    }
    public int getCurrentId() throws SQLException {
        QueryBuilder queryBuilder = new QueryBuilder();

        ResultSet resultSet = queryBuilder
                .from("songs")
                .select("MAX(id) AS current_id")
                .get();

        if (resultSet.next()) {
            return resultSet.getInt("current_id");
        }
        return 0;
    }

}
