package com.example.sakankom;

import com.example.sakankom.OwnerFiles.House;
import com.example.sakankom.OwnerFiles.Residence;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import oracle.jdbc.OracleConnectionWrapper;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.*;

public class ResidencesHandler implements Initializable {
    @FXML
    private GridPane residenceContainer;
    private List<Residence> residences;
    private List<House> houses;
    @FXML
    private Label mainLabel;

    private int totalTenants;

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

                for (Node node : residenceBox.getChildren()) {
                    if (node instanceof HBox) {
                        for (Node node2 : ((HBox) node).getChildren()) {
                            if(node2 instanceof MFXButton button) {
                                button.setOnAction(event -> {
                                    showHouses(residence.getResidenceName());
                                });
                            }
                        }
                    }
                }

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
    public void showHouses(String residenceName){
        residenceContainer.getChildren().clear();
        houses = new ArrayList<>();
        totalTenants = 0;
        ResultSet rst, rst2;

        try{
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@//localhost:1521/xepdb1", "sakankom", "12345678");
            Statement st = con.createStatement();
            Statement st2 = con.createStatement();

            rst = st.executeQuery("SELECT residence_id FROM residence WHERE residence_name='" + residenceName + "' and isvalid='1'");
            rst.next();
            rst2 = st2.executeQuery("SELECT * FROM house WHERE residence_id='" + rst.getString("residence_id") + "' and isvalid='1'");

            while (rst2.next()){
                houses.add(new House(
                        "House " + rst2.getString("house_id"),
                        "/photos/" + rst2.getString("image"),
                        rst2.getInt("price"),
                        residenceName,
                        rst2.getInt("floor_number")
                ));

                 totalTenants += rst2.getInt("reserved_capacity");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        mainLabel.setText("All houses of " + residenceName + " . " + houses.size() + " Houses with " + totalTenants + " person reserved.");

        try{
            ResultSet rst3, rst4;
            Set<Integer> uniqueFloors = new HashSet<>();
            int column = 1;
            int row = 1;
            try{
                DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
                Connection con = DriverManager.getConnection("jdbc:oracle:thin:@//localhost:1521/xepdb1", "sakankom", "12345678");
                Statement st = con.createStatement();
                Statement st2 = con.createStatement();

                rst3 = st2.executeQuery("SELECT residence_id FROM residence WHERE isvalid='1' and residence_name='" + residenceName + "'");
                rst3.next();
                rst4 = st.executeQuery("SELECT floor_number FROM house WHERE isvalid='1' and residence_id='" + rst3.getString("residence_id") + "'");

                while (rst4.next()) {
                    uniqueFloors.add(rst4.getInt("floor_number"));
                }
                Integer[] FloorsArray = uniqueFloors.toArray(new Integer[0]);

                Arrays.sort(FloorsArray);

                for(int i = 0 ; i < FloorsArray.length ; i++){
                    List<House> floorHouses = new ArrayList<>();
                    for(House housee : houses){
                        if(housee.getFloor() == FloorsArray[i]){
                            floorHouses.add(housee);
                        }
                    }

                    FXMLLoader fxmlLoader1 = new FXMLLoader();
                    fxmlLoader1.setLocation(getClass().getResource("showHouses.fxml"));
                    VBox floor = fxmlLoader1.load();
                    ShowHousesHandler Handler2 = fxmlLoader1.getController();
                    Handler2.setDate(floorHouses, residenceName);

                    residenceContainer.add(floor, 0, row++);
                    GridPane.setMargin(floor, new Insets(10));
                    con.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}