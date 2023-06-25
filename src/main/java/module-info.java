module com.example.sakankom {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires MaterialFX;

    opens com.example.sakankom to javafx.fxml;
    exports com.example.sakankom;
}