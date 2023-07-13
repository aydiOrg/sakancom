
package com.example.sakankom;

import com.example.sakankom.OwnerFiles.House;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class CardHandler implements Initializable {
    @FXML
    private HBox box;

    public Label getHouseName() {
        return houseName;
    }

    public void setHouseName(Label houseName) {
        this.houseName = houseName;
    }

    public ImageView getHouseRating() {
        return houseRating;
    }

    public void setHouseRating(ImageView houseRating) {
        this.houseRating = houseRating;
    }

    public Label getHouseRes() {
        return houseRes;
    }

    public void setHouseRes(Label houseRes) {
        this.houseRes = houseRes;
    }

    public ImageView getHouseImage() {
        return houseImage;
    }

    public void setHouseImage(ImageView houseImage) {
        this.houseImage = houseImage;
    }

    @FXML
    private javafx.scene.control.Label houseName;

    @FXML
    private ImageView houseRating;

    @FXML
    private javafx.scene.control.Label houseRes;

    @FXML
    private ImageView houseImage;

    private String [] colors = {"B9E5FF", "BDB2FE", "FB9AA8", "FF5056"};

    public void setDate(House house){
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(house.getImageSrc())));
        houseImage.setImage(image);

        houseName.setText(house.getName());
        houseRes.setText(house.getRes());
        box.setStyle("-fx-background-color: #" + colors[(int)(Math.random()*colors.length)] + ";" +
                "-fx-background-radius: 15;" +
                "-fx-effect: dropShadow(three-pass-box, rgba(0, 0, 0, 0.1), 10, 0, 0, 10)");


    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}