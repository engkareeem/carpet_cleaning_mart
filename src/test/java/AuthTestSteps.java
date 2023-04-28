import com.carpetcleaningmart.Utils.Auth;
import com.carpetcleaningmart.Utils.DBApi;
import com.carpetcleaningmart.model.User;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.After;

import static org.junit.Assert.*;

public class AuthTestSteps {

    private User user;

    @Before
    public void setUp() throws Exception {
        user = null;
    }

    @After
    public void tearDown() throws Exception {
        DBApi.deleteTestCustomer();
    }

    @Given("^the user does not have an account$")
    public void the_user_does_not_have_an_account() {
        assertNull(Auth.getCurrentUser());
    }

    @When("^the user signs up with email \"([^\"]*)\", name \"([^\"]*)\", address \"([^\"]*)\", phone \"([^\"]*)\", and password \"([^\"]*)\"$")
    public void the_user_signs_up_with_email_name_address_phone_and_password(String email, String name, String address, String phone, String password) {
        Auth.signUp(email, name, address, phone, password);
        user = Auth.getCurrentUser();
    }

    @Then("^the user account should be created successfully$")
    public void the_user_account_should_be_created_successfully() {
        assertNotNull(user);
        assertEquals(user.getEmail(), "customer@gmail.com");
        assertEquals(user.getName(), "Ahmad Moneer");
        assertEquals(user.getAddress(), "Nablus, Rafidya");
        assertEquals(user.getPhone(), "059-872-8291");
        assertFalse(Auth.getIsWorker());
    }

    @Given("^the user has an account with email \"([^\"]*)\" and password \"([^\"]*)\"$")
    public void the_user_has_an_account_with_email_and_password(String email, String password) {
        Auth.signUp(email, "Ahmad Moneer", "Nablus, Rafidya", "059-872-8291", password);
    }

    @When("^the user logs in with email \"([^\"]*)\" and password \"([^\"]*)\"$")
    public void the_user_logs_in_with_email_and_password(String email, String password) {
        Auth.logIn(email, password);
        user = Auth.getCurrentUser();
    }

    @Then("^the user should be logged in successfully$")
    public void the_user_should_be_logged_in_successfully() {
        assertNotNull(user);
    }

    @And("^the user should not have a role$")
    public void the_user_should_not_have_a_role() {
        assertNull(Auth.getRole());
    }

    @And("^the user should be able to log out$")
    public void the_user_should_be_able_to_log_out() {
        Auth.logout();
        assertNull(Auth.getCurrentUser());
    }

    @Given("^the user is logged in with email \"([^\"]*)\" and password \"([^\"]*)\"$")
    public void the_user_is_logged_in_with_email_and_password(String email, String password) {
        Auth.logIn(email, password);
        user = Auth.getCurrentUser();
        assertNotNull(user);
    }

    @When("^the user checks if they are logged in$")
    public void the_user_checks_if_they_are_logged_in() {
        assertTrue(Auth.isLoggedIn());
    }

    @Then("^the user should be logged out successfully$")
    public void the_user_should_be_logged_out_successfully() {
        Auth.logout();
        assertFalse(Auth.isLoggedIn());
    }
}
