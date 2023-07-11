package com.example.sakankom;
import com.example.sakankom.dataStructures.Furniture;
import com.example.sakankom.dataStructures.Tenant;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class NewFurnitureHandler implements Initializable {
        @FXML
        private MFXButton sellBtn;

        @FXML
        private AnchorPane mainPane;

        @FXML
        private MFXTextField udescription;

        @FXML
        private MFXTextField uname;

        @FXML
        private MFXTextField uprice;
        Tenant tenant;
        ArrayList<Furniture> furnitures;
        VBox container;
        FurnitureHandler furnitureHandler;
        public boolean savePressed;
        int insertedId;
        public void setFurnitures(ArrayList<Furniture> furnitures){
            this.furnitures = furnitures;
        }
        public void setTenant(Tenant t){
            this.tenant = t;

        }
        public void setFurnitureHandler(FurnitureHandler t){
            this.furnitureHandler = t;
        }
        public AnchorPane getMainPane() {
            return mainPane;
        }


        @FXML
        void sellItem(ActionEvent event) {
                 String name = uname.getText();
                 String price = uprice.getText();
                 String description = udescription.getText();
                 String owner = Integer.toString(tenant.getTenantID());

                 Furniture furniture;
                 ResultSet rst;
                 try{
                         DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
                         Connection con = DriverManager.getConnection("jdbc:oracle:thin:@//localhost:1521/xepdb1", "sakankom", "12345678");
                         Statement st = con.createStatement();
                         st.executeUpdate("insert into furniture values (fur_seq.nextval,'" +owner+"' , '"+price+"' , '1', '"+name+"' , '"+description + "' , '0')");
                         con.setAutoCommit(false);
                         con.commit();


                         furniture = new Furniture();
                         furniture.setDescription(description);
                         furniture.setName(name);
                         furniture.setPrice(Integer.parseInt(price));
                         furniture.setOwnerName(tenant.getFname() + " " + tenant.getLname());
                         rst = st.executeQuery("select * from furniture order by furniture_id desc");
                         if(rst.next())
                         furniture.setFurnitureId(rst.getInt("furniture_id"));
                         furniture.setTenantId(tenant.getTenantID());
                         furniture.setIsSold("0");
                         furniture.setIsValid("1");
                         insertedId = furniture.getFurnitureId();

                         furnitures.add(furniture);

                        con.close();
                 }catch (SQLException e) {
                         e.printStackTrace();
                 }
                 furnitureHandler.generateGUI();
                 savePressed = true;

        }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tenant = new Tenant();
        savePressed = false;
        insertedId = -1;
    }

    public MFXTextField getUdescription() {
        return udescription;
    }

    public MFXTextField getUname() {
        return uname;
    }

    public MFXTextField getUprice() {
        return uprice;
    }
}
