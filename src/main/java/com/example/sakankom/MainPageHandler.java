package com.example.sakankom;
import com.example.sakankom.dataStructures.Tenant;
import com.example.sakankom.dataStructures.User;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class MainPageHandler implements Initializable {
        //data from the sign in
        User user;
        Tenant tenant;
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

                //set the labels
                uName.setText(tenant.getFname() + tenant.getLname());
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
        private Button btn2;

        @FXML
        private Button btn3;

        @FXML
        private Button btn4;

        @FXML
        private Button btn5;

        @FXML
        private AnchorPane mainPane;


        AnchorPane page2;
        AnchorPane page3;
        AnchorPane page4;
        AnchorPane page5;

        Button[] buttons = new Button[5];


        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {
                buttons[0] = btn1;buttons[1] = btn2;buttons[2] = btn3;buttons[3] = btn4;buttons[4] = btn5;
        }

        @FXML
        void showPage1(ActionEvent event) {
//                if(! mainPane.getChildren().isEmpty()){
//                        mainPane.getChildren().remove(0);
//                }
//                mainPane.getChildren().add(page1);

                for(int i=0 ; i<5;i++) {
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
                for(int i=0 ; i<5;i++) {
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
                for(int i=0 ; i<5;i++) {
                        if(!buttons[i].getStylesheets().isEmpty())
                                buttons[i].getStylesheets().remove(0);
                        buttons[i].getStylesheets().add("mainPageButtonsUnselected.css");
                }
                if(!btn3.getStylesheets().isEmpty())
                        btn3.getStylesheets().remove(0);
                btn3.getStylesheets().add("mainPageButtons.css");
        }

        @FXML
        void showPage4(ActionEvent event) {
                for(int i=0 ; i<5;i++) {
                        if(!buttons[i].getStylesheets().isEmpty())
                                buttons[i].getStylesheets().remove(0);
                        buttons[i].getStylesheets().add("mainPageButtonsUnselected.css");
                }
                if(!btn4.getStylesheets().isEmpty())
                        btn4.getStylesheets().remove(0);
                btn4.getStylesheets().add("mainPageButtons.css");
        }

        @FXML
        void showPage5(ActionEvent event) {
                for(int i=0 ; i<5;i++) {
                        if(!buttons[i].getStylesheets().isEmpty())
                                buttons[i].getStylesheets().remove(0);
                        buttons[i].getStylesheets().add("mainPageButtonsUnselected.css");
                }
                if(!btn5.getStylesheets().isEmpty())
                        btn5.getStylesheets().remove(0);
                btn5.getStylesheets().add("mainPageButtons.css");
        }


}





