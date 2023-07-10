package com.example.sakankom;

import com.example.sakankom.dataStructures.Apartment;
import com.example.sakankom.dataStructures.Neigbour;
import com.example.sakankom.dataStructures.Tenant;
import com.example.sakankom.dataStructures.User;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

import java.sql.*;
import java.util.ArrayList;

import static junit.framework.TestCase.assertTrue;


public class TenantViewCurrentHouses {
    User user;
    Tenant tenant;
    ArrayList<Apartment> apartments;
    ArrayList<Neigbour> neigbours;

    public TenantViewCurrentHouses (User user, Tenant tenant){
        this.user = user;
        this.tenant = tenant;
        apartments = new ArrayList<Apartment>();
        neigbours = new ArrayList<Neigbour>();
    }

    @Given("the tenant is logged in to the system")
    public void theTenantIsLoggedInToTheSystem() {
        // Write code here that turns the phrase above into concrete actions
        MainPageHandler mainPageHandler = Wrapper.signInHandler.mainPageHandler;
        user = mainPageHandler.getUser();
        tenant = mainPageHandler.getTenant();

        assertTrue(user.getFlag() && user.getUserType().equalsIgnoreCase("tenant"));
    }

    @Given("the tenant presses on apartments")
    public void theTenantPressesOnApartments() {
        // Write code here that turns the phrase above into concrete actions
        MainPageHandler mainPageHandler = Wrapper.signInHandler.mainPageHandler;
        assertTrue(mainPageHandler.isApartementsPressed);
    }

