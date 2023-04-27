

import com.carpetcleaningmart.Utils.Auth;

import com.carpetcleaningmart.Utils.DBApi;
import com.carpetcleaningmart.model.Customer;
import com.carpetcleaningmart.model.User;
import com.carpetcleaningmart.model.Worker;


import org.junit.*;

import static org.junit.Assert.*;

public class AuthTest {

    private User user;


    @Before
    public void setUp() throws Exception {
        user = null;
    }

    @AfterClass
    public static void tearDown() throws Exception {
        DBApi.deleteTestCustomer();
    }

    @Test
    public void testSignUpCustomer() {
        Auth.signUp("customer@gmail.com", "Ahmad Moneer", "Nablus, Rafidya", "059-872-8291", "123123");
        user = Auth.getCurrentUser();

        assertNotNull(user);
        assertEquals(user.getEmail(), "customer@gmail.com");
        assertEquals(user.getName(), "Ahmad Moneer");
        assertEquals(user.getAddress(), "Nablus, Rafidya");
        assertEquals(user.getPhone(), "059-872-8291");
        assertFalse(Auth.getIsWorker());

    }


    @Test
    public void testLogIn() {
        Auth.logIn("customer@gmail.com", "123123");
        user = Auth.getCurrentUser();
        assertNotNull(user);
        assertFalse(Auth.getIsWorker());
        assertNull(Auth.getRole());
    }

    @Test
    public void testLogOut() {
        Auth.logIn("customer@gmail.com", "123123");
        user = Auth.getCurrentUser();
        assertNotNull(user);
        Auth.logout();
        assertNull(Auth.getCurrentUser());
        assertNull(Auth.getIsWorker());
    }

    @Test
    public void testIsLoggedIn() {
        Auth.logout();
        assertFalse(Auth.isLoggedIn());
        Auth.logIn("customer@gmail.com", "123123");
        assertTrue(Auth.isLoggedIn());
        Auth.logout();
        assertFalse(Auth.isLoggedIn());
    }

}
