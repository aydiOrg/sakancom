package com.example.sakankom;
import com.example.sakankom.dataStructures.Apartment;
import com.example.sakankom.dataStructures.Reservation;
import com.example.sakankom.dataStructures.Tenant;
import com.example.sakankom.dataStructures.User;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXScrollPane;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.ResourceBundle;
import java.util.Stack;

import static javafx.application.Platform.exit;

public class MainPageHandler implements Initializable {
        //data from the sign in
        User user;
        ArrayList<Apartment> apartments;
        ArrayList<Reservation> reservations;
        CurrentHousesHandler currentHousesHandler;
        FurnitureHandler furnitureHandler;
        Tenant tenant;
        @FXML
        private MFXTextField uBdate;

        @FXML
        private MFXTextField uEmail;
        @FXML
        private AnchorPane page1;

        @FXML
        private MFXTextField uGender;

        @FXML
        private MFXTextField uJob;

        @FXML
        private MFXTextField uName;

        @FXML
        private MFXTextField uPassword;

        @FXML
        private MFXTextField uPhone;

        @FXML
        private MFXTextField uUsername;


        @FXML
        private Label userLabel;
        @FXML
        private Button btn1;
        @FXML
        private HBox container;

        @FXML
        private Button btn2;

        @FXML
        private Button btn3;
        @FXML
        private Button logoutBtn;
        @FXML
        private AnchorPane mainPane;
        @FXML
        private MFXButton editBtn;

        private MFXScrollPane page3;
        MFXScrollPane page2;

        Button[] buttons = new Button[5];


        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {
                buttons[0] = btn1;buttons[1] = btn2;buttons[2] = btn3;
                uEmail.setEditable(false); uUsername.setEditable(false); uPassword.setEditable(false); uGender.setEditable(false); uJob.setEditable(false); uPhone.setEditable(false);uBdate.setEditable(false);uName.setEditable(false);
                reservations =new ArrayList<Reservation>();
                //loading my pages
                FXMLLoader loader1 = new FXMLLoader(getClass().getResource("Current-Houses.fxml"));
                try {
                        Parent root1 = loader1.load();
                } catch (IOException e) {
                        throw new RuntimeException(e);
                }
                currentHousesHandler = loader1.getController();
                page3 = currentHousesHandler.getMainPane();
                apartments = currentHousesHandler.getApartments();

                FXMLLoader loader2= new FXMLLoader(getClass().getResource("Furniture.fxml"));

                try{
                        Parent root2 = loader2.load();
                }catch (IOException e) {
                        e.printStackTrace();
                }
                furnitureHandler = loader2.getController();
                page2 = furnitureHandler.getMainPane();







        }

        @FXML
        public void editData(ActionEvent event) {
               if(editBtn.getText().equalsIgnoreCase("edit")){
                       tenant.setUpdated(true);
                       //setting the fields to be editable
                       uEmail.setEditable(true); uUsername.setEditable(true); uPassword.setEditable(true); uGender.setEditable(true); uJob.setEditable(true); uPhone.setEditable(true);uBdate.setEditable(true);uName.setEditable(true);
                       editBtn.setText("Save");uPassword.setText(tenant.getPassword());
                }
                else if(editBtn.getText().equalsIgnoreCase("save")){
                        tenant.setUpdated(false);
                        //setting the fields to be editable
                        uEmail.setEditable(false); uUsername.setEditable(false); uPassword.setEditable(false); uGender.setEditable(false); uJob.setEditable(false); uPhone.setEditable(false);uBdate.setEditable(false);uName.setEditable(false);
                        editBtn.setText("edit");
                        //insert the data to the database

                        String []name = uName.getText().split(" ");
                        String bdate = uBdate.getText();
                        String pnumber = uPhone.getText();
                        String email = uEmail.getText();
                        String job = uJob.getText();
                        String gender = uGender.getText();
                        String username = uUsername.getText();
                        String password = uPassword.getText();



                        try{
                                DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
                                Connection con = DriverManager.getConnection("jdbc:oracle:thin:@//localhost:1521/xepdb1", "sakankom", "12345678");
                                //jdbc:oracle:thin:@//localhost:1521/xepdb1
                                Statement st = con.createStatement();
                                st.executeUpdate("update tenant set fname= '" + name[0] + "' , lname = '" + name[1] + "' , birthdate = to_date('" + bdate + "','yyyy-mm-dd') , phone_number = '" + pnumber + "' , email = '" + email + "', job = '" + job+ "' , gender = '" + gender + "', username = '" +username+ "', pass = '" +password + "' where username = '" + tenant.getUsername() + "'" );

                                con.setAutoCommit(false);
                                con.commit();
                                con.close();
                        } catch (Exception e)
                        {
                                e.printStackTrace();
                        }
                       String s ="";
                       for(int i =0;i<tenant.getPassword().length();i++) {
                               s += "*";
                       }
                       uPassword.setText(s);

                }

        }

