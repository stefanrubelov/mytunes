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

public class SongDAODB {
    QueryBuilder queryBuilder = new QueryBuilder();
    GenreDAODB genreData = new GenreDAODB();
    ArtistDAODB artistData = new ArtistDAODB();

    public List<Song> getAllSongs() throws PlayerException, SQLException {
        QueryBuilder queryBuilder = new QueryBuilder();
        List<Song> songs = new ArrayList<>();
        Song song = null;
        long queryStart = System.currentTimeMillis();
        ResultSet resultSet = queryBuilder
                .from("songs")
                .select("songs.*, artists.name AS artist_name, artists.description AS artist_description, genres.title AS genre_title")
                .join("artists", "songs.artist_id = artists.id", "")
                .join("genres", "songs.genre_id = genres.id", "")
                .get();
        long queryEnd = System.currentTimeMillis();
        System.out.println("Query Execution Time: " + (queryEnd - queryStart) + "ms");
        long mappingStart = System.currentTimeMillis();
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            song = mapModel(resultSet, id);
            songs.add(song);
        }
        long mappingEnd = System.currentTimeMillis();
        System.out.println("Mapping Time: " + (mappingEnd - mappingStart) + "ms");
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
                .where("songs.id = ?", id)
                .get();

        if (resultSet.next()) {
            song = mapModel(resultSet, id);
        }
        return song;
    }

    public void delete(int id) throws PlayerException {
        queryBuilder
                .from("songs")
                .where("id = ?", id)
                .delete();
    }

    public void update(Song song) throws PlayerException {
        queryBuilder
                .table("songs")
                .where("id = ?", song.getId())
                .set("title", song.getTitle())
                .set("release_date", song.getReleaseDate())
                .set("duration", song.getDuration())
                .set("artist_id", song.getArtist().getId())
                .set("genre_id", song.getGenre().getId())
                .set("updated_at", LocalDateTime.now())
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
                .save();
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
                    .where("songs.title LIKE ?", '%' + input + '%')
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

    public Song mapModel(ResultSet resultSet, int id) throws PlayerException, SQLException {
        int duration = resultSet.getInt("duration");
        String title = resultSet.getString("title");
        Artist artist = new Artist(resultSet.getInt("artist_id"), resultSet.getString("artist_name"), resultSet.getString("artist_description"));
        String releaseDate = resultSet.getString("release_date");
        Genre genre = new Genre(resultSet.getInt("genre_id"), resultSet.getString("genre_title"));
        String path = "";
        LocalDateTime createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
        LocalDateTime updatedAt = resultSet.getTimestamp("updated_at").toLocalDateTime();
        Song song = new Song(id, duration, genre, title, artist, releaseDate, path, createdAt, updatedAt);

        return song;
    }

    public static void main(String[] args) throws SQLException, PlayerException {
        SongDAODB dao = new SongDAODB();
        List<Song> songs = dao.getAllSongs();
    }
}
