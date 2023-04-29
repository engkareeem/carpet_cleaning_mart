package Steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class StatisticsFunctionalitySteps {
    @Given("no workers are in the database")
    public void noWorkersAreInTheDatabase() {
    }

    @When("a worker is added to the database")
    public void aWorkerIsAddedToTheDatabase() {
    }

    @Then("the number of workers in the database should be {int} more than before")
    public void theNumberOfWorkersInTheDatabaseShouldBeMoreThanBefore(int arg0) {
    }

    @Given("no orders are in the database")
    public void noOrdersAreInTheDatabase() {
    }

    @When("an order is added to the database with status {string}")
    public void anOrderIsAddedToTheDatabaseWithStatus(String arg0) {
    }

    @And("a worker is assigned to the order")
    public void aWorkerIsAssignedToTheOrder() {
    }

    @And("the order status is changed to {string}")
    public void theOrderStatusIsChangedTo(String arg0) {
    }

    @Then("the number of orders in the {string} status should be {int}")
    public void theNumberOfOrdersInTheStatusShouldBe(String arg0, int arg1) {
    }
}
