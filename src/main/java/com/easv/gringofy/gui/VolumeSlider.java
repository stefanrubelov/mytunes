package com.easv.gringofy.gui;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;

public class VolumeSlider extends VBox {
    private final DoubleProperty volumeValue;

    public VolumeSlider(double min, double max, double initialValue) {
        this.volumeValue = new SimpleDoubleProperty(initialValue);

        Slider slider = new Slider(min, max, initialValue);
        slider.setMajorTickUnit((max - min) / 4);
        slider.setBlockIncrement(10);
        slider.setMaxWidth(125);
        volumeValue.bindBidirectional(slider.valueProperty());

        this.setMaxWidth(125);
        this.getChildren().add(slider);
        this.setSpacing(10);
        this.setStyle("-fx-alignment: center; ");
    }

    public DoubleProperty volumeValueProperty() {
        return volumeValue;
    }

    public double getVolume() {
        return volumeValue.get();
    }

}