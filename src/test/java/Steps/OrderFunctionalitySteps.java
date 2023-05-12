package Steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;


@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/java/feature/orderFunctionality.feature")
public class OrderFunctionalitySteps {
    @Given("an order is created with category {string}, name {string}, description {string}, price {double} and no image")
    public void anOrderIsCreatedWithCategoryNameDescriptionPriceAndNoImage(String arg0, String arg1, String arg2, int arg3, int arg4) {
    }

    @When("the order is added to the database")
    public void theOrderIsAddedToTheDatabase() {
    }

    @Then("the order should exist in the database with the same properties")
    public void theOrderShouldExistInTheDatabaseWithTheSameProperties() {
    }

    @When("the order description is updated to {string}")
    public void theOrderDescriptionIsUpdatedTo(String arg0) {
    }

    @Then("the order description in the database should match {string}")
    public void theOrderDescriptionInTheDatabaseShouldMatch(String arg0) {
    }

    @When("the order is deleted from the database")
    public void theOrderIsDeletedFromTheDatabase() {
    }

    @Then("the order should not exist in the database")
    public void theOrderShouldNotExistInTheDatabase() {
    }

}
