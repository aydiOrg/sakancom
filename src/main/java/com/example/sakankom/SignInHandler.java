package com.example.sakankom;
import com.example.sakankom.dataStructures.User;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


import javax.sql.StatementEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class SignInHandler implements Initializable {
    @FXML
    public MFXTextField password;
    @FXML
    private AnchorPane mainPane;
    @FXML
    public MFXTextField username;
    @FXML
    private MFXButton signBtn;

    Stage newStage = new Stage();
    Stage currentStage = new Stage();
    ArrayList<User> users ;
    public boolean isUserLoggedIn;



    public ArrayList<User> getUsers(){
        return users;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        isUserLoggedIn = false;
        users = new ArrayList<User>();
        ResultSet rst, rst2, rst3;
        try {
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@//localhost:1521/xepdb1", "sakankom", "12345678");
            //jdbc:oracle:thin:@//localhost:1521/xepdb1
            Statement st = con.createStatement();
            rst = st.executeQuery("select username, pass from tenant ");



            //bringing tenants
            while (rst.next()) {
                System.out.println(rst.getString("username"));
                users.add(new User(rst.getString("username"), rst.getString("pass"), "tenant",false));
            }
            rst2 = st.executeQuery("select username, pass from owner ");
            //bringing owners
            while (rst2.next()) {
                System.out.println(rst2.getString("username"));
                users.add(new User(rst2.getString("username"), rst2.getString("pass"), "owner",false));
            }
            rst3 = st.executeQuery("select username, pass from admin ");
            //bringing admins
            while (rst3.next()) {
                System.out.println(rst3.getString("username"));
                users.add(new User(rst3.getString("username"), rst3.getString("pass"), "admin",false));
            }
            con.close();

        }catch (SQLException e){
            e.printStackTrace();
        }


        //loading the fxml pages



    }
    @FXML
    void validator(ActionEvent event) {
        String u = username.getText();
        String p = password.getText();

        for (int i=0 ; i< users.size();i++) {
            User user = users.get(i);
            if(user.getPassword().equals(p) && user.getUsername().equalsIgnoreCase(u)) {
                user.setFlag(true);
                if (user.getUserType().equals("tenant")) {
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("Main-Page.fxml"));
                        Parent root = loader.load();
                        Scene scene = new Scene(root);
                        newStage.setScene(scene);
                        newStage.show();

                        MainPageHandler mainPageHandler = loader.getController();
                        mainPageHandler.setUser(user);
                        mainPageHandler.setTenantData(user);
                        mainPageHandler.manageReservations();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                }
                else if (user.getUserType().equals("owner")) {

                }
                else if (user.getUserType().equals("admin")) {

                }

                isUserLoggedIn = true;
                //closing the current stage
                currentStage = (Stage) mainPane.getScene().getWindow();
                currentStage.close();
            }

        }


    }





}
