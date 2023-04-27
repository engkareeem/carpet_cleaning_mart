//package com.carpetcleaningmart.Utils;

import static org.junit.jupiter.api.Assertions.*;

import com.carpetcleaningmart.Utils.Auth;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.carpetcleaningmart.model.Customer;
import com.carpetcleaningmart.model.User;
import com.carpetcleaningmart.model.Worker;

class AuthTest {

    private User user;
    private Customer fakeCustomer;

    private Worker fakeWorker;


    @BeforeEach
    void setUp() throws Exception {
        user = null;
    }

    @Test
    void testSignUpCustomer() {
        Auth.signUp("customer@test.com", "Ahmad Moneer", "123 Main St", "059-872-8291", "123123");
        user = Auth.getCurrentUser();
        assertNotNull(user);
        assertEquals(user.getEmail(), "test@test.com");
        assertEquals(user.getName(), "John Doe");
        assertEquals(user.getAddress(), "123 Main St");
        assertEquals(user.getPhone(), "555-555-5555");
        assertFalse(Auth.getIsWorker());
    }

    @Test
    void testLogInAsWorker() {
        Auth.logIn("test_worker@test.com", "password");
        user = Auth.getCurrentUser();
        assertNotNull(user);
        assertTrue(Auth.getIsWorker());
        assertEquals(((Worker) user).getType(), Worker.WorkerType.EMPLOYEE);
    }

    @Test
    void testLogInAsCustomer() {
        Auth.logIn("test_customer@test.com", "password");
        user = Auth.getCurrentUser();
        assertNotNull(user);
        assertFalse(Auth.getIsWorker());
        assertNull(Auth.getRole());
    }

    @Test
    void testLogOut() {
        Auth.logIn("test_customer@test.com", "password");
        user = Auth.getCurrentUser();
        assertNotNull(user);
        Auth.logout();
        assertNull(Auth.getCurrentUser());
        assertNull(Auth.getIsWorker());
    }

    @Test
    void testIsLoggedIn() {
        assertTrue(Auth.isLoggedIn());
        Auth.logIn("test_customer@test.com", "password");
        assertFalse(Auth.isLoggedIn());
        Auth.logout();
        assertTrue(Auth.isLoggedIn());
    }

}
