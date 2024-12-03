package com.easv.gringofy.dal.db;

import com.easv.gringofy.be.Album;
import com.easv.gringofy.exceptions.PlayerException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AlbumDAODB {

    public List<Album> getAllAlbums() throws PlayerException, SQLException {
        QueryBuilder queryBuilder = new QueryBuilder();
        List<Album> albums = new ArrayList<>();
        Album album = null;

        ResultSet resultSet = queryBuilder
                .from("albums")
                .select("*")
                .get();

        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            album = mapModel(resultSet, id);
            albums.add(album);
        }

        return albums;
    }

    public Album get(int id) throws PlayerException, SQLException {
        QueryBuilder queryBuilder = new QueryBuilder();
        Album album = null;
        ResultSet resultSet = queryBuilder
                .from("albums")
                .select("*")
                .where("id", "=", id)
                .get();

        while (resultSet.next()) {
            album = mapModel(resultSet, id);
        }

        return album;
    }

    public List<Album> getAllAlbumsByInput(String input) throws SQLException {
        QueryBuilder queryBuilder = new QueryBuilder();
        List<Album> albums = new ArrayList<>();
        Album album = null;

        ResultSet resultSet = queryBuilder
                .from("albums")
                .select("*")
                .where("albums.title", "LIKE", '%' + input + '%')
                .get();

        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            album = mapModel(resultSet, id);
            albums.add(album);
        }

        return albums;
    }

    public Album mapModel(ResultSet resultSet, int id) throws SQLException {
        String title = resultSet.getString("title");
        String description = resultSet.getString("description");
        Album album = new Album(id, title, description);

        return album;
    }
}
