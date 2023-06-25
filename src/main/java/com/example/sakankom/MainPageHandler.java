package com.example.sakankom;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class MainPageHandler implements Initializable {
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

        AnchorPane page1;
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





