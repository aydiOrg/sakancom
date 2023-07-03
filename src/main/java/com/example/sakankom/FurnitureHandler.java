package com.example.sakankom;
import com.example.sakankom.dataStructures.Furniture;
import com.example.sakankom.dataStructures.Tenant;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXScrollPane;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class FurnitureHandler implements Initializable {
        Tenant tenant;
        @FXML
        private MFXButton addBtn;
        @FXML
        private VBox container;
        @FXML
        private MFXScrollPane mainPane;

    ArrayList<Furniture> furnitures;
    public MFXScrollPane getMainPane() {
        return mainPane;
    }
    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }

    @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {
        tenant = new Tenant();
             furnitures = new ArrayList<Furniture>();
             ResultSet rst;
             try {
                 DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
                 Connection con = DriverManager.getConnection("jdbc:oracle:thin:@//localhost:1521/xepdb1", "sakankom", "12345678");
                 Statement st = con.createStatement();
                 rst = st.executeQuery("select * from tenant, furniture where furniture.tenant_id = tenant.tenant_id");

                 Furniture furniture;
                 while (rst.next()) {
                     furniture = new Furniture();

                     furniture.setFurnitureId(rst.getInt("furniture_id"));
                     furniture.setTenantId(rst.getInt("tenant_id"));
                     furniture.setName(rst.getString("name"));
                     furniture.setDescription(rst.getString("description"));
                     furniture.setPrice(rst.getInt("price"));
                     furniture.setIsValid(rst.getString("isvalid"));
                     furniture.setOwnerName(rst.getString("fname") + " " + rst.getString("lname"));
                     furniture.setIsSold(rst.getString("issold"));
                     furnitures.add(furniture);
                 }
                 con.close();
             }
             catch (SQLException e) {
                 e.printStackTrace();
             }
             generateGUI();




         }
         public void generateGUI (){

            while(!container.getChildren().isEmpty()) {
                container.getChildren().remove(0);
            }
             //Declaring elements --------------------------------------------------------------------
             container.setMaxWidth(1030);

             //prepare strings and data.
             String name = "";
             String ownerName = "";
             String description = "";
             String price = "";
            VBox card;
            Label l1,l2,l3,l4;
            MFXButton btn;
                for (int i = 0; i < furnitures.size(); i++) {
                    if (furnitures.get(i).getIsValid().equalsIgnoreCase("1") && furnitures.get(i).getIsSold().equalsIgnoreCase("0")) {
                        //data
                        name = furnitures.get(i).getName();
                        ownerName = furnitures.get(i).getOwnerName();
                        price = furnitures.get(i).getPrice() + "";
                        description = furnitures.get(i).getDescription();

                        //generating elements
                        //where it starts

                        card = new VBox();
                        card.setMaxWidth(950);
                        card.getStylesheets().add("TenantHouses.css");
                        card.getStyleClass().add("vbox");

                        l1 = new Label(name);
                        l2 = new Label("By " + ownerName);
                        l3 = new Label("Description: " + description);
                        l4 = new Label(price + "$");

                        btn = new MFXButton("buy");
                        //setting the styles

                        l1.getStylesheets().add("TenantHouses.css");
                        l1.getStyleClass().add("l1");


                        DoubleProperty fontSize = new SimpleDoubleProperty(18);
                        l1.styleProperty().bind(Bindings.format("-fx-font-size: %.2fpt;", fontSize));
                        DoubleProperty fontSize2 = new SimpleDoubleProperty(12);
                        l2.styleProperty().bind(Bindings.format("-fx-font-size: %.2fpt;", fontSize2));
                        l3.styleProperty().bind(Bindings.format("-fx-font-size: %.2fpt;", fontSize2));
                        l4.styleProperty().bind(Bindings.format("-fx-font-size: %.2fpt;", fontSize2));
                        btn.getStylesheets().add("TenantHouses.css");
                        btn.getStyleClass().add("myBtn");

                        //adding elements to card
                        card.getChildren().add(l1);
                        card.getChildren().add(l2);
                        card.getChildren().add(l3);
                        card.getChildren().add(l4);
                        card.getChildren().add(btn);
                        container.getChildren().add(card);

                        btn.setId(Integer.toString(furnitures.get(i).getFurnitureId()));
                        btn.setOnAction(handler);
                        //where it ends

                    }

                }

         }

            EventHandler<ActionEvent> handler = event -> {
                MFXButton btn = (MFXButton) event.getSource();
                String id = btn.getId();

                try {
                    DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
                    Connection con = DriverManager.getConnection("jdbc:oracle:thin:@//localhost:1521/xepdb1", "sakankom", "12345678");
                    Statement st = con.createStatement();
                    st.executeUpdate("update furniture set issold = '1' where furniture_id = '" + id + "'");


                    con.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
                for (int i=0;i<furnitures.size();i++) {
                    if(furnitures.get(i).getFurnitureId() == Integer.parseInt(id)) {
                        furnitures.get(i).setIsSold("1");
                    }
                }
                generateGUI();
            };
        @FXML
        void addFurniture(ActionEvent event) {
            //loading my page
            FXMLLoader loader2 = new FXMLLoader(getClass().getResource("newFurniture.fxml"));
            Parent root2 = null;
            try{
                 root2 = loader2.load();
                Scene scene = new Scene(root2);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.show();

            }catch (IOException e) {
                e.printStackTrace();
            }

            NewFurnitureHandler newFurnitureHandler  = loader2.getController();
            newFurnitureHandler.setTenant(tenant);
            newFurnitureHandler.setFurnitures(furnitures);
            newFurnitureHandler.setFurnitureHandler(this);
        }


}
