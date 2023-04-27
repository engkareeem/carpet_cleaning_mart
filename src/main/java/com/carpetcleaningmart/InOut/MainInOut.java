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
            System.out.println("+----------------------------------------------------------------------------+");
            System.out.println("|                                 Main Menu                                  |");
            System.out.println("+----------------------------------------------------------------------------+");
            System.out.println("|     1. Make new order                                                      |");
            System.out.println("|     2. Track your orders                                                   |");
            System.out.println("|     3. Logout                                                              |");
            System.out.println("+----------------------------------------------------------------------------+");
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
            System.out.println("+----------------------------------------------------------------------------+");
            System.out.println("|                                 Main Menu                                  |");
            System.out.println("+----------------------------------------------------------------------------+");
            System.out.println("|     1. View current order                                                  |");
            System.out.println("|     2. Logout                                                              |");
            System.out.println("+----------------------------------------------------------------------------+");
            choice = Interrupt.readChoice(2);
            if(choice == 1) {
                OrdersInOut.displayCurrentOrder();
            } else {
                Auth.logout();
            }
        } else if (Auth.getRole() == Worker.WorkerType.ADMIN) {
            System.out.println("+----------------------------------------------------------------------------+");
            System.out.println("|                                 Main Menu                                  |");
            System.out.println("+----------------------------------------------------------------------------+");
            System.out.println("|     1. Hire Worker                                                         |");
            System.out.println("|     2. Statistics                                                          |");
            System.out.println("|     3. Print Report                                                        |");
            System.out.println("|     4. Logout                                                              |");
            System.out.println("+----------------------------------------------------------------------------+");
            choice = Interrupt.readChoice(4);
        }
        System.out.print(Colors.ANSI_RESET);
    }
}