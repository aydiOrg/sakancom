package com.example.sakankom;

import com.example.sakankom.dataStructures.AdminReservation;
import com.example.sakankom.dataStructures.Apartment;
import com.example.sakankom.dataStructures.User;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import junit.framework.TestCase;

import java.sql.*;
import java.util.ArrayList;

import static junit.framework.Assert.assertTrue;


public class AdminReservationsManagement {
    User user;
    ArrayList<AdminReservation> adminReservations;

    public AdminReservationsManagement(User user){
        this.user = user;
        adminReservations = new ArrayList<AdminReservation>();
    }

    @Given("the user who is admin is logged in to the system")
    public void theUserWhoIsAdminIsLoggedInToTheSystem() {
        // Write code here that turns the phrase above into concrete actions
        AdminMainPageHandler adminMainPageHandler = Wrapper.signInHandler.adminMainPageHandler;
        user = adminMainPageHandler.getUser();
        if(!user.getUserType().equalsIgnoreCase("admin")){
            TestCase.assertTrue(true);
        }
        else
        assertTrue(user.getFlag() && user.getUserType().equalsIgnoreCase("admin"));
    }

    @Given("the user presses on the reservations button in the sidebar")
    public void theUserPressesOnTheReservationsButtonInTheSidebar() {
        AdminMainPageHandler adminMainPageHandler = Wrapper.signInHandler.adminMainPageHandler;
        user = adminMainPageHandler.getUser();
        if(!user.getUserType().equalsIgnoreCase("admin")){
            TestCase.assertTrue(true);
        }
        else {
            // Write code here that turns the phrase above into concrete actions
            assertTrue(adminMainPageHandler.isReservationsPressed());
        }
    }

    @Then("all of the current available reservations should be displayed")
    public void allOfTheCurrentAvailableReservationsShouldBeDisplayed() {
        AdminMainPageHandler adminMainPageHandler = Wrapper.signInHandler.adminMainPageHandler;
        user = adminMainPageHandler.getUser();
        if (!user.getUserType().equalsIgnoreCase("admin")) {
            TestCase.assertTrue(true);
        } else {
            // Write code here that turns the phrase above into concrete actions
            ResultSet rst, rst2;
            try {

                DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
                Connection con = DriverManager.getConnection("jdbc:oracle:thin:@//localhost:1521/xepdb1", "sakankom", "12345678");
                Statement st = con.createStatement();

                rst2 = st.executeQuery("select * from reservation , tenant , house , residence , owner where reservation.tenant_id = tenant.tenant_id and reservation.house_id = house.house_id and house.residence_id = residence.residence_id and residence.owner_id = owner.owner_id");

                AdminReservation rs;
                while (rst2.next()) {
                    rs = new AdminReservation();
                    rs.setResidenceID(rst2.getInt("residence_id"));
                    rs.setHouseId(rst2.getInt("house_id"));
                    rs.setOwnerId(rst2.getInt("owner_id"));
                    rs.setTenantId(rst2.getInt("tenant_id"));

                    rs.setResidenceName(rst2.getString("residence_name"));
                    rs.setPrice(rst2.getInt("price"));
                    rs.setAddress(rst2.getString("location"));
                    adminReservations.add(rs);
                }

                rst = st.executeQuery("select * from owner");
                while (rst.next()) {
                    for (int i = 0; i < adminReservations.size(); i++) {
                        if (adminReservations.get(i).getOwnerId() == rst.getInt("owner_id")) {
                            adminReservations.get(i).setOwnerName(rst.getString("fname") + " " + rst.getString("lname"));
                            adminReservations.get(i).setOwnerPhone(rst.getString("phone_number"));
                            adminReservations.get(i).setOwnerEmail(rst.getString("email"));
                        }
                    }
                }
                rst.close();

                rst = st.executeQuery("select * from tenant");
                while (rst.next()) {
                    for (int i = 0; i < adminReservations.size(); i++) {
                        if (adminReservations.get(i).getTenantId() == rst.getInt("tenant_id")) {
                            adminReservations.get(i).setTenantName(rst.getString("fname") + " " + rst.getString("lname"));
                            adminReservations.get(i).setTenantPhone(rst.getString("phone_number"));
                            adminReservations.get(i).setTenantEmail(rst.getString("email"));
                        }
                    }
                }


                con.close();
            } catch (SQLException e) {
                e.printStackTrace();

            }

            ArrayList<AdminReservation> adminReservations1 = adminMainPageHandler.getAdminReservations();

            boolean dataValid = true;
            for (int i = 0; i < adminReservations.size(); i++) {
                for (int k = 0; k < adminReservations1.size(); k++) {
                    if (adminReservations.get(i).getHouseId() == adminReservations1.get(k).getHouseId()) {
                        if (adminReservations.get(i).getTenantId() != adminReservations1.get(k).getTenantId() || adminReservations.get(i).getOwnerId() != adminReservations1.get(k).getOwnerId() || adminReservations.get(i).getResidenceID() != adminReservations1.get(k).getResidenceID()) {
                            dataValid = false;
                            break;
                        }
                    }


                }

            }


            assertTrue(dataValid);

        }
    }


}