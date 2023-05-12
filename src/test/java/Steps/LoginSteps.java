package Steps;

import com.carpetcleaningmart.utils.Auth;
import com.carpetcleaningmart.utils.DBApi;
import com.carpetcleaningmart.model.User;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.AfterClass;

import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;


@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/java/feature/login.feature")
public class LoginSteps {

    private User user;

    @Before
    public void setUp() {
        user = null;
    }

    @AfterClass
    public static void tearDown() {
        DBApi.deleteTestCustomer();
    }


    @Given("the user is not signed up")
    public void the_user_is_not_signed_up() {
        assertNull(user);
    }
    @When("the user sign up with email {string}, name {string}, address {string}, phone {string}, and password {string}")
    public void the_user_sign_up_with_email_name_address_phone_and_password(String string, String string2, String string3, String string4, String string5) {
        Auth.signUp(string, string2, string3, string4, string5);

    }
    @Then("the user should be signed up successfully")
    public void the_user_should_be_signed_up_successfully() {
        user = Auth.getCurrentUser();
        assertNotNull(user);
    }
    @Then("the user should have email {string}, name {string}, address {string}, and phone {string}")
    public void the_user_should_have_email_name_address_and_phone(String string, String string2, String string3, String string4) {
        assertEquals(string, user.getEmail());
        assertEquals(string2, user.getName());
        assertEquals(string3, user.getAddress());
        assertEquals(string4, user.getPhone());
    }
    @Then("the user should not be a worker")
    public void the_user_should_not_be_a_worker() {
        assertFalse(Auth.getIsWorker());
    }

    @Given("the user is signed up with email {string} and password {string}")
    public void theUserIsSignedUpWithEmailAndPassword(String arg0, String arg1) {
    }

    @When("the user log in with email {string} and password {string}")
    public void theUserLogInWithEmailAndPassword(String arg0, String arg1) {
        Auth.logIn(arg0, arg1);
    }

    @Then("the user should be logged in successfully")
    public void theUserShouldBeLoggedInSuccessfully() {
        assertNotNull(Auth.getCurrentUser());

    }

    @And("the user should not have a role")
    public void theUserShouldNotHaveARole() {
        assertNull(Auth.getIsWorker());
    }

    @And("the user should be able to log out")
    public void theUserShouldBeAbleToLogOut() {
        Auth.logout();
    }


    @And("the user should be able to check if they are logged in")
    public void theUserShouldBeAbleToCheckIfTheyAreLoggedIn() {
        assertTrue(Auth.isLoggedIn());
    }

    @And("the user should not be logged in after they log out")
    public void theUserShouldNotBeLoggedInAfterTheyLogOut() {
        assertFalse(Auth.isLoggedIn());
    }


}
