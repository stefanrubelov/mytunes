package com.easv.gringofy.gui.controllers;

import com.easv.gringofy.gui.MusicPlayer;
import com.easv.gringofy.gui.SongQueue;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class CategoriesPageController extends MusicPlayer implements Initializable {
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        progressBar.progressProperty().bind(SongQueue.getProgressProperty());
    }
}
