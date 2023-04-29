package Test;

import com.carpetcleaningmart.Utils.DBApi;
import com.carpetcleaningmart.model.Order;
import com.carpetcleaningmart.model.Product;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import static org.junit.Assert.*;

@FixMethodOrder(MethodSorters.JVM)
public class OrdersFunctionalityTest {
    private Order order;
    private String orderId;
    @Before
    public void setup() {
        order = new Order(Product.Category.CARPET,"Kareem`s Carpet","9*9 Pink Carpet",null,405.0,null);
        orderId = DBApi.addOrder(order);
    }
    @After
    public void end() {
        DBApi.deleteOrder(orderId);
    }

    @Test
    public void testAddOrder() {
        Order testOrder = DBApi.getOrder(orderId);
        assertNotNull(testOrder);
        assertEquals(orderId,testOrder.getId());
        assertEquals(order.getName(),testOrder.getName());
        assertEquals(order.getPrice(),testOrder.getPrice());
        assertEquals(order.getCategory(),testOrder.getCategory());
        assertEquals(order.getDescription(),testOrder.getDescription());
    }

    @Test
    public void testUpdateOrder() {
        order.setDescription("Testing description");
        DBApi.updateOrder(orderId,order);
        Order testOrder = DBApi.getOrder(orderId);
        assertEquals(order.getDescription(),testOrder.getDescription());
    }
    @Test
    public void testDeleteOrder() {
        DBApi.deleteOrder(orderId);

        Order testOrder = DBApi.getOrder(orderId);
        assertNull(testOrder.getId());
    }

}
