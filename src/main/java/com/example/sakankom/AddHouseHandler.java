
package com.example.sakankom;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXCheckbox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;

import java.sql.*;

public class AddHouseHandler {

    @FXML
    private MFXButton btnSubmit;
    @FXML
    private MFXTextField balaconyNumber;

    @FXML
    private MFXTextField bathroomsNumber;

    @FXML
    private MFXTextField bedroomsNumber;

    @FXML
    private MFXTextField capacity;

    @FXML
    private MFXTextField flatNumber;

    @FXML
    private MFXTextField floorNumber;

    @FXML
    private MFXTextField genders;

    @FXML
    private MFXTextField imageName;

    @FXML
    private MFXCheckbox isAccepted;

    @FXML
    private MFXTextField houseID;
    @FXML
    private MFXCheckbox isReserved;

    @FXML
    private MFXCheckbox isValid;

    @FXML
    private MFXTextField price;
    @FXML
    private MFXTextField services;
    @FXML
    private MFXTextField reservedCapacity;

    @FXML
    private MFXTextField residenceID;

    @FXML
    void submitBtnHandler(ActionEvent event) {
        ResultSet rst;

        String accepted = isAccepted.isSelected() ? "1" : "0";
        String reserved = isReserved.isSelected() ? "1" : "0";
        try{
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@//localhost:1521/xepdb1", "sakankom", "12345678");
            Statement st = con.createStatement();

            rst = st.executeQuery("INSERT INTO HOUSE (HOUSE_ID," +
                    " RESIDENCE_ID, " +
                    "BEDROOMS_NUMBER, SERVICES, PRICE, FLOOR_NUMBER, " +
                    "FLAT_NUMBER, CAPACITY, RESERVED_CAPACITY, GENDERS, " +
                    "BALCONY, ISVALID, ISACCEPTED, ISRESERVED, IMAGE)" +
                    " VALUES ('" + houseID.getText() + "','" +
                    residenceID.getText() + "','" + bedroomsNumber.getText() + "','" +
                    services.getText() + "','" + price.getText() + "','" +
                    floorNumber.getText() + "','" + flatNumber.getText() + "','" +
                    capacity.getText() + "','" + reservedCapacity.getText() + "','" +
                    genders.getText() + "','" + balaconyNumber.getText() + "','" +
                    "1" + "','" + accepted + "','" + reserved + "','" + imageName.getText() + "' )"
            );

            con.close();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Message");
            alert.setHeaderText(null);
            alert.setContentText("Add House Successfully :)");

            alert.showAndWait();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}