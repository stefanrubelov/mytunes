package com.easv.gringofy.dal.db;

import com.easv.gringofy.be.*;
import com.easv.gringofy.exceptions.PlayerException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AlbumDAODB {

    public List<Album> getAllAlbums() throws SQLException {
        QueryBuilder queryBuilder = new QueryBuilder();
        List<Album> albums = new ArrayList<>();

        ResultSet resultSet = queryBuilder
                .from("albums")
                .select("albums.id AS album_id")
                .select("albums.title AS album_title")
                .select("albums.description AS album_description")
                .select("albums.release_date AS album_release_date")
                .select("artists.id AS artist_id")
                .select("artists.name AS artist_name")
                .select("artists.description AS artist_description")
                .join("artists", "albums.artist_id = artists.id", "")
                .get();

        while (resultSet.next()) {
            int id = resultSet.getInt("album_id");
            Album album = mapModel(resultSet, id);
            albums.add(album);
        }

        return albums;
    }

    public Album get(int id) throws PlayerException, SQLException {
        QueryBuilder queryBuilder = new QueryBuilder();
        Album album = null;
        ResultSet resultSet = queryBuilder
                .from("albums")
                .select("albums.id AS album_id")
                .select("albums.title AS album_title")
                .select("albums.description AS album_description")
                .select("albums.release_date AS album_release_date")
                .select("artists.id AS artist_id")
                .select("artists.name AS artist_name")
                .select("artists.description AS artist_description")
                .join("artists", "albums.artist_id = artists.id", "")
                .where("albums.id", "=", id)
                .get();

        while (resultSet.next()) {
            album = mapModel(resultSet, id);
        }

        return album;
    }

    public List<Album> getAllAlbumsByInput(String input) throws SQLException {
        QueryBuilder queryBuilder = new QueryBuilder();
        List<Album> albums = new ArrayList<>();

        ResultSet resultSet = queryBuilder
                .from("albums")
                .select("albums.id AS album_id")
                .select("albums.title AS album_title")
                .select("albums.description AS album_description")
                .select("albums.release_date AS album_release_date")
                .select("artists.id AS artist_id")
                .select("artists.name AS artist_name")
                .select("artists.description AS artist_description")
                .join("artists", "albums.artist_id = artists.id", "")
                .where("albums.title", "LIKE", '%' + input + '%')
                .get();

        while (resultSet.next()) {
            int id = resultSet.getInt("album_id");
            Album album = mapModel(resultSet, id);
            albums.add(album);
        }

        return albums;
    }

    public void update(Album album) {
        QueryBuilder queryBuilder = new QueryBuilder();

        queryBuilder
                .table("albums")
                .where("id", "=", album.getId())
                .set("title", album.getTitle())
                .set("description", album.getDescription())
                .set("release_date", album.getReleaseDate())
                .set("artist_id", album.getArtist().getId())
                .update();
    }

    public void delete(Album album) {
        QueryBuilder queryBuilder = new QueryBuilder();

        queryBuilder
                .from("albums")
                .where("id", "=", album.getId())
                .delete();
    }

    public void addSong(Playlist playlist, Song song) throws SQLException {
        QueryBuilder queryBuilder = new QueryBuilder();

        queryBuilder
                .table("album_song")
                .insert("album_id", playlist.getId())
                .insert("song_id", song.getId())
                .insert("position", this.getLargestPosition(playlist) + 1)
                .save();
    }

    public void removeSong(AlbumSong albumSong) {
        QueryBuilder queryBuilder = new QueryBuilder();

        queryBuilder
                .from("album_song")
                .where("id", "=", albumSong.getId())
                .delete();

        this.updateOrder(albumSong);
    }

    private void updateOrder(AlbumSong album) {
        QueryBuilder queryBuilder = new QueryBuilder();

        queryBuilder
                .table("album_song")
                .set("position", "position - 1", true)
                .where("album_id", "=", album.getAlbumId())
                .where("position", ">", album.getPosition())
                .update();
    }

    private int getLargestPosition(Playlist playlist) throws SQLException {
        QueryBuilder queryBuilder = new QueryBuilder();

        ResultSet resultSet = queryBuilder
                .from("album_song")
                .select("MAX(position) AS largest_position")
                .where("album_id", "=", playlist.getId())
                .get();

        if (resultSet.next()) {
            return resultSet.getInt("largest_position");
        }

        return 0;
    }

    public void incrementPosition(AlbumSong album) {
        QueryBuilder queryBuilder = new QueryBuilder();

        queryBuilder
                .table("album_song")
                .set("position", "position + 1", true)
                .where("id", "=", album.getId())
                .update();
    }

    public void decrementPosition(AlbumSong album) {
        QueryBuilder queryBuilder = new QueryBuilder();

        queryBuilder
                .table("album_song")
                .set("position", "position - 1", true)
                .where("id", "=", album.getId())
                .update();
    }

    public Album mapModel(ResultSet resultSet, int id) throws SQLException {
        String album_title = resultSet.getString("album_title");
        String album_description = resultSet.getString("album_description");
        String release_date = resultSet.getString("album_release_date");
        int artist_id = resultSet.getInt("artist_id");
        String artist_name = resultSet.getString("artist_name");
        String artist_description = resultSet.getString("artist_description");
        Artist artist = new Artist(artist_id, artist_name, artist_description);
        return new Album(id, album_title, album_description, release_date, artist);
    }
}
