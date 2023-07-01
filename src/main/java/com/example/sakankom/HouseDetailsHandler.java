package com.example.sakankom;
import com.example.sakankom.dataStructures.Apartment;
import com.example.sakankom.dataStructures.Neigbour;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class HouseDetailsHandler implements Initializable {
        Apartment apartment;
        @FXML
        private HBox container1;


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
        private Label uservices;

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

        public void valuesSetter(Apartment apartment, ArrayList<Apartment> apartments, ArrayList<Neigbour> neigbours){
                this.apartment = apartment;
                resName.setText(apartment.getAptName());
                uaddress.setText(apartment.getAddress());
                ufloor.setText(Integer.toString(apartment.getFloor()));
                uaptnumber.setText(Integer.toString(apartment.getAptNumber()));

                int count = 0;
                for (int i=0;i<apartments.size();i++){
                        if(apartments.get(i).getHouseId() != apartment.getHouseId() && apartments.get(i).getResidenceId() == apartment.getResidenceId() && apartments.get(i).getFloor() == apartment.getFloor()) {
                                count++;
                        }
                }

                uaptcount.setText("" + count); // needs query
                uservices.setText(apartment.getServices());
                ubathrooms.setText(Integer.toString(apartment.getBathsN()));
                String s= (apartment.getBalcony().equals("1")) ? "yes" : "no";
                ubalcony.setText(s);
                ubedrooms.setText(Integer.toString(apartment.getBedsN()));
                s = (apartment.getCapacity() > 1) ? "Shared" :"solo" ;
                utype.setText(s + " room");
                ucapacity.setText(Integer.toString(apartment.getCapacity()));
                uavailable.setText(Integer.toString(apartment.getCapacity() - apartment.getResCapacity()));
                uownername.setText(apartment.getOwnerName());
                uphone.setText(apartment.getOwnerPhone());
                uemail.setText(apartment.getOwnerEmail());

                //neighbours generating

                String name= "";
                String job = "";

                for (int i = 0;i<neigbours.size();i++) {
                        if(neigbours.get(i).getHouseID() == apartment.getHouseId()) {
                                name = neigbours.get(i).getName();
                                job = neigbours.get(i).getJob();
                                //filling the reservations
                                VBox card;
                                Label l1, l2;
                                HBox h1, h2;

                                //generating elements
                                card = new VBox();
                                card.setMaxWidth(230);
                                card.setPrefWidth(230);
                                card.setMinWidth(230);

                                h1 = new HBox();
                                h2 = new HBox();


                                l1 = new Label(name);
                                l2 = new Label(job);


                                DoubleProperty fontSize = new SimpleDoubleProperty(16);
                                l1.styleProperty().bind(Bindings.format("-fx-font-size: %.2fpt;", fontSize));
                                DoubleProperty fontSize2 = new SimpleDoubleProperty(13);
                                l2.styleProperty().bind(Bindings.format("-fx-font-size: %.2fpt;", fontSize2));
                                card.getStyleClass().add("hbox");
                                card.getStylesheets().add("mainPageHandler.css");

                                l1.getStyleClass().add("l1");
                                l2.getStyleClass().add("l2");
                                l1.getStylesheets().add("mainPageHandler.css");
                                l2.getStylesheets().add("mainPageHandler.css");

                                h1.getChildren().add(l1);
                                h2.getChildren().add(l2);
                                card.getChildren().add(h1);
                                card.getChildren().add(h2);

                                container1.getChildren().add(card);
                        }
                }




        }


        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {

        }
}
