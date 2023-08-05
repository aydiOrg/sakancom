package com.example.sakankom;

import com.example.sakankom.dataStructures.AdminReservation;
import com.example.sakankom.dataStructures.Admin;
import com.example.sakankom.dataStructures.Apartment;
import com.example.sakankom.dataStructures.User;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXScrollPane;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
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

public class AdminMainPageHandler implements Initializable {
        @FXML
        private AnchorPane bigPane;
        @FXML
        private AnchorPane mainPane;
        @FXML
        private Label name;
        @FXML
        private MFXButton reservationsBtn;
        @FXML
        private VBox container;
        @FXML
        private MFXScrollPane page1;
        private MFXScrollPane page2;
        public boolean logoutBtnPressed = false;
        public boolean userClickedReservationsButton = false;
        public boolean userClickedAcceptButton = false;
        public boolean userClickedRejectButton = false;
        private final String password = "12345678";


        User user;
        public ArrayList<Apartment> apartments ;

        public Admin getAdmin() {
                return admin;
        }

        Admin admin;
        int houseIDTest1;
        int houseIDTest2;
        ArrayList<AdminReservation> adminReservations;
        AdminReservationsHandler adminReservationsHandler;
        public boolean isReservationsPressed;

        public User getUser() {
                return user;
        }

