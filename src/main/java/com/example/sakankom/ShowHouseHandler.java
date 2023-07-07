package com.example.sakankom;

import com.example.sakankom.OwnerFiles.House;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class ShowHouseHandler implements Initializable {
    @FXML
    private ImageView houseImage;

    @FXML
    private MFXButton btnShow;
    @FXML
    private Label houseName;

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
