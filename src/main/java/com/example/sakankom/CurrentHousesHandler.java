package com.example.sakankom;
import com.example.sakankom.dataStructures.Apartment;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;

import javax.swing.*;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CurrentHousesHandler implements Initializable {
    @FXML
    private VBox container;
    ArrayList<Apartment> apartments;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        apartments = new ArrayList<Apartment>();
        //retrieve the data from database
        ResultSet rst;
        try{

            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@//localhost:1521/xepdb1", "sakankom", "12345678");
            //jdbc:oracle:thin:@//localhost:1521/xepdb1
            Statement st = con.createStatement();
            rst = st.executeQuery("select * from house,owner,residence where house.residence_id = residence.residence_id and residence.owner_id = owner.owner_id");

            Apartment apt;
            while(rst.next()) {
                apt = new Apartment();
                apt.setAddress(rst.getString("location"));
                apt.setOwnerName(rst.getString("fname") + " " + rst.getString("lname"));
                apt.setAptName(rst.getString("residence_name"));
                apt.setHouseId(rst.getInt("house_id"));
                apt.setResidenceId(rst.getInt("residence_id"));
                apt.setOwnerId(rst.getInt("owner_id"));
                apt.setBathsN(rst.getInt("bathrooms_number"));
                apt.setBedsN(rst.getInt("bedrooms_number"));
                apt.setServices(rst.getString("services"));
                apt.setPrice(rst.getDouble("price"));
                apt.setFloor(rst.getInt("floor_number"));
                apt.setAptNumber(rst.getInt("flat_number"));
                apt.setCapacity(rst.getInt("capacity"));
                apt.setResCapacity(rst.getInt("reserved_capacity"));
                apt.setGender(rst.getString("genders"));
                apt.setBalcony(rst.getString("balcony"));
                apt.setIsValid(rst.getString("isvalid"));
                apt.setIsAccepted(rst.getString("isaccepted"));

                apartments.add(apt);
            }

            con.close();
        }
        catch (SQLException e){
            e.printStackTrace();
        }


        //Declaring elements --------------------------------------------------------------------
        container.setMaxWidth(1030);
        VBox card ;
        Label l1, l2;
        HBox cont , cont2;
        Label l3,l4;
        Label l5; MFXButton reserveBtn;
        MFXButton detailsBtn;

        //prepare strings and data.
        String name = "";
        String owner = "";
        String type = "";
        String address = "";
        String price = "";
        for(int i= 0; i<apartments.size();i++) {
            //data
            name = apartments.get(i).getAptName();
            owner = apartments.get(i).getOwnerName();
            type = (apartments.get(i).getCapacity() > 1) ? "shared" :  "solo";
            address = apartments.get(i).getAddress();
            price = Double.toString(apartments.get(i).getPrice());

            //generating elements
            //where it starts

            card = new VBox();
            card.setMaxWidth(950);
            card.getStylesheets().add("TenantHouses.css");
            card.getStyleClass().add("vbox");

            l1= new Label(name);
            l2 = new Label("By " + owner);
            l3 = new Label("Type: "+ type +" room   ,");
            l4 = new Label("   Address: " + address);
            cont = new HBox();
            cont2 = new HBox();

            cont.getChildren().add(l3); cont.getChildren().add(l4);

            l5 = new Label("Price: " + price +"$" + "\t\t");
            reserveBtn = new MFXButton("Reserve");
            detailsBtn = new MFXButton("details");



            //setting the styles
            reserveBtn.getStylesheets().add("TenantHouses.css");
            reserveBtn.getStyleClass().add("myBtn");
            detailsBtn.getStylesheets().add("TenantHouses.css");
            detailsBtn.getStyleClass().add("myBtn");
            l1.getStylesheets().add("TenantHouses.css");
            l1.getStyleClass().add("l1");

            cont2.getStylesheets().add("TenantHouses.css");
            cont2.getStyleClass().add("cont2");

            cont.getStylesheets().add("TenantHouses.css");
            cont.getStyleClass().add("cont");


            DoubleProperty fontSize = new SimpleDoubleProperty(18);
            l1.styleProperty().bind(Bindings.format("-fx-font-size: %.2fpt;", fontSize));
            DoubleProperty fontSize2 = new SimpleDoubleProperty(12);
            l2.styleProperty().bind(Bindings.format("-fx-font-size: %.2fpt;", fontSize2));
            l3.styleProperty().bind(Bindings.format("-fx-font-size: %.2fpt;", fontSize2));
            l4.styleProperty().bind(Bindings.format("-fx-font-size: %.2fpt;", fontSize2));
            l5.styleProperty().bind(Bindings.format("-fx-font-size: %.2fpt;", fontSize2));


            //adding elements to card
            card.getChildren().add(l1);
            card.getChildren().add(l2);
            card.getChildren().add(cont);

            cont2.getChildren().add(l5);cont2.getChildren().add(detailsBtn);cont2.getChildren().add(new Label("   "));cont2.getChildren().add(reserveBtn);
            card.getChildren().add(cont2);

            container.getChildren().add(card);

            //where it ends


        }



    }
}
