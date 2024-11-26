package com.easv.gringofy.dal.db;

import com.easv.gringofy.be.Genre;
import com.easv.gringofy.exceptions.PlayerException;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class GenreDAODB {
    public Genre getGenreById(int id) throws PlayerException {
        try (DBConnection dbConnection = new DBConnection();
             Connection connection = dbConnection.getConnection();)
        {
            String sql = "SELECT * FROM genre WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String title = resultSet.getString("title");
                LocalDateTime createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
                LocalDateTime updatedAt = resultSet.getTimestamp("updated_at").toLocalDateTime();
                return new Genre(id, title, createdAt, updatedAt);
            }
        }
        catch (Exception e) {
            throw new PlayerException(e.getMessage());

        }
        throw new PlayerException("Genre with id " + id + " not found");
    }

    public List<Genre> getAllGenres() throws PlayerException {
        try (DBConnection dbConnection = new DBConnection();
             Connection connection = dbConnection.getConnection();)
        {
        List<Genre> genres = new ArrayList<>();
        String sql = "SELECT * FROM genre";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String title = resultSet.getString("title");
            LocalDateTime createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
            LocalDateTime updatedAt = resultSet.getTimestamp("updated_at").toLocalDateTime();
            genres.add(new Genre(id, title, createdAt, updatedAt));
        }
        }
        catch (Exception e) {
            throw new PlayerException(e.getMessage());
        }
        throw new PlayerException("No genres table found");
    }
}