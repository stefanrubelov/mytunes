package com.easv.gringofy.dal.db;

import com.easv.gringofy.be.Artist;
import com.easv.gringofy.be.ArtistSong;
import com.easv.gringofy.be.Genre;
import com.easv.gringofy.be.GenreSong;
import com.easv.gringofy.exceptions.PlayerException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class GenreDAODB {
    private final QueryBuilder queryBuilder;

    public GenreDAODB() {
        this.queryBuilder = new QueryBuilder();
    }

    public List<Genre> getAllGenres() throws SQLException {
        QueryBuilder queryBuilder = new QueryBuilder();
        List<Genre> genres = new ArrayList<>();
        Genre genre;

        ResultSet resultSet = queryBuilder
                .select("*")
                .from("genres")
                .get();

        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            genre = mapModel(resultSet, id);
            genres.add(genre);
        }

        return genres;
    }

    public Genre get(int id) throws PlayerException, SQLException {
        QueryBuilder queryBuilder = new QueryBuilder();
        Genre genre = null;

        ResultSet resultSet = queryBuilder
                .select("*")
                .where("id", "=", id)
                .from("genres")
                .get();

        if (resultSet.next()) {
            genre = mapModel(resultSet, id);
        }

        return genre;
    }

    public void update(Genre genre) throws PlayerException {
        QueryBuilder queryBuilder = new QueryBuilder();
        queryBuilder
                .table("genres")
                .where("id", "=", genre.getId())
                .set("title", genre.getTitle())
                .set("path", genre.getPath())
                .set("updated_at", LocalDateTime.now())
                .update();
    }

    public void insert(Genre genre) throws PlayerException {
        QueryBuilder queryBuilder = new QueryBuilder();
        queryBuilder
                .table("genres")
                .insert("title", genre.getTitle())
                .insert("path", genre.getPath())
                .insert("updated_at", LocalDateTime.now())
                .insert("created_at", LocalDateTime.now())
                .save();
    }

    public Genre mapModel(ResultSet resultSet, int id) throws SQLException {
        String title = resultSet.getString("title");
        LocalDateTime createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
        LocalDateTime updatedAt = resultSet.getTimestamp("updated_at").toLocalDateTime();
        String path = resultSet.getString("path");
        return new Genre(id, title, path, createdAt, updatedAt);
    }

    public void delete(Genre genre) {
        QueryBuilder queryBuilder = new QueryBuilder();
        queryBuilder
                .table("genres")
                .where("id", "=", genre.getId())
                .delete();
    }

    public void addSong(Genre genre, int songId) throws SQLException {
        QueryBuilder queryBuilder = new QueryBuilder();
        queryBuilder
                .table("genre_song")
                .insert("genre_id", genre.getId())
                .insert("song_id", songId)
                .insert("position", this.getLargestPosition(genre) + 1)
                .save();
    }
    private int getLargestPosition(Genre genre) throws SQLException {
        QueryBuilder queryBuilder = new QueryBuilder();
        ResultSet resultSet = queryBuilder
                .from("genre_song")
                .select("MAX(position) AS largest_position")
                .where("genre_id", "=", genre.getId())
                .get();

        if (resultSet.next()) {
            return resultSet.getInt("largest_position");
        }

        return 0;
    }
    public void incrementPosition(GenreSong genreSong) {
        QueryBuilder queryBuilder = new QueryBuilder();

        queryBuilder
                .table("genre_song")
                .set("position", "position + 1", true)
                .where("id", "=", genreSong.getId())
                .update();
    }

    public void decrementPosition(GenreSong genreSong) {
        QueryBuilder queryBuilder = new QueryBuilder();

        queryBuilder
                .table("artist_song")
                .set("position", "position - 1", true)
                .where("id", "=", genreSong.getId())
                .update();
    }
}