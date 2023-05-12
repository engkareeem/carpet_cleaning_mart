package Steps;

import com.carpetcleaningmart.utils.DBApi;
import com.carpetcleaningmart.model.Customer;
import com.carpetcleaningmart.model.Order;
import com.carpetcleaningmart.model.Product;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;


@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/java/feature/distributeOrderOnWorkers.feature")
public class DistributeOrderOnWorkersSteps {
    private  Customer customer;
    private Order order;

    @Given("a customer wants to make a new order")
    public void aCustomerWantsToMakeANewOrder() {
        customer = new Customer();
        customer.setName("Ahmad Ali");
        customer.setPhone("059-555-123");
        customer.setAddress("Nablus, Rafidya");
        customer.setEmail("customer@gmail.com");

        customer.setId(DBApi.addCustomer(customer, "123123"));


    }

    @When("they place the order")
    public void theyPlaceTheOrder() {
        order = new Order();
        order.setCustomerId(customer.getId());
        order.setName("Carpet Cleaning");
        order.setPrice(100.0);
        order.setCategory(Product.Category.CARPET);
        order.setDescription("A Test Order");
        order.setId(DBApi.addOrder(order, true));

    }

    @Then("the order status should be {string}")
    public void theOrderStatusShouldBe(String arg0) {
        Order testOrder = DBApi.getOrder(order.getId());

        int numberOfFreeWorkers = DBApi.getFreeWorkers().size();

        if (numberOfFreeWorkers > 0) {
            // Then the order status should be "IN_TREATMENT"
            assertEquals(Order.Status.IN_TREATMENT, testOrder.getStatus());
        }
    }

    @And("there are no free workers available")
    public void thereAreNoFreeWorkersAvailable() {
        Order testOrder = DBApi.getOrder(order.getId());

        int numberOfFreeWorkers = DBApi.getFreeWorkers().size();

        if (numberOfFreeWorkers == 0) {
            // Then the order status should be "IN_TREATMENT"
            assertEquals(Order.Status.WAITING, testOrder.getStatus());
        }
    }


}
