package Test;

import com.carpetcleaningmart.utils.DBApi;
import com.carpetcleaningmart.model.Customer;
import com.carpetcleaningmart.model.Order;
import com.carpetcleaningmart.model.Product;


import org.junit.*;
import org.junit.runners.MethodSorters;


import static org.junit.Assert.*;


@FixMethodOrder(MethodSorters.JVM)

public class DistributeOrderTest {

    private Customer customer;
    private Order order;

    @Before
    public void setUp() {
        customer = new Customer();
        customer.setName("Ahmad Ali");
        customer.setPhone("059-555-123");
        customer.setAddress("Nablus, Rafidya");
        customer.setEmail("customer@gmail.com");

        customer.setId(DBApi.addCustomer(customer, "123123"));

        order = new Order();
        order.setCustomerId(customer.getId());
        order.setName("Carpet Cleaning");
        order.setPrice(100.0);
        order.setCategory(Product.Category.CARPET);
        order.setDescription("A Test Order");

        order.setId(DBApi.addOrder(order, false));

    }

    @After
    public void tearDown() {
        DBApi.deleteOrder(order.getId());
        DBApi.deleteCustomer(customer.getId());
    }

    @Test
    public void testNewOrderWithFreeWorker() {



        Order testOrder = DBApi.getOrder(order.getId());

        int numberOfFreeWorkers = DBApi.getFreeWorkers().size();

        if (numberOfFreeWorkers != 0){
            DBApi.distributeWaitingOrders();


                // Then the order status should be "IN_TREATMENT"
            assertEquals(numberOfFreeWorkers - 1, DBApi.getFreeWorkers().size());

        }


    }

    @Test
    public void testNewOrderWithNoFreeWorker() {
        Order testOrder = DBApi.getOrder(order.getId());

        int numberOfFreeWorkers = DBApi.getFreeWorkers().size();

        if (numberOfFreeWorkers == 0) {
            DBApi.distributeWaitingOrders();
            // Then the order status should be "IN_TREATMENT"
            assertEquals(Order.Status.WAITING, testOrder.getStatus());
        }
    }
}
