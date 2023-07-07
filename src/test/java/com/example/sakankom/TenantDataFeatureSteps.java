package com.example.sakankom;

import com.example.sakankom.dataStructures.Tenant;
import com.example.sakankom.dataStructures.User;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

import java.sql.*;

import static junit.framework.TestCase.assertTrue;

public class TenantDataFeatureSteps {

    User user;
    Tenant tenant;
    public TenantDataFeatureSteps(User user,Tenant tenant) {
        this.user = user;
        user.setUserType("tenant");
        user.setFlag(true);
        user.setUsername("aydi");
        user.setPassword("123");
        
        this.tenant = tenant;

    }

    //The first scenario
    @Given("user is logged in and the user is a tenant")
    public void userIsLoggedInAndTheUserIsATenant() {
        // Write code here that turns the phrase above into concrete actions
        assertTrue(user.getFlag() && user.getUserType().equalsIgnoreCase("tenant"));
    }
    @Then("his personal data should be shown")
    public void hisPersonalDataShouldBeShown() {
        // Write code here that turns the phrase above into concrete actions
        boolean check = false;
        ResultSet rst;
        try {
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@//localhost:1521/xepdb1", "sakankom", "12345678");
            //jdbc:oracle:thin:@//localhost:1521/xepdb1
            Statement st = con.createStatement();
            rst = st.executeQuery("select * from tenant where username = '" + user.getUsername() + "'");

            while (rst.next()){
                check = true;
                tenant.setTenantID(rst.getInt("tenant_id"));
                tenant.setFname(rst.getString("fname"));
                tenant.setLname(rst.getString("lname"));
                tenant.setbDate(rst.getString("birthdate"));
                tenant.setpNumber(rst.getString("phone_number"));
                tenant.setEmail(rst.getString("email"));
                tenant.setJob(rst.getString("job"));
                tenant.setGender(rst.getString("gender"));
                tenant.setUsername(user.getUsername());
                tenant.setPassword(user.getPassword());
            }
            con.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        assertTrue(check);
    }


    //The second scenario
    @Given("the user who is tenant in the profile and presses edit")
    public void theUserWhoIsTenantInTheProfileAndPressesEdit() {
        // Write code here that turns the phrase above into concrete actions
        assertTrue(user.getFlag() && user.getUserType().equalsIgnoreCase("tenant"));
    }
    @Given("user presses save after editing the data")
    public void userPressesSaveAfterEditingTheData() {

    }
    @Then("his data should be updated")
    public void hisDataShouldBeUpdated() {

    }
    //3rd Scenario
    @Given("the tenant has reservations")
    public void theTenantHasReservations() {
        // Write code here that turns the phrase above into concrete actions
    }
    @Then("all of them should be displayed")
    public void allOfThemShouldBeDisplayed() {
        // Write code here that turns the phrase above into concrete actions
    }


}
