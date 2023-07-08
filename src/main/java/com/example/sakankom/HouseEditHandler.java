package com.example.sakankom;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXCheckbox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class HouseEditHandler implements Initializable {
    @FXML
    private MFXTextField balaconyNumber;

    @FXML
    private MFXTextField bathroomsNumber;

    @FXML
    private MFXTextField bedroomsNumber;

    @FXML
    private MFXButton btnSubmit;

    @FXML
    private MFXTextField capacity;

    @FXML
    private MFXTextField flatNumber;

    @FXML
    private MFXTextField floorNumber;

    @FXML
    private MFXTextField genders;

    @FXML
    private MFXTextField houseID;

    @FXML
    private MFXTextField imageName;

    @FXML
    private MFXCheckbox isAccepted;

    @FXML
    private MFXCheckbox isReserved;

    @FXML
    private MFXCheckbox isValid;

    @FXML
    private MFXTextField price;

    @FXML
    private MFXTextField reservedCapacity;

    @FXML
    private MFXTextField residenceID;

    @FXML
    private MFXTextField services;

    @FXML
    void submitBtnHandler(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