    @Then("all available apartments should be displayed")
    public void allAvailableApartmentsShouldBeDisplayed() {
        ArrayList<Apartment> realApartments = Wrapper.signInHandler.mainPageHandler.currentHousesHandler.apartments;
        neigbours = Wrapper.signInHandler.mainPageHandler.currentHousesHandler.neigbours;
        // Write code here that turns the phrase above into concrete actions
        ResultSet rst;
        try{

            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@//localhost:1521/xepdb1", "sakankom", "12345678");
            //jdbc:oracle:thin:@//localhost:1521/xepdb1
            Statement st = con.createStatement();
            rst = st.executeQuery("select * from house,owner,residence where house.residence_id = residence.residence_id and residence.owner_id = owner.owner_id and house.isvalid = '1' and house.isaccepted = '1'");

            Apartment apt;
            while(rst.next()) {
                apt = new Apartment();
                apt.setOwnerEmail(rst.getString("email"));
                apt.setOwnerPhone(rst.getString("phone_number"));
                apt.setAddress(rst.getString("location"));
                apt.setOwnerName(rst.getString("fname") + " " + rst.getString("lname"));
                apt.setAptName(rst.getString("residence_name"));
                apt.setHouseId(rst.getInt("house_id"));
                apt.setResidenceId(rst.getInt("residence_id"));
                apt.setOwnerId(rst.getInt("owner_id"));
                apt.setBathsN(rst.getInt("bathrooms_number"));
                apt.setBedsN(rst.getInt("bedrooms_number"));
                apt.setServices(rst.getString("services"));
                apt.setPrice(rst.getDouble("price"));
                apt.setFloor(rst.getInt("floor_number"));
                apt.setAptNumber(rst.getInt("flat_number"));
                apt.setCapacity(rst.getInt("capacity"));
                apt.setResCapacity(rst.getInt("reserved_capacity"));
                apt.setGender(rst.getString("genders"));
                apt.setBalcony(rst.getString("balcony"));
                apt.setIsValid(rst.getString("isvalid"));
                apt.setIsAccepted(rst.getString("isaccepted"));
                apt.setIsReserved(rst.getString("isreserved"));
                apartments.add(apt);
            }

            con.close();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        boolean sizeMatches = false;
        boolean dataMatches = false;
        boolean dataGenerated = false;
        int counter = 0;

        if(realApartments.size() == apartments.size())
            sizeMatches = true;

        for (int i =0;i<apartments.size();i++) {
            if(apartments.get(i).getHouseId() == realApartments.get(i).getHouseId() && apartments.get(i).getOwnerId() == realApartments.get(i).getOwnerId() && apartments.get(i).getResidenceId() == realApartments.get(i).getResidenceId()) {
              dataMatches = true;
            }
            if(apartments.get(i).getIsReserved().equals("0")){
                boolean flag = false;
                for (int u =0;u<neigbours.size();u++){
                    if (neigbours.get(u).getTenantID() == tenant.getTenantID() && neigbours.get(u).getHouseID() == apartments.get(i).getHouseId()){
                        flag = true;
                        break;
                    }
                }
                if (flag)
                    continue;
                counter++;
            }
        }

        if(Wrapper.signInHandler.mainPageHandler.currentHousesHandler.container.getChildren().size() == counter) {
            dataGenerated = true;
        }
        System.out.println(sizeMatches + " " + dataMatches + " " + dataGenerated);

        assertTrue(sizeMatches && dataMatches && dataGenerated);
    }

    //Second Scenario ----------------------------
    @Given("the tenant who is logged in to the system presses on apartments")
    public void theTenantWhoIsLoggedInToTheSystemPressesOnApartments() {
        // Write code here that turns the phrase above into concrete actions
        MainPageHandler mainPageHandler = Wrapper.signInHandler.mainPageHandler;
        user = mainPageHandler.getUser();
        tenant = mainPageHandler.getTenant();

        assertTrue(user.getFlag() && user.getUserType().equalsIgnoreCase("tenant") && mainPageHandler.isApartementsPressed);
    }
    @Given("the tenant presses on details of a specific house")
    public void theTenantPressesOnDetailsOfASpecificHouse() {
        // Write code here that turns the phrase above into concrete actions
        CurrentHousesHandler currentHousesHandler = Wrapper.signInHandler.mainPageHandler.currentHousesHandler;
        assertTrue(currentHousesHandler.detailsPressed);
    }
    @Then("the details of the house are shown")
    public void theDetailsOfTheHouseAreShown() {
        // Write code here that turns the phrase above into concrete actions
        Apartment apartment = Wrapper.signInHandler.mainPageHandler.currentHousesHandler.houseDetailsHandler.apartment;
        HouseDetailsHandler houseDetailsHandler = Wrapper.signInHandler.mainPageHandler.currentHousesHandler.houseDetailsHandler;
        neigbours = Wrapper.signInHandler.mainPageHandler.currentHousesHandler.neigbours;
        ResultSet rst, rst2;
        try{

            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@//localhost:1521/xepdb1", "sakankom", "12345678");
            //jdbc:oracle:thin:@//localhost:1521/xepdb1
            Statement st = con.createStatement();
            rst = st.executeQuery("select * from house,owner,residence where house.residence_id = residence.residence_id and residence.owner_id = owner.owner_id and house.isvalid = '1' and house.isaccepted = '1'");

            Apartment apt;
            while(rst.next()) {
                apt = new Apartment();
                apt.setOwnerEmail(rst.getString("email"));
                apt.setOwnerPhone(rst.getString("phone_number"));
                apt.setAddress(rst.getString("location"));
                apt.setOwnerName(rst.getString("fname") + " " + rst.getString("lname"));
                apt.setAptName(rst.getString("residence_name"));
                apt.setHouseId(rst.getInt("house_id"));
                apt.setResidenceId(rst.getInt("residence_id"));
                apt.setOwnerId(rst.getInt("owner_id"));
                apt.setBathsN(rst.getInt("bathrooms_number"));
                apt.setBedsN(rst.getInt("bedrooms_number"));
                apt.setServices(rst.getString("services"));
                apt.setPrice(rst.getDouble("price"));
                apt.setFloor(rst.getInt("floor_number"));
                apt.setAptNumber(rst.getInt("flat_number"));
                apt.setCapacity(rst.getInt("capacity"));
                apt.setResCapacity(rst.getInt("reserved_capacity"));
                apt.setGender(rst.getString("genders"));
                apt.setBalcony(rst.getString("balcony"));
                apt.setIsValid(rst.getString("isvalid"));
                apt.setIsAccepted(rst.getString("isaccepted"));
                apt.setIsReserved(rst.getString("isreserved"));
                apartments.add(apt);
            }


            rst2 = st.executeQuery("select * from tenant, reservation where reservation.tenant_id = tenant.tenant_id");

            Neigbour neigbour;
            while (rst2.next()) {
                neigbour = new Neigbour();
                neigbour.setHouseID(rst2.getInt("house_id"));
                neigbour.setJob(rst2.getString("job"));
                neigbour.setTenantID(rst2.getInt("tenant_id"));
                neigbour.setName(rst2.getString("fname") + " " + rst2.getString("lname"));

                neigbours.add(neigbour);
            }

            con.close();
        }
        catch (SQLException e){
            e.printStackTrace();
        }

        boolean apartmentExists = false;
        boolean neigboursExist = false;

        for (int i=0;i<apartments.size(); i++) {
            if (apartments.get(i).getHouseId() == apartment.getHouseId() && apartments.get(i).getResidenceId() == apartment.getResidenceId() && apartments.get(i).getOwnerId() == apartment.getOwnerId()) {
                apartmentExists = true;
            }
        }
        int counter =0;
        for (int i = 0;i<neigbours.size();i++) {
            if (neigbours.get(i).getHouseID() == apartment.getHouseId() && neigbours.get(i).getTenantID() != tenant.getTenantID()){
                counter++;
            }
        }

        if(houseDetailsHandler.container1.getChildren().size() == counter) {
            neigboursExist = true;
        }




        assertTrue(apartmentExists && neigboursExist);

    }

    //The 3rd Scenario
    @Given("the tenant who is logged in presses on apartments")
    public void theTenantWhoIsLoggedInPressesOnApartments() {
        // Write code here that turns the phrase above into concrete actions
        MainPageHandler mainPageHandler = Wrapper.signInHandler.mainPageHandler;
        user = mainPageHandler.getUser();
        tenant = mainPageHandler.getTenant();

        assertTrue(user.getFlag() && user.getUserType().equalsIgnoreCase("tenant") && mainPageHandler.isApartementsPressed);
    }
    @Given("the tenant presses on the reserve button of any house")
    public void theTenantPressesOnTheReserveButtonOfAnyHouse() {
        // Write code here that turns the phrase above into concrete actions
        CurrentHousesHandler currentHousesHandler = Wrapper.signInHandler.mainPageHandler.currentHousesHandler;
        assertTrue(currentHousesHandler.reservePressed);
    }
    @Then("a reservation is created")
    public void aReservationIsCreated() {
        // Write code here that turns the phrase above into concrete actions
        CurrentHousesHandler currentHousesHandler = Wrapper.signInHandler.mainPageHandler.currentHousesHandler;
        Apartment myApartment = currentHousesHandler.myApartment;
        boolean reservationExists = false;


        ResultSet rst;
        try {

            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@//localhost:1521/xepdb1", "sakankom", "12345678");
            //jdbc:oracle:thin:@//localhost:1521/xepdb1
            Statement st = con.createStatement();
            rst = st.executeQuery("select * from reservation where house_id = '" + myApartment.getHouseId() + "' and tenant_id = '" + tenant.getTenantID() +"'");

            if (rst.next()) {
                reservationExists = true;
            }

            con.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        assertTrue(reservationExists);
    }




}
