package com.easv.gringofy.dal.db;

import com.easv.gringofy.be.Album;
import com.easv.gringofy.be.Playlist;
import com.easv.gringofy.be.Song;
import com.easv.gringofy.exceptions.PlayerException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PlaylistDAODB {
    public List<Playlist> getAllPlaylistsByInput(String input) throws SQLException {
        QueryBuilder queryBuilder = new QueryBuilder();
        List<Playlist> playlists = new ArrayList<Playlist>();
        Playlist playlist = null;

        ResultSet resultSet = queryBuilder
                .from("playlists")
                .select("*")
                .where("title", "LIKE", "%" + input + "%")
                .get();
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            playlist = mapModel(resultSet, id);
            playlists.add(playlist);
        }
        return playlists;
    }
    public List<Playlist> getAllPlaylists() throws SQLException {
        QueryBuilder queryBuilder = new QueryBuilder();
        List<Playlist> playlists = new ArrayList<Playlist>();
        Playlist playlist = null;

        ResultSet resultSet = queryBuilder
                .from("playlists")
                .select("*")
                .get();
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            playlist = mapModel(resultSet, id);
            playlists.add(playlist);
        }
        return playlists;
    }
    private Playlist mapModel(ResultSet resultSet, int id) throws SQLException {
        String title = resultSet.getString("title");
        String description = resultSet.getString("description");
        return new Playlist(id, title, description);
    }
    public void insert(Playlist playlist) throws PlayerException {
        QueryBuilder queryBuilder = new QueryBuilder();
        queryBuilder
                .table("playlists")
                .insert("title", playlist.getTitle())
                .insert("description", playlist.getDescription())
                .insert("created_at", playlist.getCreatedAt())
                .insert("updated_at", playlist.getUpdatedAt())
                .save();
    }
}
