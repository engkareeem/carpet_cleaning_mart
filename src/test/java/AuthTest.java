

import com.carpetcleaningmart.Utils.Auth;

import com.carpetcleaningmart.Utils.DBApi;
import com.carpetcleaningmart.model.User;


import org.junit.*;
import org.junit.runners.MethodSorters;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;


@FixMethodOrder(MethodSorters.JVM)
public class AuthTest {

    private User user;


    @Before
    public void setUp() {
        user = null;
    }

    @After
    public void tearDown()  {
        DBApi.deleteTestCustomer();
    }

    @Test
    public void testSignUpCustomer() throws InterruptedException {
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
        Auth.signUp("customer@gmail.com", "Ahmad Moneer", "Nablus, Rafidya", "059-872-8291", "123123");
        Auth.logIn("customer@gmail.com", "123123");
        user = Auth.getCurrentUser();
        assertNotNull(Auth.getCurrentUser());
        assertFalse(Auth.getIsWorker());
        assertNull(Auth.getRole());
    }

    @Test
    public void testLogOut() {
        Auth.signUp("customer@gmail.com", "Ahmad Moneer", "Nablus, Rafidya", "059-872-8291", "123123");

        Auth.logIn("customer@gmail.com", "123123");
        user = Auth.getCurrentUser();
        assertNotNull(user);
        Auth.logout();
        assertNull(Auth.getCurrentUser());
        assertNull(Auth.getIsWorker());
    }

    @Test
    public void testIsLoggedIn() {
        Auth.signUp("customer@gmail.com", "Ahmad Moneer", "Nablus, Rafidya", "059-872-8291", "123123");

        Auth.logIn("customer@gmail.com", "123123");

        Auth.logout();
        assertFalse(Auth.isLoggedIn());
        Auth.logIn("customer@gmail.com", "123123");
        assertTrue(Auth.isLoggedIn());
        Auth.logout();
        assertFalse(Auth.isLoggedIn());
    }

}
