package com.carpetcleaningmart.InOut;

import com.carpetcleaningmart.Utils.Auth;
import com.carpetcleaningmart.Utils.Colors;
import com.carpetcleaningmart.Utils.Interrupt;
import com.carpetcleaningmart.model.Worker;

public class MainInOut {
    public static void mainMenu() {

        UtilsInOut.clear();
        int choice;
        System.out.print(Colors.ANSI_DEFAULT);
        if (!Auth.getIsWorker()) {
            UtilsInOut.printHeader("Main Menu");
            UtilsInOut.printContentRow("1. Make new order");
            UtilsInOut.printContentRow("2. Track your orders");
            UtilsInOut.printContentRow("3. Logout");
            UtilsInOut.printSeparator();
            choice = Interrupt.readChoice(3);
            if (choice == 1) {
                UtilsInOut.clear();
                OrdersInOut.createNewOrder();
            } else if (choice == 2) {
                OrdersInOut.displayOrders();
            } else {
                Auth.logout();
            }
        } else if (Auth.getRole() == Worker.WorkerType.EMPLOYEE) {
            UtilsInOut.printHeader("Main Menu");
            UtilsInOut.printContentRow("1. View current order");
            UtilsInOut.printContentRow("2. Logout");
            UtilsInOut.printSeparator();
            choice = Interrupt.readChoice(2);
            if (choice == 1) {
                OrdersInOut.displayCurrentOrder();
            } else {
                Auth.logout();
            }
        } else if (Auth.getRole() == Worker.WorkerType.ADMIN) {
            UtilsInOut.printHeader("Main Menu");
            UtilsInOut.printContentRow("1. Hire Worker");
            UtilsInOut.printContentRow("2. Statistics");
            UtilsInOut.printContentRow("3. Print Report");
            UtilsInOut.printContentRow("4. Logout");
            UtilsInOut.printSeparator();
            choice = Interrupt.readChoice(4);
        }
        System.out.print(Colors.ANSI_RESET);
    }
}