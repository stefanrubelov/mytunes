package com.easv.gringofy.dal.db;

import com.easv.gringofy.be.Artist;
import com.easv.gringofy.exceptions.PlayerException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ArtistDAODB {
    private QueryBuilder queryBuilder;

    public ArtistDAODB() {
        this.queryBuilder = new QueryBuilder();
    }

    public List<Artist> getAll() throws SQLException {
        QueryBuilder queryBuilder = new QueryBuilder();
        List<Artist> artists = new ArrayList<>();
        Artist artist = null;

        ResultSet resultSet = queryBuilder
                .select("*")
                .from("artists")
                .get();

        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            artist = mapModel(resultSet, id);
            artists.add(artist);
        }

        return artists;
    }

    public Artist get(int id) throws SQLException {
        QueryBuilder queryBuilder = new QueryBuilder();
        Artist artist = null;

        ResultSet resultSet = queryBuilder
                .select("*")
                .from("artists")
                .where("id = ?", id)
                .get();

        if (resultSet.next()) {;
            artist = mapModel(resultSet, id);
        }

        return artist;
    }

    public void update(Artist artist) throws PlayerException {
        QueryBuilder queryBuilder = new QueryBuilder();
        queryBuilder
                .table("artists")
                .where("id = ?", artist.getId())
                .set("name", artist.getName())
                .set("description", artist.getDescription())
                .set("updated_at", LocalDateTime.now())
                .update();
    }

    public void insert(Artist artist) throws PlayerException {
        QueryBuilder queryBuilder = new QueryBuilder();
        queryBuilder
                .table("artists")
                .insert("name", artist.getName())
                .insert("description", artist.getDescription())
                .save();
    }

    public void delete(int id) throws PlayerException {
        QueryBuilder queryBuilder = new QueryBuilder();
        queryBuilder
                .from("artists")
                .where("id = ?", id)
                .delete();
    }

    private Artist mapModel(ResultSet resultSet, int id) throws SQLException {
        String name = resultSet.getString("name");
        String description = resultSet.getString("description");
        LocalDateTime createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
        LocalDateTime updatedAt = resultSet.getTimestamp("updated_at").toLocalDateTime();
        return new Artist(id, name, description, createdAt, updatedAt);
    }
}