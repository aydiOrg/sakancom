package com.example.sakankom;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXCheckbox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;

import java.sql.*;

public class AddResidenceHandler {

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
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@//localhost:1521/xepdb1", "sakankom", "12345678");
            Statement st = con.createStatement();

            rst = st.executeQuery("INSERT INTO RESIDENCE VALUES ('" + residenceID.getText() + "','" + ownerID.getText() + "','" + locationField.getText() + "','" + residenceName.getText() + "','1')");
            con.close();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Message");
            alert.setHeaderText(null);
            alert.setContentText("Add Residence Successfully :)");

            alert.showAndWait();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}