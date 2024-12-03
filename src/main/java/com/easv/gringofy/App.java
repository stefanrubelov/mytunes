package com.easv.gringofy;

import com.easv.gringofy.dal.db.DBConnection;
import com.easv.gringofy.dal.db.SongDAODB;
import com.easv.gringofy.exceptions.PlayerException;
import com.easv.gringofy.utils.Env;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException, PlayerException, SQLException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/com/easv/gringofy/views/home-page-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle(Env.get("APP_NAME", "Gringofy"));
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

//        ArtistDAODB artistDAODB = new ArtistDAODB();
        SongDAODB songDAODB = new SongDAODB();
        songDAODB.getAllSongs();
//        GenreDAODB genreDAODB = new GenreDAODB();
//        try {
//            artistDAODB.get(1);
////            artistDAODB.delete(1);
//            Song newSong = new Song(0,69,genreDAODB.get(1), "insert new Song Title", artistDAODB.get(1), "2222-12-22", LocalDateTime.now(), LocalDateTime.now());
//
//            songDAODB.insert(newSong);
//        } catch (Exception e){
//            e.printStackTrace();
//
//        }
//        SongDAODB songDAODB = new SongDAODB();
//        try {
//            Song song = songDAODB.get(1);
//            System.out.println();
//
//            System.out.println(song.getTitle() + " - " + song.getArtist().getName() + " (" + song.getGenre().getTitle() + ")");
//
//        } catch (PlayerException e) {
//            throw new RuntimeException(e);
//        }
    }

    public static void main(String[] args) throws SQLException {
        launch();
    }
}