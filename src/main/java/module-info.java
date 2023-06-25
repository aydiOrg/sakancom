module com.example.sakankom {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires MaterialFX;
    requires java.sql;
    requires com.oracle.database.jdbc;

    opens com.example.sakankom to javafx.fxml;
    exports com.example.sakankom;
}