        public ArrayList<AdminReservation> getAdminReservations() {
                return adminReservations;
        }
        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {

                user = new User();
                ResultSet rst;
                apartments = new ArrayList<>();
                adminReservations = new ArrayList<>();
                isReservationsPressed = false;
                try{

                        DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
                        Connection con = DriverManager.getConnection("jdbc:oracle:thin:@//localhost:1521/xepdb1", "sakankom", password);
                        Statement st = con.createStatement();
                        rst = st.executeQuery("select * from house,owner,residence where house.residence_id = residence.residence_id and residence.owner_id = owner.owner_id and house.isvalid = '1' and house.isaccepted = '0'");

                        Apartment apt;
                        while(rst.next()) {
                                apt = new Apartment();
                                apt.setOwnerEmail(rst.getString("email"));
                                apt.setOwnerPhone(rst.getString("phone_number"));
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
                                apt.setIsReserved(rst.getString("isreserved"));
                                apartments.add(apt);
                        }
                        System.out.println(apartments.size()+"");


                        con.close();
                }
                catch (SQLException e){ e.printStackTrace(); }



                reservationsBtn.getStyleClass().add("selected");

                FXMLLoader loader2 = new FXMLLoader(getClass().getResource("Admin-Reservations.fxml"));

                try { Parent root = loader2.load(); } catch (IOException e) { throw new RuntimeException(e); }

                adminReservationsHandler = loader2.getController();
                page2 = adminReservationsHandler.getMainPane();

        }
        @FXML
        void logoutBtnHandler() {
                Stage stage = (Stage) mainPane.getScene().getWindow();
                stage.close();

                Stage newStage = new Stage();
                try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("Sign-In.fxml"));
                        Parent root = loader.load();
                        Scene scene = new Scene(root);
                        newStage.setScene(scene);
                        newStage.show();

                } catch (IOException e) { e.printStackTrace(); }
                logoutBtnPressed = true;

        }

        public void generateGUI(){
                if(!container.getChildren().isEmpty()) container.getChildren().remove(0);

                //Declaring elements --------------------------------------------------------------------
                container.setMaxWidth(920);
                VBox card ;
                Label l1, l2;
                HBox cont , cont2;
                Label l3,l4;
                Label l5; MFXButton reserveBtn , detailsBtn;

                //prepare strings and data.
                String name;
                String owner;
                String type;
                String address;
                String price;


                for(int i= 0; i<apartments.size();i++) {

                        if(apartments.get(i).getIsValid().equals("1") && apartments.get(i).getIsAccepted().equals("0") && apartments.get(i).getIsReserved().equalsIgnoreCase("0")){
                                //data
                                name = apartments.get(i).getAptName();
                                owner = apartments.get(i).getOwnerName();
                                type = (apartments.get(i).getCapacity() > 1) ? "shared" : "solo";
                                address = apartments.get(i).getAddress();
                                price = Double.toString(apartments.get(i).getPrice());

                                //generating elements

                                card = new VBox();
                                card.setMaxWidth(950);
                                card.getStylesheets().add("TenantHouses.css");
                                card.getStyleClass().add("vbox");

                                l1 = new Label(name);
                                l2 = new Label("By " + owner);
                                l3 = new Label("Type: " + type + " room   ,");
                                l4 = new Label("   Address: " + address);
                                cont = new HBox();
                                cont2 = new HBox();

                                cont.getChildren().add(l3);
                                cont.getChildren().add(l4);

                                l5 = new Label("Price: " + price + "$" + "\t\t");
                                reserveBtn = new MFXButton("accept");
                                detailsBtn = new MFXButton("reject");


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

                                cont2.getChildren().add(l5);
                                cont2.getChildren().add(detailsBtn);
                                cont2.getChildren().add(new Label("   "));
                                cont2.getChildren().add(reserveBtn);
                                card.getChildren().add(cont2);

                                container.getChildren().add(card);

                                reserveBtn.setId(Integer.toString(apartments.get(i).getHouseId()));
                                detailsBtn.setId(Integer.toString(apartments.get(i).getHouseId()));

                                int finalI1 = i;
                                VBox finalCard = card;
                                int finalI = i;

                                detailsBtn.setOnAction(event -> {
                                        userClickedRejectButton = true;
                                        houseIDTest1 =  apartments.get(finalI).getHouseId();

                                        ResultSet rst;

                                        try{
                                                DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
                                                Connection con = DriverManager.getConnection("jdbc:oracle:thin:@//localhost:1521/xepdb1", "sakankom", password);
                                                Statement st = con.createStatement();

                                                rst = st.executeQuery("UPDATE house SET isvalid='0' WHERE house_id='" + Integer.parseInt(Integer.toString(apartments.get(finalI1).getHouseId())) + "'");
                                                rst.next();

                                                con.close();
                                        } catch (SQLException e) { throw new RuntimeException(e); }

                                        apartments.remove(finalI1);

                                        container.getChildren().remove(finalCard);
                                });
                                reserveBtn.setOnAction(event -> {
                                        userClickedAcceptButton = true;
                                        houseIDTest2 =  apartments.get(finalI).getHouseId();

                                        ResultSet rst;

                                        try{
                                                DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
                                                Connection con = DriverManager.getConnection("jdbc:oracle:thin:@//localhost:1521/xepdb1", "sakankom", password);
                                                Statement st = con.createStatement();

                                                rst = st.executeQuery("UPDATE house SET isaccepted='1' WHERE house_id='" + Integer.parseInt(Integer.toString(apartments.get(finalI1).getHouseId())) + "'");
                                                rst.next();

                                                con.close();

                                        } catch (SQLException e) {throw new RuntimeException(e);}
                                        apartments.remove(finalI1);

                                        container.getChildren().remove(finalCard);
                                });


                                //where it ends

                        }

                }
        }
        @FXML
        void viewApartments() {
                if(! bigPane.getChildren().isEmpty()) bigPane.getChildren().remove(0);

                bigPane.getChildren().add(page1);
        }

        @FXML
        void viewReservations() {
                if(! bigPane.getChildren().isEmpty()) bigPane.getChildren().remove(0);

                bigPane.getChildren().add(page2);
                isReservationsPressed = true;
                userClickedReservationsButton = true;
                if (reservationsBtn.getStyleClass().contains("selected")) System.out.println("Do nothing");

                else generateGUI();

        }
        public void fetchData(){
                ResultSet rst,rst2;
                try{

                        DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
                        Connection con = DriverManager.getConnection("jdbc:oracle:thin:@//localhost:1521/xepdb1", "sakankom", password);
                        Statement st = con.createStatement();
                        rst = st.executeQuery("select * from house,owner,residence where house.residence_id = residence.residence_id and residence.owner_id = owner.owner_id and house.isvalid = '1' and house.isaccepted = '0'");

                        Apartment apt;
                        while(rst.next()) {
                                apt = new Apartment();
                                apt.setOwnerEmail(rst.getString("email"));
                                apt.setOwnerPhone(rst.getString("phone_number"));
                                apt.setAddress(rst.getString("location"));
                                apt.setOwnerName(rst.getString("fName") + " " + rst.getString("lName"));
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
                                apt.setIsValid(rst.getString("isValid"));
                                apt.setIsAccepted(rst.getString("isAccepted"));
                                apt.setIsReserved(rst.getString("isReserved"));
                                apartments.add(apt);
                        }

                        rst2 = st.executeQuery("select * from reservation , tenant , house , residence , owner where reservation.tenant_id = tenant.tenant_id and reservation.house_id = house.house_id and house.residence_id = residence.residence_id and residence.owner_id = owner.owner_id");

                        AdminReservation rs;
                        while (rst2.next()) {
                                rs = new AdminReservation();
                                rs.setResidenceID(rst2.getInt("residence_id"));
                                rs.setHouseId(rst2.getInt("house_id"));
                                rs.setOwnerId(rst2.getInt("owner_id"));
                                rs.setTenantId(rst2.getInt("tenant_id"));

                                rs.setResidenceName(rst2.getString("residence_name"));
                                rs.setPrice(rst2.getInt("price"));
                                rs.setAddress(rst2.getString("location"));
                                adminReservations.add(rs);
                        }

                        rst.close();
                        rst = st.executeQuery("select * from owner");
                        while(rst.next()) {
                                for (AdminReservation adminReservation : adminReservations) {
                                        if (adminReservation.getOwnerId() == rst.getInt("owner_id")) {
                                                adminReservation.setOwnerName(rst.getString("fname") + " " + rst.getString("lname"));
                                                adminReservation.setOwnerPhone(rst.getString("phone_number"));
                                                adminReservation.setOwnerEmail(rst.getString("email"));
                                        }
                                }
                        }
                        rst.close();

                        rst = st.executeQuery("select * from tenant");
                        while(rst.next()) {
                                for (AdminReservation adminReservation : adminReservations) {
                                        if (adminReservation.getTenantId() == rst.getInt("tenant_id")) {
                                                adminReservation.setTenantName(rst.getString("fname") + " " + rst.getString("lname"));
                                                adminReservation.setTenantPhone(rst.getString("phone_number"));
                                                adminReservation.setTenantEmail(rst.getString("email"));
                                        }
                                }
                        }


                        con.close();
                }
                catch (SQLException e){ e.printStackTrace(); }

                adminReservationsHandler.setUser(user);
                adminReservationsHandler.setAdminReservations(adminReservations);
                generateGUI();
        }
        public void setUser(User user) {
                this.user = user;
                name.setText(user.getUsername());

                ResultSet rst;
                try {
                        DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
                        Connection con = DriverManager.getConnection("jdbc:oracle:thin:@//localhost:1521/xepdb1", "sakankom", password);
                        Statement st = con.createStatement();
                        rst = st.executeQuery("select * from admin where username = '" + user.getUsername() + "'");

                        admin = new Admin();
                        if (rst.next()) {
                                admin.setAdminID(rst.getInt("admin_id"));
                        }
                        con.close();

                } catch (SQLException e) {e.printStackTrace();}

                fetchData();

        }

}
