package com.easv.gringofy;

import com.easv.gringofy.exceptions.PlayerException;
import com.easv.gringofy.gui.models.PlayerModel;
import com.easv.gringofy.utils.Env;
import com.easv.gringofy.utils.Validator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class App extends Application {

    PlayerModel playerModel = new PlayerModel();
    @Override
    public void start(Stage stage) throws IOException, PlayerException, SQLException {
        playerModel.loadDefaultSongs();
        playerModel.loadDefaultPlaylists();
        playerModel.loadDefaultAlbums();
        playerModel.loadDefaultArtists();
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/com/easv/gringofy/views/home-page-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle(Env.get("APP_NAME", "Gringofy"));
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

        Validator validator = new Validator()
                .setField("name", "Stefan")
                .setField("age", "15")
                .required("name", "age")
                .min("age", 15)
                .max("name", 5)
                .numeric("age", "name");

        if (validator.passes()) {
            System.out.println("success");
        } else {
            StringBuilder errorMessages = new StringBuilder();
            validator.getErrors().forEach((field, messages) -> {
                messages.forEach(message -> errorMessages.append(message).append("\n"));
            });
            System.out.println(errorMessages.toString());
        }
    }

    public static void main(String[] args) {
        launch();
    }
}