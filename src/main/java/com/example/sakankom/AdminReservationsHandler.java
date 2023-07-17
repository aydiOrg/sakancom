package com.example.sakankom;
import io.github.palexdev.materialfx.controls.MFXScrollPane;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminReservationsHandler implements Initializable {
        @FXML
        private VBox container;
        @FXML
        private MFXScrollPane mainPane;

        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {

        }

        public MFXScrollPane getMainPane() {
            return mainPane;
        }
}
