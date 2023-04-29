package Test;

import com.carpetcleaningmart.Utils.DBApi;
import com.carpetcleaningmart.model.Order;
import com.carpetcleaningmart.model.Product;
import com.carpetcleaningmart.model.Worker;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import static org.junit.Assert.*;

@FixMethodOrder(MethodSorters.JVM)

public class StatisticsFunctionalityTest {
    Order order;
    Worker worker;

    @Before
    public void setup() {
        order = new Order(Product.Category.CARPET,"Kareem`s Carpet","9*9 Pink Carpet",null,405.0,"1");
        worker = new Worker(null,"Ahmad Rami","059-555-5678","Nablus, Ragidya","worker@gmail.com", Worker.WorkerType.EMPLOYEE,null);
    }

    @Test
    public void workersCountTest() {
        int old = DBApi.getWorkersCount();
        String workerId = DBApi.addWorker(worker,"123123");
        int new_ = DBApi.getWorkersCount();
        assertEquals(new_,old+1);
        DBApi.deleteWorker(workerId);
    }
    @Test
    public void ordersCountTest() {
        int waitingOld = DBApi.getOrderStatusCount(Order.Status.WAITING);
        int inTreatmentOld = DBApi.getOrderStatusCount(Order.Status.IN_TREATMENT);
        int completeOld = DBApi.getOrderStatusCount(Order.Status.COMPLETE);

        String orderId = DBApi.addOrder(order);
        String workerId = DBApi.addWorker(worker,"123123");

        int waitingNew = DBApi.getOrderStatusCount(Order.Status.WAITING);

        int inTreatmentNew = DBApi.getOrderStatusCount(Order.Status.IN_TREATMENT);

        DBApi.finishWorkOnAnOrder(orderId);
        int completeNew = DBApi.getOrderStatusCount(Order.Status.COMPLETE);

        assertTrue(waitingOld == waitingNew - 1 || inTreatmentOld == inTreatmentNew-1);
        assertEquals(completeNew,completeOld + 1);
        DBApi.deleteOrder(orderId);
        DBApi.deleteWorker(workerId);
    }

}
