package Steps;

import com.carpetcleaningmart.utils.DBApi;
import com.carpetcleaningmart.model.Worker;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;


@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/java/feature/workerFunctionality.feature")
public class WorkerFunctionalitySteps {

    private Worker worker;

    @Given("a worker with name {string}, phone {string}, address {string}, email {string}, and type {string}")
    public void aWorkerWithNamePhoneAddressEmailAndType(String arg0, String arg1, String arg2, String arg3, String arg4) {
        worker = new Worker();
        worker.setName("Ahmad Rami");
        worker.setPhone("059-555-5678");
        worker.setAddress("Nablus, Ragidya");
        worker.setEmail("worker@gmail.com");
        worker.setType(Worker.WorkerType.EMPLOYEE);
    }

    @When("the worker is added to the database with a password of {string}")
    public void theWorkerIsAddedToTheDatabaseWithAPasswordOf(String arg0) {
        DBApi.addWorker(worker, arg0);
    }

    @Then("the worker should be successfully added to the database")
    public void theWorkerShouldBeSuccessfullyAddedToTheDatabase() {
        Worker addedWorker = DBApi.getTestWorker();

        assertNotNull(addedWorker);


    }

    @And("the worker's information should match the added information")
    public void theWorkerSInformationShouldMatchTheAddedInformation() {
        Worker addedWorker = DBApi.getTestWorker();

        assertNotNull(addedWorker);

        assertEquals(worker.getName(), addedWorker.getName());
        assertEquals(worker.getPhone(), addedWorker.getPhone());
        assertEquals(worker.getAddress(), addedWorker.getAddress());
        assertEquals(worker.getEmail(), addedWorker.getEmail());
        assertEquals(worker.getType().toString(), addedWorker.getType().toString());
    }

    @Given("a worker with name {string}, phone {string}, address {string}, email {string}, and type {string} exists in the database")
    public void aWorkerWithNamePhoneAddressEmailAndTypeExistsInTheDatabase(String arg0, String arg1, String arg2, String arg3, String arg4) {
        Worker addedWorker = DBApi.getTestWorker();

        assertNotNull(addedWorker);

        assertEquals(worker.getName(), addedWorker.getName());
        assertEquals(worker.getPhone(), addedWorker.getPhone());
        assertEquals(worker.getAddress(), addedWorker.getAddress());
        assertEquals(worker.getEmail(), addedWorker.getEmail());
        assertEquals(worker.getType().toString(), addedWorker.getType().toString());
    }

    @When("the worker's name is updated to {string}, phone is updated to {string}, address is updated to {string}, and type is updated to {string}")
    public void theWorkerSNameIsUpdatedToPhoneIsUpdatedToAddressIsUpdatedToAndTypeIsUpdatedTo(String arg0, String arg1, String arg2, String arg3) {
        Worker addedWorker = DBApi.getTestWorker();

        assertNotNull(addedWorker);
        worker.setName(arg0);
        worker.setPhone(arg1);
        worker.setAddress(arg2);
        worker.setType(Worker.WorkerType.EMPLOYEE);

        DBApi.updateWorker(addedWorker.getId(), worker);
    }

    @Then("the worker's information should be successfully updated in the database")
    public void theWorkerSInformationShouldBeSuccessfullyUpdatedInTheDatabase() {
        Worker addedWorker = DBApi.getTestWorker();

        assertNotNull(addedWorker);
    }



    @When("the worker is deleted from the database")
    public void theWorkerIsDeletedFromTheDatabase() {

        Worker addedWorker = DBApi.getTestWorker();

        assertNotNull(addedWorker);

        DBApi.deleteWorker(addedWorker.getId());




    }

    @Then("the worker should be successfully deleted from the database")
    public void theWorkerShouldBeSuccessfullyDeletedFromTheDatabase() {

        Worker deletedWorker = DBApi.getTestWorker();

        assertNull(deletedWorker);

    }



}
