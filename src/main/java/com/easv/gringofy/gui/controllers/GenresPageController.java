package com.easv.gringofy.gui.controllers;

import com.easv.gringofy.be.Genre;
import com.easv.gringofy.exceptions.PlayerException;
import com.easv.gringofy.gui.MusicPlayer;
import com.easv.gringofy.gui.NodeBuilder;
import com.easv.gringofy.gui.controllers.creators.GenreCreatorController;
import com.easv.gringofy.gui.interfaces.ICollectionPage;
import com.easv.gringofy.gui.models.PlayerModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class GenresPageController extends MusicPlayer implements Initializable, ICollectionPage {
    private final NodeBuilder nodeBuilder = new NodeBuilder();
    private final PlayerModel playerModel = new PlayerModel();
    private List<Genre> genres;

    @FXML
    private FlowPane flowPaneGenresContainer;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
        try {
            get();
            set();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void fetch() throws SQLException, PlayerException {
        playerModel.loadDefaultGenres();
    }

    @Override
    public void get() throws SQLException {
        genres = playerModel.getDefaultGenres();
    }

    @Override
    public void set() {
        genres.forEach(genre -> flowPaneGenresContainer.getChildren().add(nodeBuilder.genreToNode(genre)));
    }

    @Override
    public void clear() {
        flowPaneGenresContainer.getChildren().clear();
    }

    @Override
    public void refresh() throws SQLException, PlayerException {
        clear();
        get();
        fetch();
        set();
    }

    @FXML
    private void showGenreForm() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/easv/gringofy/views/genre-creator.fxml"));
        Parent root = loader.load();
        GenreCreatorController controller = loader.getController();
        controller.setGenresPageController(this);
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Artist Creator");
        stage.setResizable(false);
        stage.show();
    }
}
