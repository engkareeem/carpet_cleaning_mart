package com.carpetcleaningmart.InOut;

import com.carpetcleaningmart.Utils.Auth;
import com.carpetcleaningmart.Utils.Colors;
import com.carpetcleaningmart.Utils.DBApi;
import com.carpetcleaningmart.Utils.Interrupt;
import com.carpetcleaningmart.model.Customer;
import com.carpetcleaningmart.model.Worker;

public class AuthInOut {
    public static void authorizeUser() {
        UtilsInOut.clear();
        System.out.println(Colors.ANSI_DEFAULT);
        UtilsInOut.printHeader("Authentication");
        UtilsInOut.printContentRow("1. Login");
        UtilsInOut.printContentRow("2. Register new account");
        UtilsInOut.printContentRow("3. Exit");
        UtilsInOut.printSeparator();

        int choice = Interrupt.readChoice(3);
        if (choice == 1) {
            while (!Auth.isLoggedIn()) {
                String email = Interrupt.readEmail();
                String password = Interrupt.readPassword();
                Auth.logIn(email,password);
            }
        } else if(choice == 2) {
            String name = Interrupt.readString("Enter your name: ");
            String email = Interrupt.readEmail();
            while(DBApi.checkIfEmailUsed(email)) {
                Interrupt.printError("Email is already in use.");
                email = Interrupt.readEmail();
            }
            String phone = Interrupt.readPhoneNumber();
            String address = Interrupt.readString("Enter your address: ");
            String password = Interrupt.readPassword();
            Auth.signUp(email,name,address,phone,password);
        } else {
            System.exit(0);
        }
    }
    public static Worker hireWorker() {
        String name = Interrupt.readString("Enter worker name: ");
        String email = Interrupt.readEmail();
        while(DBApi.checkIfEmailUsed(email)) {
            Interrupt.printError("Email is already in use.");
            email = Interrupt.readEmail();
        }
        String phone = Interrupt.readPhoneNumber();
        String address = Interrupt.readString("Enter worker address: ");
        String password = Interrupt.readPassword();
        Worker worker = new Worker(null,name,phone,address,email, Worker.WorkerType.EMPLOYEE,null);
        DBApi.addWorker(worker,password);
        return worker;
    }

}
