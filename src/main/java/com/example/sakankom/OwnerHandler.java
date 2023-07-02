package com.example.sakankom;

import com.example.sakankom.OwnerFiles.House;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Collections;

public class OwnerHandler implements Initializable{

    @FXML
    private MFXButton btnAddHouse;

    @FXML
    private MFXButton btnAddResidence;

    @FXML
    private MFXButton btnMain;

    @FXML
    private HBox cardLayout;
    private List<House> recentlyAdded;
    private List<House> recommended;

    private VBox mainBox;

    @FXML
    private AnchorPane page;
    @FXML
    private VBox show;

    @FXML
    private GridPane houseContainer;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        mainBox = new VBox();
        mainBox.getChildren().setAll(show.getChildren());

        try {
            mainBtnHandler(new ActionEvent());
        } catch (IOException e) {

            throw new RuntimeException(e);
        }

    }

    @FXML
    void mainBtnHandler(ActionEvent event) throws IOException {
        if (btnMain.getStyleClass().contains("selected")) {
            System.out.println("Do nothing");
        } else {
            recentlyAdded = new ArrayList<>();
            recommended = new ArrayList<>();
            int column = 1;
            int row = 1;
            ResultSet rst, rst2, rst3, rst4;

            try {
                DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
                Connection con = DriverManager.getConnection("jdbc:oracle:thin:@//localhost:1521/xepdb1", "sakankom", "12345678");
                Statement st = con.createStatement();
                Statement st2 = con.createStatement();
                Statement st3 = con.createStatement();
                Statement st4 = con.createStatement();

                rst = st.executeQuery("select house_id, residence_id, image, price from house where isValid='1'");

                while(rst.next()){
                    rst2 = st2.executeQuery("select residence_name from residence where residence_id = '" + rst.getString("residence_id") + "' and isVAlid='1'");
                    rst2.next();
                    recommended.add(new House(
                            "House " + rst.getString("house_id"),
                            "/photos/" + rst.getString("image"),
                            rst.getInt("price"),
                            rst2.getString("residence_name")
                    ));
                }
                Collections.reverse(recommended);
                if (recommended.size() < 4) {
                    recentlyAdded.addAll(recommended);
                }
                else {
                    for (int j = recommended.size() - 1; j >= recommended.size() / 2; j--) {
                        recentlyAdded.add(recommended.get(recommended.size() - 1 - j));
                    }
                }

                cardLayout.getChildren().clear();
                for (House value : recentlyAdded) {

                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("card.fxml"));
                    HBox cardBox = fxmlLoader.load();

                    CardHandler cardHandler = fxmlLoader.getController();
                    cardHandler.setDate(value);
                    cardLayout.getChildren().add(cardBox);
                }
                Collections.reverse(recommended);
                for (House house : recommended) {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("house.fxml"));
                    VBox houseBox = fxmlLoader.load();
                    houseHandler houseHandler = fxmlLoader.getController();
                    houseHandler.setDate(house);

                    if (column == 6) {
                        column = 0;
                        ++row;
                    }
                    houseContainer.add(houseBox, column++, row);
                    GridPane.setMargin(houseBox, new Insets(10));
                }

                MFXButton foundButton = (MFXButton) page.lookup(".selected");
                if (foundButton != null) {
                    if (foundButton.getStyleClass().equals(btnAddHouse.getStyleClass()))
                        btnAddHouse.getStyleClass().remove("selected");
                    if (foundButton.getStyleClass().equals(btnAddResidence.getStyleClass()))
                        btnAddResidence.getStyleClass().remove("selected");
                }


                show.getChildren().clear();
                show.getChildren().add(mainBox);
                btnMain.getStyleClass().add("selected");

                con.close();
            }catch (Exception e){
                System.out.println(e);
            }
        }
    }

    @FXML
    void addHouseBtnHandler(ActionEvent event) throws IOException {
        if (btnAddHouse.getStyleClass().contains("selected")){
            System.out.println("Do nothing");
        } else {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("addHouse.fxml"));
            VBox showBox = fxmlLoader.load();

            MFXButton foundButton = (MFXButton) page.lookup(".selected");
            if (foundButton != null) {
                if(foundButton.getStyleClass().equals(btnMain.getStyleClass())) btnMain.getStyleClass().remove("selected");
                if(foundButton.getStyleClass().equals(btnAddResidence.getStyleClass())) btnAddResidence.getStyleClass().remove("selected");
            }


            show.getChildren().clear();
            show.getChildren().add(showBox);
            btnAddHouse.getStyleClass().add("selected");
        }
    }

    @FXML
    void addResidenceBtnHandler(ActionEvent event) throws IOException {
        if (btnAddResidence.getStyleClass().contains("selected")){
            System.out.println("Do nothing");
        } else {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("addResidence.fxml"));
            VBox showBox = fxmlLoader.load();

            MFXButton foundButton = (MFXButton) page.lookup(".selected");
            if (foundButton != null) {
                if(foundButton.getStyleClass().equals(btnMain.getStyleClass())) btnMain.getStyleClass().remove("selected");
                if(foundButton.getStyleClass().equals(btnAddHouse.getStyleClass())) btnAddHouse.getStyleClass().remove("selected");
            }


            show.getChildren().clear();
            show.getChildren().add(showBox);
            btnAddResidence.getStyleClass().add("selected");
        }
    }
}