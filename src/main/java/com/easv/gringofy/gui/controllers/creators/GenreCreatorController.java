package com.easv.gringofy.gui.controllers.creators;

import com.easv.gringofy.be.Genre;
import com.easv.gringofy.bll.GenreManager;
import com.easv.gringofy.exceptions.PlayerException;

import com.easv.gringofy.gui.controllers.GenreController;
import com.easv.gringofy.gui.controllers.GenresPageController;
import com.easv.gringofy.gui.models.PlayerModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class GenreCreatorController implements Initializable {

    private Genre genre;
    private String filePath;
    private GenresPageController genresPageController;
    private GenreController genreController;
    private boolean editMode = false;

    private final PlayerModel playerModel = new PlayerModel();
    private final GenreManager genreManager = new GenreManager();
    private final static String IMAGES_DIRECTORY_PATH = "src/main/resources/com/easv/gringofy/images/musicGenres";
    private final static String DEFAULT_GENRE_PICTURE = "src/main/resources/com/easv/gringofy/images/musicGenres/defaultGenrePicture.png";

    @FXML
    private TextField txtFieldGenreName;

    @FXML
    private Label lblPath;

    @FXML
    private ImageView imgViewImageContainer;

    @FXML
    private StackPane stackPaneContainer;

    @FXML
    private ImageView imgViewEditIcon;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        imgViewEditIcon.setVisible(false);
        stackPaneContainer.setOnMouseClicked(_ -> {
            try {
                selectFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        stackPaneContainer.setOnMouseEntered(_ -> {
            imgViewEditIcon.setVisible(true);
        });
        stackPaneContainer.setOnMouseExited(_ -> {
            imgViewEditIcon.setVisible(false);
        });
    }

    public void createGenre() throws PlayerException, SQLException {
        String name = txtFieldGenreName.getText();
        LocalDateTime now = LocalDateTime.now();
        String path = filePath;
        if(filePath == null) {
            path = DEFAULT_GENRE_PICTURE;
        }
        if (!editMode) {
            Genre genre = new Genre(name, path, now, now);
            genreManager.insert(genre);
            genresPageController.refresh();
        } else {
            Genre genre = new Genre(name, path);
            genreManager.update(genre);
            genreController.setGenre(genre);
            refreshGenresData();
        }
        Stage stage = (Stage) txtFieldGenreName.getScene().getWindow();
        stage.close();
    }

    private void refreshGenresData() throws SQLException, PlayerException {
        playerModel.loadDefaultGenres();
    }

    public void setGenreController(GenreController genreController) {
        this.genreController = genreController;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public void setCurrentParameters() {
        txtFieldGenreName.setText(genre.getName());
    }

    public void setEditMode(boolean editMode) {
        this.editMode = editMode;
    }

    public void setGenresPageController(GenresPageController genresPageController) {
        this.genresPageController = genresPageController;
    }

    @FXML
    private void selectFile() throws IOException {
        Stage stage = (Stage) txtFieldGenreName.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select a picture");
        FileChooser.ExtensionFilter imageFilter = new FileChooser.ExtensionFilter("Image Files (*.png, *.jpg, *.jpeg)", "*.png", "*.jpg", "*.jpeg");
        fileChooser.getExtensionFilters().add(imageFilter);
        // set initial directory
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            lblPath.setText("Selected Path: " + selectedFile.getAbsolutePath());
            setFilePath(selectedFile.getAbsolutePath());
            setImage(filePath);
        } else {
            lblPath.setText("No file selected");
        }
    }

    private void setFilePath(String filePath) throws IOException {
        Path sourcePath = Paths.get(filePath);

        Path destinationDir = Paths.get(IMAGES_DIRECTORY_PATH);

        Path destinationPath = destinationDir.resolve(sourcePath.getFileName());

        Files.move(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);

        this.filePath = destinationPath.toString();
    }

    private void setImage(String path){
        File file = new File(path);
        imgViewImageContainer.setImage(new Image(file.toURI().toString()));
    }

}
