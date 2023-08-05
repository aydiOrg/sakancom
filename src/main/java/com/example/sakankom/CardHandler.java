
package com.example.sakankom;

import com.example.sakankom.OwnerFiles.House;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class CardHandler  {
    @FXML
    private HBox box;
    @FXML
    private javafx.scene.control.Label houseName;
    @FXML
    private javafx.scene.control.Label houseRes;
    @FXML
    private ImageView houseImage;
    private final String [] colors = {":#b9e5ff", ":#bdb2fe", ":#fb9aa8", ":#ff5056"};

    public void setDate(House house){
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(house.getImageSrc())));
        houseImage.setImage(image);

        houseName.setText(house.getName());
        houseRes.setText(house.getRes());
        box.setStyle("-fx-background-color" + colors[(int)(Math.random()*colors.length)] + "; -fx-background-radius: 15; -fx-effect: dropShadow(three-pass-box, rgba(0, 0, 0, 0.1), 10, 0, 0, 10)");
    }

}