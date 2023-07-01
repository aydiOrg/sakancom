package com.example.sakankom;
import com.example.sakankom.dataStructures.Apartment;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

public class HouseDetailsHandler implements Initializable {
        Apartment apartment;
        @FXML
        private HBox container1;

        @FXML
        private HBox container2;

        @FXML
        private Label resName;

        @FXML
        private Label uaddress;

        @FXML
        private Label uaptcount;

        @FXML
        private Label uaptnumber;

        @FXML
        private Label uavailable;

        @FXML
        private Label ubalcony;

        @FXML
        private Label ubathrooms;

        @FXML
        private Label ubedrooms;

        @FXML
        private Label ucapacity;

        @FXML
        private Label uemail;

        @FXML
        private Label ufloor;

        @FXML
        private Label uownername;

        @FXML
        private Label uphone;

        @FXML
        private Label utype;

        public void valuesSetter(Apartment apartment){
                this.apartment = apartment;
        }


        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {

        }
}