        void setUser(User u) {
                this.user = u;
                userLabel.setText(user.getUsername());
        }
        public void setTenantData(User user){
                ResultSet rst;
                try {
                        DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
                        Connection con = DriverManager.getConnection("jdbc:oracle:thin:@//localhost:1521/xepdb1", "sakankom", "12345678");
                        //jdbc:oracle:thin:@//localhost:1521/xepdb1
                        Statement st = con.createStatement();
                        rst = st.executeQuery("select * from tenant where username = '" + user.getUsername() + "'");

                        tenant = new Tenant();
                        while (rst.next()){
                                tenant.setUpdated(false);
                                tenant.setTenantID(rst.getInt("tenant_id"));
                                tenant.setFname(rst.getString("fname"));
                                tenant.setLname(rst.getString("lname"));
                                tenant.setbDate(rst.getString("birthdate"));
                                tenant.setpNumber(rst.getString("phone_number"));
                                tenant.setEmail(rst.getString("email"));
                                tenant.setJob(rst.getString("job"));
                                tenant.setGender(rst.getString("gender"));
                                tenant.setUsername(user.getUsername());
                                tenant.setPassword(user.getPassword());
                        }
                        con.close();
                }
                catch (SQLException e) {
                        e.printStackTrace();
                }

                //send the tenant data to currentHousesHandler
                currentHousesHandler.setTenant(tenant);
                furnitureHandler.setTenant(tenant);

                //set the labels
                uName.setText(tenant.getFname() + " " + tenant.getLname());
                uBdate.setText(tenant.getbDate().substring(0,10));
                uJob.setText(tenant.getJob());
                uPhone.setText(tenant.getpNumber());
                uEmail.setText(tenant.getEmail());
                uGender.setText(tenant.getGender());
                uUsername.setText(tenant.getUsername());
                String s ="";
                for(int i =0;i<tenant.getPassword().length();i++) {
                        s += "*";
                }
                uPassword.setText(s);
        }
        public void manageReservations() {
                //page1 special ----------------------------------------------------------------
                //connection
                ResultSet rst;
                try {
                        DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
                        Connection con = DriverManager.getConnection("jdbc:oracle:thin:@//localhost:1521/xepdb1", "sakankom", "12345678");
                        //jdbc:oracle:thin:@//localhost:1521/xepdb1
                        Statement st = con.createStatement();
                        rst = st.executeQuery("select * from reservation , tenant where isvalid = '1' and reservation.tenant_id = tenant.tenant_id and tenant.tenant_id = '" + tenant.getTenantID() +"'");


                        Reservation reservation;

                        while (rst.next()) {
                                reservation = new Reservation();
                                reservation.setHouse_id(rst.getInt("house_id"));
                                reservation.setTenant_id(rst.getInt("tenant_id"));
                                reservation.setReservationDate(rst.getString("reservation_date"));
                                reservation.setPrice(rst.getInt("price"));
                                reservation.setIsValid(rst.getString("isvalid"));

                                //payying date
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                Calendar cal = Calendar.getInstance();
                                cal.setTime(rst.getDate("payying_date"));
                                cal.add(Calendar.DAY_OF_MONTH,30);
                                String dateafter = sdf.format(cal.getTime());
                                System.out.println(dateafter);
                                // done editing

                                reservation.setPayyingDate(dateafter);
                                reservations.add(reservation);
                        }
                        con.close();
                }
                catch (SQLException e){
                        e.printStackTrace();
                }
                String name = "22";
                String address = "22";
                String owner = "33";
                String payDate = "44"; //reservation
                String email = "55";
                String phone = "66";

                for (int i =0;i<reservations.size();i++) {
                        for (int k=0;k<apartments.size();k++) {
                                if (apartments.get(k).getHouseId() == reservations.get(i).getHouse_id()) {
                                        name = apartments.get(k).getAptName();
                                        address = apartments.get(k).getAddress();
                                        owner = apartments.get(k).getOwnerName();
                                        payDate = reservations.get(i).getPayyingDate();
                                        email = apartments.get(k).getOwnerEmail();
                                        phone = apartments.get(k).getOwnerPhone();
                                        break;
                                }
                        }

                        //filling the reservations
                        VBox card;
                        Label l1, l2, l3,l4,l5,l6;
                        HBox h1,h2,h3,h4,h5,h6;

                        //generating elements
                        card = new VBox();
                        card.setMaxWidth(250); card.setPrefWidth(250);card.setMinWidth(250);

                        h1 = new HBox();
                        h2 = new HBox();
                        h3 = new HBox();
                        h4 = new HBox();
                        h5 = new HBox();
                        h6 = new HBox();

                        l1 = new Label(name);
                        l2 = new Label(address);
                        l3= new Label(owner);
                        l4 = new Label(payDate);
                        l5 = new Label(phone);
                        l6 = new Label(email);

                        DoubleProperty fontSize = new SimpleDoubleProperty(16);
                        l1.styleProperty().bind(Bindings.format("-fx-font-size: %.2fpt;", fontSize));
                        DoubleProperty fontSize2 = new SimpleDoubleProperty(13);
                        l2.styleProperty().bind(Bindings.format("-fx-font-size: %.2fpt;", fontSize2)); l3.styleProperty().bind(Bindings.format("-fx-font-size: %.2fpt;", fontSize2));l4.styleProperty().bind(Bindings.format("-fx-font-size: %.2fpt;", fontSize2));l5.styleProperty().bind(Bindings.format("-fx-font-size: %.2fpt;", fontSize2));l6.styleProperty().bind(Bindings.format("-fx-font-size: %.2fpt;", fontSize2));
                        card.getStyleClass().add("hbox");
                        card.getStylesheets().add("mainPageHandler.css");
                        l1.getStyleClass().add("l1");
                        l2.getStyleClass().add("l2");
                        l3.getStyleClass().add("l3");
                        l4.getStyleClass().add("l4");
                        l5.getStyleClass().add("l5");
                        l6.getStyleClass().add("l6");
                        l1.getStylesheets().add("mainPageHandler.css");
                        l2.getStylesheets().add("mainPageHandler.css");
                        l3.getStylesheets().add("mainPageHandler.css");
                        l4.getStylesheets().add("mainPageHandler.css");
                        l5.getStylesheets().add("mainPageHandler.css");
                        l6.getStylesheets().add("mainPageHandler.css");



                        h1.getChildren().add(l1);h2.getChildren().add(l2);h3.getChildren().add(l3);h4.getChildren().add(l4);h5.getChildren().add(l5);h6.getChildren().add(l6);
                        card.getChildren().add(h1);
                        card.getChildren().add(h2);
                        card.getChildren().add(h3);
                        card.getChildren().add(h4);
                        card.getChildren().add(h5);
                        card.getChildren().add(h6);

                        container.getChildren().add(card);


                }

        }

