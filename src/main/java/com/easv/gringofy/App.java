package com.easv.gringofy;

import com.easv.gringofy.dal.db.DBConnection;
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
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/com/easv/gringofy/views/player-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle(Env.get("APP_NAME", "Gringofy"))
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}