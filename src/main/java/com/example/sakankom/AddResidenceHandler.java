package com.example.sakankom;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXCheckbox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.sql.*;

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
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@//localhost:1521/xepdb1", "sakankom", "12345678");
            Statement st = con.createStatement();
            Statement st2 = con.createStatement();
            Statement st3 = con.createStatement();
            Statement st4 = con.createStatement();

            rst = st.executeQuery("INSERT INTO RESIDENCE VALUES ('" + residenceID.getText() + "','" + ownerID.getText() + "','" + locationField.getText() + "','" + residenceName.getText() + "','1')");
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}