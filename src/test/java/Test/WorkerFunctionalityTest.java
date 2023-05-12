package Test;

import com.carpetcleaningmart.utils.DBApi;


import com.carpetcleaningmart.model.Worker;
import org.junit.*;
import org.junit.runners.MethodSorters;


import static org.junit.Assert.*;

@FixMethodOrder(MethodSorters.JVM)
public class WorkerFunctionalityTest {

    private Worker worker;

    @Before
    public void setUp() {
        worker = new Worker();
        worker.setName("Ahmad Rami");
        worker.setPhone("059-555-5678");
        worker.setAddress("Nablus, Ragidya");
        worker.setEmail("worker@gmail.com");
        worker.setType(Worker.WorkerType.EMPLOYEE);
    }

    @After
    public void tearDown() {
        DBApi.deleteTestWorker();
    }

    @Test
    public void testAddWorker() {
        // add a worker
        DBApi.addWorker(worker, "123123");


        // check that the worker was added
        Worker addedWorker = DBApi.getTestWorker();

        assertNotNull(addedWorker);
        assertEquals("Ahmad Rami", addedWorker.getName());
        assertEquals("059-555-5678", addedWorker.getPhone());
        assertEquals("Nablus, Ragidya", addedWorker.getAddress());
        assertEquals("worker@gmail.com", addedWorker.getEmail());
        assertEquals("EMPLOYEE", addedWorker.getType().toString());
    }

    @Test
    public void testUpdateWorker() {
        // add a worker
        DBApi.addWorker(worker, "123123");

        Worker testWorker = DBApi.getTestWorker();

        assertNotNull(testWorker);
        // update the worker
        testWorker.setName("Yaser Mostafa");
        testWorker.setPhone("059-555-123");
        testWorker.setAddress("Nablus, BeitEihba");
        testWorker.setType(Worker.WorkerType.EMPLOYEE);

        DBApi.updateWorker(testWorker.getId(), testWorker);
        // check that the worker was updated
        Worker updatedWorker = DBApi.getWorkerById(testWorker.getId());

        assertNotNull(updatedWorker);
        assertEquals("Yaser Mostafa", updatedWorker.getName());
        assertEquals("059-555-123", updatedWorker.getPhone());
        assertEquals("Nablus, BeitEihba", updatedWorker.getAddress());
        assertEquals("EMPLOYEE", updatedWorker.getType().toString());
    }

    @Test
    public void testDeleteWorker() {
        // add a worker
        DBApi.addWorker(worker, "123123");

        Worker testWorker = DBApi.getTestWorker();

        // delete the worker
        assertNotNull(testWorker);
        DBApi.deleteWorker(testWorker.getId());
        // check that the worker was deleted
        assertNull(DBApi.getWorkerById(testWorker.getId()));
    }
}