        @FXML
        void showPage1(ActionEvent event) {
                if(! mainPane.getChildren().isEmpty()){
                        mainPane.getChildren().remove(0);
                }
                mainPane.getChildren().add(page1);

                for(int i=0 ; i<3;i++) {
                        if(!buttons[i].getStylesheets().isEmpty())
                        buttons[i].getStylesheets().remove(0);
                        buttons[i].getStylesheets().add("mainPageButtonsUnselected.css");
                }
                if(!btn1.getStylesheets().isEmpty())
                        btn1.getStylesheets().remove(0);
                btn1.getStylesheets().add("mainPageButtons.css");
        }

        @FXML
        void showPage2(ActionEvent event) {
                if(! mainPane.getChildren().isEmpty()){
                        mainPane.getChildren().remove(0);
                }
                mainPane.getChildren().add(page2);

                for(int i=0 ; i<3;i++) {
                        if(!buttons[i].getStylesheets().isEmpty())
                                buttons[i].getStylesheets().remove(0);
                        buttons[i].getStylesheets().add("mainPageButtonsUnselected.css");
                }
                if(!btn2.getStylesheets().isEmpty())
                        btn2.getStylesheets().remove(0);
                btn2.getStylesheets().add("mainPageButtons.css");
        }

        @FXML
        void showPage3(ActionEvent event) {
                if(! mainPane.getChildren().isEmpty()){
                        mainPane.getChildren().remove(0);
                }
                mainPane.getChildren().add(page3);

                for(int i=0 ; i<3;i++) {
                        if(!buttons[i].getStylesheets().isEmpty())
                                buttons[i].getStylesheets().remove(0);
                        buttons[i].getStylesheets().add("mainPageButtonsUnselected.css");
                }
                if(!btn3.getStylesheets().isEmpty())
                        btn3.getStylesheets().remove(0);
                btn3.getStylesheets().add("mainPageButtons.css");
        }


        @FXML
        void logout(ActionEvent event) {
                Stage stage = (Stage) mainPane.getScene().getWindow();
                stage.close();
                exit();
        }
}





