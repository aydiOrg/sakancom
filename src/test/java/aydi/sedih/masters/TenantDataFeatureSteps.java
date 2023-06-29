package aydi.sedih.masters;

import com.example.sakankom.dataStructures.User;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

import static junit.framework.TestCase.assertTrue;

public class TenantDataFeatureSteps {

    User user;
    public TenantDataFeatureSteps(User user) {
        this.user = user;
        user.setUserType("tenant");
        user.setFlag(true);
        user.setUsername("aydi");
        user.setPassword("123");
    }

    //The first scenario
    @Given("user is logged in and the user is a tenant")
    public void userIsLoggedInAndTheUserIsATenant() {
        // Write code here that turns the phrase above into concrete actions
        assertTrue(user.getFlag() && user.getUserType().equalsIgnoreCase("tenant"));
    }
    @Given("the user selects the profile from the menu")
    public void theUserSelectsTheProfileFromTheMenu() {
        // Write code here that turns the phrase above into concrete actions
        
    }
    @Then("his personal data should be shown")
    public void hisPersonalDataShouldBeShown() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }


    //The second scenario
    @Given("the user who is tenant in the profile and presses edit")
    public void theUserWhoIsTenantInTheProfileAndPressesEdit() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
    @Given("user presses save after editing the data")
    public void userPressesSaveAfterEditingTheData() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
    @Then("his data should be updated")
    public void hisDataShouldBeUpdated() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

}
