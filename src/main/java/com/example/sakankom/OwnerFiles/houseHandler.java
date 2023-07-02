package com.example.sakankom.OwnerFiles;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class houseHandler implements Initializable {
    @FXML
    private ImageView houseImage;

    @FXML
    private Label houseName;

    @FXML
    private ImageView houseRating;

    @FXML
    private Label houseRes;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void setDate(House house){
        Image image = new Image(getClass().getResourceAsStream(house.getImageSrc()));
        houseImage.setImage(image);
        houseName.setText(house.getName());
        houseRes.setText(house.getRes());

    }
}