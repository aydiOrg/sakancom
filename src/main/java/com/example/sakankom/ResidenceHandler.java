package com.example.sakankom;

import com.example.sakankom.OwnerFiles.House;
import com.example.sakankom.OwnerFiles.Residence;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ResidenceHandler implements Initializable {
    @FXML
    private ResidencesHandler residencesHandler;

    @FXML
    private Label locationField;

    @FXML
    private Label ownerName;

    @FXML
    private Label residenceName;
    @FXML
    private VBox residenceCard;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void setDate(Residence residence){
        locationField.setText(residence.getLocation());
        ownerName.setText(residence.getOwnerName());
        residenceName.setText(residence.getResidenceName());
    }

}
