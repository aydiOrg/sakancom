package com.example.sakankom.OwnerFiles;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXCheckbox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AddResidenceHandler {

    @FXML
    private MFXButton btnSubmit;

    @FXML
    private MFXCheckbox isValid;

    @FXML
    private MFXTextField locationField;

    @FXML
    private MFXTextField ownerID;

    @FXML
    private MFXTextField residenceID;

    @FXML
    private MFXTextField residenceName;
    @FXML
    void submitBtnHandler(ActionEvent event) {
        ResultSet rst,rst2;

        try{
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}