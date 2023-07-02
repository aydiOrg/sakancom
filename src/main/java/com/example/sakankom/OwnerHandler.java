package com.example.sakankom;

import com.example.sakankom.OwnerFiles.CardHandler;
import com.example.sakankom.OwnerFiles.House;
import com.example.sakankom.OwnerFiles.houseHandler;
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
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class OwnerHandler implements Initializable{

    @FXML
    private MFXButton btnAddHouse;

    @FXML
    private MFXButton btnCloseHouse;

    @FXML
    private MFXButton btnComments;

    @FXML
    private MFXButton btnFavorites;

    @FXML
    private MFXButton btnHistory;

    @FXML
    private MFXButton btnHouses;


    @FXML
    private MFXButton btnMain;

    @FXML
    private MFXButton btnTopHouses;
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
            System.out.println("hello");
            throw new RuntimeException(e);
        }

    }
    private List<House> recentlyAdded(){
        List<House> ls = new ArrayList<>();
        House house = new House();
        house.setName("House 111181");
        house.setImageSrc("/photos/a1.jpeg");
        house.setRes("11");
        ls.add(house);

        House house1 = new House();
        house1.setName("House 111170");
        house1.setImageSrc("/photos/a2.jpeg");
        house1.setRes("11");
        ls.add(house1);

        House house2 = new House();
        house2.setName("House 220120");
        house2.setImageSrc("/photos/a3.jpeg");
        house2.setRes("22");
        ls.add(house2);

        return ls;
    }
    private List<House> houses(){
        List<House> ls = new ArrayList<>();
        House house = new House();
        house.setName("House 111181");
        house.setImageSrc("/photos/a1.jpeg");
        house.setRes("11");
        ls.add(house);

        House house1 = new House();
        house1.setName("House 111170");
        house1.setImageSrc("/photos/a2.jpeg");
        house1.setRes("11");
        ls.add(house1);

        House house2 = new House();
        house2.setName("House 220120");
        house2.setImageSrc("/photos/a3.jpeg");
        house2.setRes("22");
        ls.add(house2);

        return ls;
    }

    @FXML
    void mainBtnHandler(ActionEvent event) throws IOException {
        if (btnMain.getStyleClass().contains("selected")) {
            System.out.println("Do nothing");
        } else {
            recentlyAdded = new ArrayList<>(recentlyAdded());
            recommended = new ArrayList<>(houses());
            int column = 1;
            int row = 1;

            try {
                cardLayout.getChildren().clear();
                for (House value : recentlyAdded) {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("card.fxml"));
                    HBox cardBox = fxmlLoader.load();
                    CardHandler cardHandler = fxmlLoader.getController();
                    cardHandler.setDate(value);
                    cardLayout.getChildren().add(cardBox);
                }

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
                    if (foundButton.getStyleClass().equals(btnTopHouses.getStyleClass()))
                        btnTopHouses.getStyleClass().remove("selected");
                    if (foundButton.getStyleClass().equals(btnComments.getStyleClass()))
                        btnComments.getStyleClass().remove("selected");
                    if (foundButton.getStyleClass().equals(btnFavorites.getStyleClass()))
                        btnFavorites.getStyleClass().remove("selected");
                    if (foundButton.getStyleClass().equals(btnHouses.getStyleClass()))
                        btnHouses.getStyleClass().remove("selected");
                    if (foundButton.getStyleClass().equals(btnHistory.getStyleClass()))
                        btnHistory.getStyleClass().remove("selected");
                    if (foundButton.getStyleClass().equals(btnAddHouse.getStyleClass()))
                        btnAddHouse.getStyleClass().remove("selected");
                    if (foundButton.getStyleClass().equals(btnCloseHouse.getStyleClass()))
                        btnCloseHouse.getStyleClass().remove("selected");
                }


                show.getChildren().clear();
                show.getChildren().add(mainBox);
                btnMain.getStyleClass().add("selected");
            }catch (Exception e ){
                System.out.println(e);
            }
        }
    }
    @FXML
    void topHousesBtnHandler(ActionEvent event) throws IOException {
        if (btnTopHouses.getStyleClass().contains("selected")){
            System.out.println("Do nothing");
        } else {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("house.fxml"));
            VBox showBox = fxmlLoader.load();

            show.getChildren().clear();
            show.getChildren().add(showBox);

            MFXButton foundButton = (MFXButton) page.lookup(".selected");
            if (foundButton != null) {
                if(foundButton.getStyleClass().equals(btnMain.getStyleClass())) btnMain.getStyleClass().remove("selected");
                if(foundButton.getStyleClass().equals(btnComments.getStyleClass())) btnComments.getStyleClass().remove("selected");
                if(foundButton.getStyleClass().equals(btnFavorites.getStyleClass())) btnFavorites.getStyleClass().remove("selected");
                if(foundButton.getStyleClass().equals(btnHouses.getStyleClass())) btnHouses.getStyleClass().remove("selected");
                if(foundButton.getStyleClass().equals(btnHistory.getStyleClass())) btnHistory.getStyleClass().remove("selected");
                if(foundButton.getStyleClass().equals(btnAddHouse.getStyleClass())) btnAddHouse.getStyleClass().remove("selected");
                if(foundButton.getStyleClass().equals(btnCloseHouse.getStyleClass())) btnCloseHouse.getStyleClass().remove("selected");
            }

            btnTopHouses.getStyleClass().add("selected");
        }
    }
    @FXML
    void commentsBtnHandler(ActionEvent event) throws IOException {
        if (btnComments.getStyleClass().contains("selected")){
            System.out.println("Do nothing");
        } else {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("Comments.fxml"));
            VBox showBox = fxmlLoader.load();

            MFXButton foundButton = (MFXButton) page.lookup(".selected");
            if (foundButton != null) {
                if(foundButton.getStyleClass().equals(btnMain.getStyleClass())) btnMain.getStyleClass().remove("selected");
                if(foundButton.getStyleClass().equals(btnTopHouses.getStyleClass())) btnTopHouses.getStyleClass().remove("selected");
                if(foundButton.getStyleClass().equals(btnFavorites.getStyleClass())) btnFavorites.getStyleClass().remove("selected");
                if(foundButton.getStyleClass().equals(btnHouses.getStyleClass())) btnHouses.getStyleClass().remove("selected");
                if(foundButton.getStyleClass().equals(btnHistory.getStyleClass())) btnHistory.getStyleClass().remove("selected");
                if(foundButton.getStyleClass().equals(btnAddHouse.getStyleClass())) btnAddHouse.getStyleClass().remove("selected");
                if(foundButton.getStyleClass().equals(btnCloseHouse.getStyleClass())) btnCloseHouse.getStyleClass().remove("selected");
            }


            show.getChildren().clear();
            show.getChildren().add(showBox);
            btnComments.getStyleClass().add("selected");
        }
    }
    @FXML
    void addHouseBtnHandler(ActionEvent event) throws IOException {
        if (btnAddHouse.getStyleClass().contains("selected")){
            System.out.println("Do nothing");
        } else {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("Comments.fxml"));
            VBox showBox = fxmlLoader.load();

            MFXButton foundButton = (MFXButton) page.lookup(".selected");
            if (foundButton != null) {
                if(foundButton.getStyleClass().equals(btnMain.getStyleClass())) btnMain.getStyleClass().remove("selected");
                if(foundButton.getStyleClass().equals(btnTopHouses.getStyleClass())) btnTopHouses.getStyleClass().remove("selected");
                if(foundButton.getStyleClass().equals(btnFavorites.getStyleClass())) btnFavorites.getStyleClass().remove("selected");
                if(foundButton.getStyleClass().equals(btnHouses.getStyleClass())) btnHouses.getStyleClass().remove("selected");
                if(foundButton.getStyleClass().equals(btnComments.getStyleClass())) btnComments.getStyleClass().remove("selected");
                if(foundButton.getStyleClass().equals(btnHistory.getStyleClass())) btnHistory.getStyleClass().remove("selected");
                if(foundButton.getStyleClass().equals(btnCloseHouse.getStyleClass())) btnCloseHouse.getStyleClass().remove("selected");
            }


            show.getChildren().clear();
            show.getChildren().add(showBox);
            btnAddHouse.getStyleClass().add("selected");
        }
    }

    @FXML
    void closeHouseBtnHandler(ActionEvent event) {

    }



    @FXML
    void favoritesBtnHandler(ActionEvent event) throws IOException {
        if (btnFavorites.getStyleClass().contains("selected")){
            System.out.println("Do nothing");
        } else {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("house.fxml"));
            VBox showBox = fxmlLoader.load();

            show.getChildren().clear();
            show.getChildren().add(showBox);

            MFXButton foundButton = (MFXButton) page.lookup(".selected");
            if (foundButton != null) {
                if(foundButton.getStyleClass().equals(btnMain.getStyleClass())) btnMain.getStyleClass().remove("selected");
                if(foundButton.getStyleClass().equals(btnComments.getStyleClass())) btnComments.getStyleClass().remove("selected");
                if(foundButton.getStyleClass().equals(btnHouses.getStyleClass())) btnHouses.getStyleClass().remove("selected");
                if(foundButton.getStyleClass().equals(btnTopHouses.getStyleClass())) btnTopHouses.getStyleClass().remove("selected");
                if(foundButton.getStyleClass().equals(btnHistory.getStyleClass())) btnHistory.getStyleClass().remove("selected");
                if(foundButton.getStyleClass().equals(btnAddHouse.getStyleClass())) btnAddHouse.getStyleClass().remove("selected");
                if(foundButton.getStyleClass().equals(btnCloseHouse.getStyleClass())) btnCloseHouse.getStyleClass().remove("selected");
            }

            btnFavorites.getStyleClass().add("selected");
        }
    }

    @FXML
    void historyBtnHandler(ActionEvent event) throws IOException {
        if (btnHistory.getStyleClass().contains("selected")){
            System.out.println("Do nothing");
        } else {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("Comments.fxml"));
            VBox showBox = fxmlLoader.load();

            MFXButton foundButton = (MFXButton) page.lookup(".selected");
            if (foundButton != null) {
                if(foundButton.getStyleClass().equals(btnMain.getStyleClass())) btnMain.getStyleClass().remove("selected");
                if(foundButton.getStyleClass().equals(btnTopHouses.getStyleClass())) btnTopHouses.getStyleClass().remove("selected");
                if(foundButton.getStyleClass().equals(btnFavorites.getStyleClass())) btnFavorites.getStyleClass().remove("selected");
                if(foundButton.getStyleClass().equals(btnHouses.getStyleClass())) btnHouses.getStyleClass().remove("selected");
                if(foundButton.getStyleClass().equals(btnComments.getStyleClass())) btnComments.getStyleClass().remove("selected");
                if(foundButton.getStyleClass().equals(btnAddHouse.getStyleClass())) btnAddHouse.getStyleClass().remove("selected");
                if(foundButton.getStyleClass().equals(btnCloseHouse.getStyleClass())) btnCloseHouse.getStyleClass().remove("selected");
            }


            show.getChildren().clear();
            show.getChildren().add(showBox);
            btnHistory.getStyleClass().add("selected");
        }
    }

    @FXML
    void housesBtnHandler(ActionEvent event) throws IOException {
        if (btnHouses.getStyleClass().contains("selected")){
            System.out.println("Do nothing");
        } else {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("house.fxml"));
            VBox showBox = fxmlLoader.load();

            show.getChildren().clear();
            show.getChildren().add(showBox);

            MFXButton foundButton = (MFXButton) page.lookup(".selected");
            if (foundButton != null) {
                if(foundButton.getStyleClass().equals(btnMain.getStyleClass())) btnMain.getStyleClass().remove("selected");
                if(foundButton.getStyleClass().equals(btnComments.getStyleClass())) btnComments.getStyleClass().remove("selected");
                if(foundButton.getStyleClass().equals(btnFavorites.getStyleClass())) btnFavorites.getStyleClass().remove("selected");
                if(foundButton.getStyleClass().equals(btnTopHouses.getStyleClass())) btnTopHouses.getStyleClass().remove("selected");
                if(foundButton.getStyleClass().equals(btnHistory.getStyleClass())) btnHistory.getStyleClass().remove("selected");
                if(foundButton.getStyleClass().equals(btnAddHouse.getStyleClass())) btnAddHouse.getStyleClass().remove("selected");
                if(foundButton.getStyleClass().equals(btnCloseHouse.getStyleClass())) btnCloseHouse.getStyleClass().remove("selected");
            }

            btnHouses.getStyleClass().add("selected");
        }
    }

}
