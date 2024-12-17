package com.easv.gringofy.dal.db;

import com.easv.gringofy.be.Genre;
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
        queryBuilder
                .table("genres")
                .where("id", "=", genre.getId())
                .set("title", genre.getTitle())
                .update();
    }

    public void insert(Genre genre) throws PlayerException {
        queryBuilder
                .table("genres")
                .insert("title", genre.getTitle())
                .save();
    }

    public Genre mapModel(ResultSet resultSet, int id) throws SQLException {
        String title = resultSet.getString("title");
        LocalDateTime createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
        LocalDateTime updatedAt = resultSet.getTimestamp("updated_at").toLocalDateTime();
        return new Genre(id, title, createdAt, updatedAt);
    }
}