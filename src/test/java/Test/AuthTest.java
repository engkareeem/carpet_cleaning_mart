package Test;

import com.carpetcleaningmart.utils.Auth;

import com.carpetcleaningmart.utils.DBApi;
import com.carpetcleaningmart.model.User;


import org.junit.*;
import org.junit.runners.MethodSorters;


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
        assertEquals( "customer@gmail.com", user.getEmail());
        assertEquals( "Ahmad Moneer", user.getName());
        assertEquals( "Nablus, Rafidya", user.getAddress());
        assertEquals( "059-872-8291", user.getPhone());
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
