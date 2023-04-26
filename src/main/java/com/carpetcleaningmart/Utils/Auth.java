package com.carpetcleaningmart.Utils;

import com.carpetcleaningmart.model.Customer;
import com.carpetcleaningmart.model.User;
import com.carpetcleaningmart.model.Worker;

public class Auth {
    private static User currentUser;
    private static Boolean isWorker;

    private static Worker.WorkerType role;

    public static void signUp(String email, String name, String address, String phone, String password){
        Customer customer = new Customer();
        customer.setName(name);
        customer.setEmail(email);
        customer.setAddress(address);
        customer.setPhone(phone);
        isWorker = false;

        currentUser = DBApi.signUp(customer, password);

        if(currentUser.getId() == null){
            currentUser = null;
            isWorker = null;
        }

    }

    public static void logIn(String email, String password){
        if(DBApi.isWorker(email)){ // Sign In As a worker
            isWorker = true;
            currentUser = DBApi.logInAsWorker(email, password);
            if(currentUser.getId() != null){
                role = ((Worker) currentUser).getType();
            }else{
                currentUser = null;
            }
        }else{ // Sign In As a Customer
            isWorker = false;
            currentUser = DBApi.loginAsCustomer(email, password);
            if(currentUser.getId() != null){
                role = null;
            }else{
                currentUser = null;
            }

        }
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static Boolean getIsWorker() {
        return isWorker;
    }

    public static Worker.WorkerType getRole() {
        return role;
    }

    public static boolean isLoggedIn(){
        return currentUser == null;
    }


    public static void logout(){
        currentUser = null;
        isWorker = null;
    }



}