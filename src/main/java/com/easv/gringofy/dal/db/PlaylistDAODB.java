package com.easv.gringofy.dal.db;

import com.easv.gringofy.be.Playlist;
import com.easv.gringofy.be.PlaylistSong;
import com.easv.gringofy.be.Song;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PlaylistDAODB {

    public List<Playlist> getAllPlaylists() throws SQLException {
        QueryBuilder queryBuilder = new QueryBuilder();
        List<Playlist> playlists = new ArrayList<Playlist>();

        ResultSet resultSet = queryBuilder
                .from("playlists")
                .select("*")
                .get();

        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            Playlist playlist = mapModel(resultSet, id);
            playlists.add(playlist);
        }

        return playlists;
    }

    public List<Playlist> getAllPlaylistsByInput(String input) throws SQLException {
        QueryBuilder queryBuilder = new QueryBuilder();
        List<Playlist> playlists = new ArrayList<Playlist>();

        ResultSet resultSet = queryBuilder
                .from("playlists")
                .select("*")
                .where("title", "LIKE", "%" + input + "%")
                .get();

        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            Playlist playlist = mapModel(resultSet, id);
            playlists.add(playlist);
        }

        return playlists;
    }

    public void addSong(Playlist playlist, Song song) throws SQLException {
        QueryBuilder queryBuilder = new QueryBuilder();
        queryBuilder
                .table("playlist_song")
                .insert("playlist_id", playlist.getId())
                .insert("song_id", song.getId())
                .insert("position", this.getLargestPosition(playlist) + 1)
                .save();
    }

    public void removeSong(PlaylistSong playlistSong) {
        QueryBuilder queryBuilder = new QueryBuilder();

        queryBuilder
                .from("playlist_song")
                .where("id", "=", playlistSong.getId())
                .delete();

        this.updateOrder(playlistSong);
    }
    private void updateOrder(PlaylistSong playlistSong) {
        QueryBuilder queryBuilder = new QueryBuilder();

        queryBuilder
                .table("playlist_song")
                .set("position", "position - 1", true)
                .where("playlist_id", "=", playlistSong.getPlaylistId())
                .where("position", ">", playlistSong.getPosition())
                .update();
    }
    private int getLargestPosition(Playlist playlist) throws SQLException {
        QueryBuilder queryBuilder = new QueryBuilder();

        ResultSet resultSet = queryBuilder
                .from("playlist_song")
                .select("MAX(position) AS largest_position")
                .where("playlist_id", "=", playlist.getId())
                .get();

        if (resultSet.next()) {
            return resultSet.getInt("largest_position");
        }

        return 0;
    }

    public void incrementPosition(PlaylistSong playlistSong) {
        QueryBuilder queryBuilder = new QueryBuilder();

        queryBuilder
                .table("playlist_song")
                .set("position", "position + 1", true)
                .where("id", "=", playlistSong.getId())
                .update();
    }

    public void decrementPosition(PlaylistSong playlistSong) {
        QueryBuilder queryBuilder = new QueryBuilder();

        queryBuilder
                .table("playlist_song")
                .set("position", "position - 1", true)
                .where("id", "=", playlistSong.getId())
                .update();
    }

    public void insert(Playlist playlist) {
        QueryBuilder queryBuilder = new QueryBuilder();
        queryBuilder
                .table("playlists")
                .insert("title", playlist.getTitle())
                .insert("description", playlist.getDescription())
                .insert("created_at", playlist.getCreatedAt())
                .insert("updated_at", playlist.getUpdatedAt())
                .save();
    }

    public void update(Playlist playlist) {
        QueryBuilder queryBuilder = new QueryBuilder();
        queryBuilder
                .table("playlists")
                .where("id", "=", playlist.getId())
                .set("title", playlist.getTitle())
                .set("description", playlist.getDescription())
                .update();
    }

    public void delete(Playlist playlist) {
        QueryBuilder queryBuilder = new QueryBuilder();

        queryBuilder
                .from("playlists")
                .where("id", "=", playlist.getId())
                .delete();
    }

    private Playlist mapModel(ResultSet resultSet, int id) throws SQLException {
        String title = resultSet.getString("title");
        String description = resultSet.getString("description");
        return new Playlist(id, title, description);
    }
}
