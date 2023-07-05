package com.example.sakankom;

import com.example.sakankom.OwnerFiles.Residence;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

public class ResidencesHandler implements Initializable {
    @FXML
    private GridPane residenceContainer;
    @FXML
    private VBox residenceCard;
    @FXML
    private HBox residenceLayout;

    private List<Residence> residences;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        residences = new ArrayList<>();
        int column = 1;
        int row = 1;
        ResultSet rst, rst2;

        try {
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@//localhost:1521/xepdb1", "sakankom", "12345678");
            Statement st = con.createStatement();
            Statement st2 = con.createStatement();
            Statement st3 = con.createStatement();
            Statement st4 = con.createStatement();

            rst = st.executeQuery("select owner_id, residence_id, residence_name, location from residence where isValid='1'");

            while (rst.next()) {
                rst2 = st2.executeQuery("SELECT fname, lname FROM owner WHERE owner_id='" + rst.getString("owner_id") + "'");
                rst2.next();
                residences.add(new Residence(
                        rst.getString("residence_id"),
                        rst.getString("owner_id"),
                        rst.getString("location"),
                        rst.getString("residence_name"),
                        rst2.getString("fname") + " " + rst2.getString("lname")
                ));
            }
            Collections.reverse(residences);
            for (Residence residence : residences) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("residence.fxml"));
                VBox residenceBox = fxmlLoader.load();

                ResidenceHandler residenceHandler = fxmlLoader.getController();
                residenceHandler.setDate(residence);

                if (column == 6) {
                    column = 0;
                    ++row;
                }
                residenceContainer.add(residenceBox, column++, row);
                GridPane.setMargin(residenceBox, new Insets(10));
            }

            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }


}
