package com.easv.gringofy.gui;

import com.easv.gringofy.gui.models.PlayerModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class MusicPlayer {

    private PlayerModel playerModel = new PlayerModel();

    @FXML protected Button buttonSwitchState;

    @FXML
    private void playPreviousSong(ActionEvent actionEvent) {
        playerModel.playPreviousSong(actionEvent);
    }

    @FXML
    private void playCurrentSong(ActionEvent actionEvent) {
        playerModel.playCurrentSong(actionEvent);
    }

    @FXML
    private void playNextSong(ActionEvent actionEvent) {
        playerModel.playNextSong(actionEvent);
    }

    @FXML
    private void goToHomePageView(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/easv/gringofy/views/home-page-view.fxml")));
        Scene scene = new Scene(root);
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }
    @FXML
    private void goToCategoriesView(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/easv/gringofy/views/categories-view.fxml")));
        Scene scene = new Scene(root);
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }
    @FXML
    private void goToPlaylistsView(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/easv/gringofy/views/playlists-view.fxml")));
        Scene scene = new Scene(root);
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }
    @FXML private void playSong(ActionEvent event) {
        if(SongQueue.switchState()){
            if(SongQueue.getState()){
                buttonSwitchState.getStyleClass().remove("pause-button");
                buttonSwitchState.getStyleClass().add("play-button");
            }
            else{

                buttonSwitchState.getStyleClass().remove("play-button");
                buttonSwitchState.getStyleClass().add("pause-button");
            }
        }
    }

    public void changeSwitchStateButton() {
        if(SongQueue.getState()){
            buttonSwitchState.getStyleClass().remove("play-button");
            buttonSwitchState.getStyleClass().add("pause-button");
        }
        else{
            buttonSwitchState.getStyleClass().remove("pause-button");
            buttonSwitchState.getStyleClass().add("play-button");
        }
    }
}
