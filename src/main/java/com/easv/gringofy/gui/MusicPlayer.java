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
    private void goToHomePageView(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/easv/gringofy/views/home-page-view.fxml"));
        Parent root = loader.load();
        MusicPlayer controller = loader.getController();
        controller.changeSwitchStateButton();
        Scene scene = new Scene(root);
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }
    @FXML
    private void goToCategoriesView(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/easv/gringofy/views/categories-view.fxml"));
        Parent root = loader.load();
        MusicPlayer controller = loader.getController();
        controller.changeSwitchStateButton();
        Scene scene = new Scene(root);
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        changeSwitchStateButton();
    }
    @FXML
    private void goToPlaylistsView(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/easv/gringofy/views/playlists-view.fxml"));
        Parent root = loader.load();
        MusicPlayer controller = loader.getController();
        controller.changeSwitchStateButton();
        Scene scene = new Scene(root);
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        changeSwitchStateButton();
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
    @FXML
    private void playPreviousSong(ActionEvent actionEvent) {
        SongQueue.playPreviousSong();
    }

    @FXML
    private void playNextSong(ActionEvent actionEvent) {
        SongQueue.playNextSong();
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
