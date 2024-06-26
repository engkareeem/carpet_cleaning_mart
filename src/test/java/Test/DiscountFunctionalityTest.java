package Test;

import com.carpetcleaningmart.functions.OrdersPage;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import static org.junit.Assert.assertEquals;

@FixMethodOrder(MethodSorters.JVM)
public class DiscountFunctionalityTest {
    @Test
    public void discountTest() {
        double price, discount;
        int timeServed;
        price = 2000;
        timeServed = 0;
        discount = OrdersPage.getDiscount(price, timeServed);
        assertEquals(0.0, discount,0);

        price = 500;
        timeServed = 4;
        discount = OrdersPage.getDiscount(price,timeServed);
        assertEquals(0.02, discount,0);

        price = 300;
        timeServed=11;
        discount = OrdersPage.getDiscount(price,timeServed);
        assertEquals(0.05, discount,0);
    }
}
