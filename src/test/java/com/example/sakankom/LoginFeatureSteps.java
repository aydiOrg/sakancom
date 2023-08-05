package com.example.sakankom;

import com.example.sakankom.dataStructures.User;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

public class LoginFeatureSteps {
    private User user;
    boolean userValid , userTypeDetermined;

    public LoginFeatureSteps(User user) {
        super();
        this.user = user;
        userValid = false;
        userTypeDetermined = false;

    }
    @BeforeAll
    public static void before_or_after_all(){
        String[] ff = {"222","11"};
        Wrapper.main(ff);
    }

    //The cases..
    @Given("the user is not logged in")
    public void theUserIsNotLoggedIn() {
        user.setFlag(false);
        assertFalse(user.getFlag());
    }
    @Given("the username is equal to <username> and the username is equal to <password>")
    public void theUsernameIsEqualToUsernameAndTheUsernameIsEqualToPassword(DataTable dataTable) {
        List<List<String>> myList = dataTable.asLists();
        ArrayList<User> values = Wrapper.signInHandler.getValues();

        for(int i = 1;i< myList.size();i++) {
            for (User value : values) {
                user.setUsername(value.getUsername());
                user.setPassword(value.getPassword());
                if (myList.get(i).get(0).equals(user.getUsername()) && myList.get(i).get(1).equals(user.getPassword())) {
                    userValid = true;
                    break;
                }

            }
            if (userValid)  break;
        }

        assertTrue(userValid);
    }

    @Then("the user should log in to the system")
    public void theUserShouldLogInToTheSystem() {
        assertTrue(Wrapper.signInHandler.isUserLoggedIn);
    }
    @Then("the user type is determined")
    public void theUserTypeIsDetermined() {
        for (int i = 0; i< Wrapper.signInHandler.getUsers().size(); i++) {
            if (Wrapper.signInHandler.getUsers().get(i).getUsername().equals(user.getUsername())) {
                user.setUserType(Wrapper.signInHandler.getUsers().get(i).getUserType());
                userTypeDetermined = true;
                break;
            }

        }
        assertTrue(userTypeDetermined);
    }

    //second scenario --------------------------------------------
    @Given("user not logged in")
    public void userNotLoggedIn() {
        user.setFlag(false);
        assertFalse(user.getFlag());
    }
    @Given("the password is not equal to <username> or the username is not equal to <password>")
    public void thePasswordIsNotEqualToUsernameOrTheUsernameIsNotEqualToPassword(DataTable dataTable) {
        boolean userValid2 = true;
        List<List<String>> myList = dataTable.asLists();
        ArrayList<User> values = Wrapper.signInHandler.getValues();

        for(int i = 1;i< myList.size();i++) {
            for (User value : values) {
                user.setUsername(value.getUsername());
                user.setPassword(value.getPassword());
                if (!myList.get(i).get(0).equals(user.getUsername()) && !myList.get(i).get(1).equals(user.getPassword())) {
                    userValid2 = false;
                    break;
                }

            }
            if (!userValid2)  break;
        }

        assertFalse(userValid2);
    }
    @Then("show a message indicating that the entered data is false")
    public void showAMessageIndicatingThatTheEnteredDataIsFalse() {
        if(Wrapper.signInHandler.alertShown) assertTrue(true);
        else assertTrue(Wrapper.signInHandler.loginPageClosed);
    }

    //3rd Scenario
    @Given("the user presses on logout")
    public void theUserPressesOnLogout() {
        assertTrue(Wrapper.signInHandler.mainPageHandler.logoutPressed || Wrapper.signInHandler.adminMainPageHandler. isLogoutBtnPressed());
    }
    @Then("the user should be logged out of the system")
    public void theUserShouldBeLoggedOutOfTheSystem() {
        assertTrue(Wrapper.signInHandler.mainPageHandler.loggedOut || Wrapper.signInHandler.adminMainPageHandler. isLogoutBtnPressed());
    }

}
