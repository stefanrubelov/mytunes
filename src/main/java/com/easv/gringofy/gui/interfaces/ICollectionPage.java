package com.easv.gringofy.gui.interfaces;

import com.easv.gringofy.exceptions.PlayerException;
import javafx.fxml.Initializable;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public interface ICollectionPage extends Initializable {
    @Override
    void initialize(URL location, ResourceBundle resources);

    void fetch() throws SQLException, PlayerException;

    void get() throws SQLException;

    void set();

    void clear();

    void refresh() throws SQLException, PlayerException;
}
