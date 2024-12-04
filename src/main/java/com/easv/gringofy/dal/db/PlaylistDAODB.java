package com.easv.gringofy.dal.db;

import com.easv.gringofy.be.Album;
import com.easv.gringofy.be.Playlist;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
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

    public void delete(Playlist playlist) throws SQLException {
        QueryBuilder queryBuilder = new QueryBuilder();
        queryBuilder
                .from("playlists")
                .where("id", "=", playlist.getId())
                .delete();
    }

    public void removeSong(PlaylistSong song) throws SQLException {
        QueryBuilder queryBuilder = new QueryBuilder();
        queryBuilder
                .from("playlist_song")
                .where("id", "=", song.getId())
                .delete();
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
    public void update(Playlist playlist) throws PlayerException {
        QueryBuilder queryBuilder = new QueryBuilder();
        queryBuilder
                .table("playlists")
                .where("id", "=", playlist.getId())
                .set("title", playlist.getTitle())
                .set("description", playlist.getDescription())
                .set("updated_at", LocalDateTime.now())
                .update();
    }
    public void delete(Playlist playlist) throws PlayerException {
        QueryBuilder queryBuilder = new QueryBuilder();
        queryBuilder
                .from("playlists")
                .where("id", "=", playlist.getId())
                .delete();
    }
//    public static void main (String[] args) throws SQLException {
//        PlaylistDAODB dao = new PlaylistDAODB();
//        dao.update();
//    }
}
