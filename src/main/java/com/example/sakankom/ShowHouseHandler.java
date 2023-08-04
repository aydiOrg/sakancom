package com.example.sakankom;

import com.example.sakankom.OwnerFiles.House;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class ShowHouseHandler implements Initializable {
    @FXML
    private ImageView houseImage;

    @FXML
    private Label houseName;

    @FXML
    private Label houseRes;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) { }
    public void setDate(House house){
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(house.getImageSrc())));
        houseImage.setImage(image);
        houseName.setText(house.getName());
        houseRes.setText(house.getRes());
    }
}
