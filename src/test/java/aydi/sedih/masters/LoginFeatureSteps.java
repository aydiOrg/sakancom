package aydi.sedih.masters;

import com.example.sakankom.dataStructures.User;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.datatable.DataTable;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

public class LoginFeatureSteps {
    User user;
    ArrayList<User> users;
    public LoginFeatureSteps(User user) {
        super();
        this.user = user;
        user.setUsername("aydi");
        user.setPassword("123");
    }
    @Before
    public void fetchData() {
        users = new ArrayList<User>();
        ResultSet rst, rst2, rst3;
        try {
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@//localhost:1521/xepdb1", "sakankom", "12345678");
            Statement st = con.createStatement();
            rst = st.executeQuery("select username, pass from tenant ");

            //bringing tenants
            while (rst.next()) {
                users.add(new User(rst.getString("username"), rst.getString("pass"), "tenant",false));
            }
            rst2 = st.executeQuery("select username, pass from owner ");
            //bringing owners
            while (rst2.next()) {
                users.add(new User(rst2.getString("username"), rst2.getString("pass"), "owner",false));
            }
            rst3 = st.executeQuery("select username, pass from admin ");
            //bringing admins
            while (rst3.next()) {
                users.add(new User(rst3.getString("username"), rst3.getString("pass"), "admin", false));
            }
            con.close();

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    //The cases..
    @Given("the user is not logged in")
    public void theUserIsNotLoggedIn() {
        // Write code here that turns the phrase above into concrete actions
        user.setFlag(false);
    }
    @Given("the password is equal to <username> and the username is equal to <password>")
    public void thePasswordIsEqualToUsernameAndTheUsernameIsEqualToPassword(DataTable dataTable) {
        // Write code here that turns the phrase above into concrete actions
        // For automatic transformation, change DataTable to one of
        // E, List<E>, List<List<E>>, List<Map<K,V>>, Map<K,V> or
        // Map<K, List<V>>. E,K,V must be a String, Integer, Float,
        // Double, Byte, Short, Long, BigInteger or BigDecimal.
        //
        // For other transformations you can register a DataTableType.
        //        List <List<String>> a = dataTable.asLists();
        //        System.out.println(a.get(1).get(0) + "");
        boolean flag = false;
        for (int i=0 ; i< users.size();i++) {
            if (user.getUsername().equalsIgnoreCase(users.get(i).getUsername()) && user.getPassword().equals(users.get(i).getPassword())) {
                flag = true;
            }
        }

        assertTrue(flag);
    }
    @Then("the user should log in to the system")
    public void theUserShouldLogInToTheSystem() {
        // Write code here that turns the phrase above into concrete actions
        boolean flag = false;
        for (int i=0 ; i< users.size();i++) {
            if (user.getUsername().equalsIgnoreCase(users.get(i).getUsername()) && user.getPassword().equals(users.get(i).getPassword())) {
                flag = true;
                users.get(i).setFlag(true);
                user.setFlag(true);
            }
        }

        assertTrue(flag);
    }
    @Then("the user type is determined")
    public void theUserTypeIsDetermined() {
        // Write code here that turns the phrase above into concrete actions
        boolean flag = false;
        for (int i=0 ; i< users.size();i++) {
            if (user.getUsername().equalsIgnoreCase(users.get(i).getUsername()) && user.getPassword().equals(users.get(i).getPassword())) {
                flag = true;
                String s = users.get(0).getUserType();

                if(s.equals("tenant"))
                    users.get(i).setUserType("tenant");
                else if(s.equals("owner"))
                    users.get(i).setUserType("owner");
                else if(s.equals("admin"))
                    users.get(i).setUserType("admin");
            }
        }

        assertTrue(flag);
    }
    @Then("A page opens which is related to the user type")
    public void aPageOpensWhichIsRelatedToTheUserType() {
        // Write code here that turns the phrase above into concrete actions
        assertTrue(user.getFlag());
    }

    //second scenario
    @Given("user not logged in")
    public void userNotLoggedIn() {
        // Write code here that turns the phrase above into concrete actions
        user.setFlag(false);
    }
    @Given("the password is not equal to <username> or the username is not equal to <password>")
    public void thePasswordIsNotEqualToUsernameOrTheUsernameIsNotEqualToPassword() {
        boolean flag = false;
        for(int i=0;i<users.size();i++) {
            if(users.get(i).getUsername().equalsIgnoreCase(user.getUsername()) && users.get(i).getPassword().equals(user.getPassword())){
                flag = true;
            }
        }

        assertFalse("The username and password are valid",flag);
    }
    @Then("show a message indicating that the entered data is false")
    public void showAMessageIndicatingThatTheEnteredDataIsFalse() {
        // Write code here that turns the phrase above into concrete actions
        user.setFlag(false);
    }




}
