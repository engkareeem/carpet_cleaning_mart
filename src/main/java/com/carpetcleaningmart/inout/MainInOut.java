package com.carpetcleaningmart.inout;

import com.carpetcleaningmart.utils.Auth;
import com.carpetcleaningmart.utils.Colors;
import com.carpetcleaningmart.utils.Interrupt;
import com.carpetcleaningmart.model.Worker;
import com.carpetcleaningmart.utils.Printer;

public class MainInOut {

    private MainInOut() {
        // Do nothing
    }

    public static void mainMenu() {
        String title = "Main Menu";

        UtilsInOut.clear();
        Printer.print(Colors.ANSI_DEFAULT);

        if (!Auth.getIsWorker()) {
            handleCustomerMenu(title);
        } else if (Auth.getRole() == Worker.WorkerType.EMPLOYEE) {
            handleEmployeeMenu(title);
        } else if (Auth.getRole() == Worker.WorkerType.ADMIN) {
            handleAdminMenu(title);
        }

        Printer.print(Colors.ANSI_RESET);
    }

    private static void handleCustomerMenu(String title) {
        UtilsInOut.printHeader(title);
        UtilsInOut.printContentRow("1. Make new order");
        UtilsInOut.printContentRow("2. Track your orders");
        UtilsInOut.printContentRow("3. Logout");
        UtilsInOut.printSeparator();

        int choice = Interrupt.readChoice(3);
        if (choice == 1) {
            UtilsInOut.clear();
            OrdersInOut.createNewOrder();
        } else if (choice == 2) {
            OrdersInOut.displayOrders();
        } else {
            Auth.logout();
        }
    }

    private static void handleEmployeeMenu(String title) {
        UtilsInOut.printHeader(title);
        UtilsInOut.printContentRow("1. View current order");
        UtilsInOut.printContentRow("2. View your previous orders");
        UtilsInOut.printContentRow("3. Logout");
        UtilsInOut.printSeparator();

        int choice = Interrupt.readChoice(3);
        if (choice == 1) {
            OrdersInOut.displayCurrentOrder();
        } else if (choice == 2) {
            OrdersInOut.displayPreviousOrders();
        } else {
            Auth.logout();
        }
    }

    private static void handleAdminMenu(String title) {
        UtilsInOut.printHeader(title);
        UtilsInOut.printContentRow("1. Hire Worker");
        UtilsInOut.printContentRow("2. Fire Worker");
        UtilsInOut.printContentRow("3. Statistics");
        UtilsInOut.printContentRow("4. Print Report");
        UtilsInOut.printContentRow("5. Logout");
        UtilsInOut.printSeparator();

        int choice = Interrupt.readChoice(5);
        if (choice == 1) {
            WorkerInOut.hireWorker();
        } else if (choice == 2) {
            WorkerInOut.fireWorker();
        } else if (choice == 3) {
            WorkerInOut.displayStatistics();
        } else {
            Auth.logout();
        }
    }
}
