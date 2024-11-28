package com.easv.gringofy.dal.db;

import com.easv.gringofy.be.Genre;
import com.easv.gringofy.be.Song;
import com.easv.gringofy.exceptions.PlayerException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SongDAODB {

    GenreDAODB genreData = new GenreDAODB();

    public List<Song> getAllSongs() throws PlayerException {
        List<Song> songs = new ArrayList<>();
        try (DBConnection dbConnection = new DBConnection();
             Connection connection = dbConnection.getConnection();) {
            String sqlPromptToSongs = "SELECT * FROM songs";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlPromptToSongs);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int genreId = resultSet.getInt("genre_id");
                int duration = resultSet.getInt("duration");
                String title = resultSet.getString("title");
                String artist = resultSet.getString("artist");
                String releaseDate = resultSet.getString("release_date");
                String filePath = resultSet.getString("path");
                LocalDateTime createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
                LocalDateTime updatedAt = resultSet.getTimestamp("updated_at").toLocalDateTime();
                Genre genre = genreData.getGenreById(genreId);
                Song song = new Song(id, duration, genre, title, artist, releaseDate, filePath, createdAt, updatedAt);
                songs.add(song);
            }
        } catch (Exception e) {
            throw new PlayerException(e.getMessage());
        }
        return songs;
    }


    public List<Song> getAllSongsByInput(String input) throws PlayerException {
        List<Song> songs = new ArrayList<>();
        if (input.length() >= 3) {
            try (DBConnection dbConnection = new DBConnection();
                 Connection connection = dbConnection.getConnection();) {
                String sqlPromptToSongs = "SELECT * FROM songs where title like ?";
                PreparedStatement preparedStatement = connection.prepareStatement(sqlPromptToSongs);
                preparedStatement.setString(1, "%" + input + "%");
//                preparedStatement.setString(2, "%" + input + "%");
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    int genreId = resultSet.getInt("genre_id");
                    int duration = resultSet.getInt("duration");
                    String title = resultSet.getString("title");
                    String artist = resultSet.getString("artist_id");
                    String releaseDate = resultSet.getString("release_date");
                    String filePath = resultSet.getString("path");
                    LocalDateTime createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
                    LocalDateTime updatedAt = resultSet.getTimestamp("updated_at").toLocalDateTime();
                    Genre genre = genreData.getGenreById(genreId);
                    Song song = new Song(id, duration, genre, title, artist, releaseDate, filePath, createdAt, updatedAt);
                    songs.add(song);
                }
            } catch (Exception e) {
                throw new PlayerException(e.getMessage());
            }
            return songs;
        }
        return null;
    }
}
