package com.carpetcleaningmart.InOut;

import com.carpetcleaningmart.Utils.DBApi;
import com.carpetcleaningmart.Utils.Interrupt;
import com.carpetcleaningmart.model.Order;
import com.carpetcleaningmart.model.Worker;

import java.util.ArrayList;

public class WorkerInOut {
    public static void displayStatistics() {
        int workersNum = DBApi.getWorkersCount();
        int customersNum = DBApi.getCustomersServedCount();
        int waitedOrders = DBApi.getOrderStatusCount(Order.Status.WAITING);
        int inTreatmentOrders = DBApi.getOrderStatusCount(Order.Status.IN_TREATMENT);
        int completedOrders = DBApi.getOrderStatusCount(Order.Status.COMPLETE);

        UtilsInOut.printHeader("Mart Statistics");
        UtilsInOut.printContentRow("Workers serving customers: " + workersNum);
        UtilsInOut.printContentRow("Customers served: " + customersNum);
        UtilsInOut.printContentRow("Waiting orders: " + waitedOrders);
        UtilsInOut.printContentRow("In Treatment orders: " + inTreatmentOrders);
        UtilsInOut.printContentRow("Completed orders: " + completedOrders);
        UtilsInOut.printSeparator();
        Interrupt.readChoice(0, "Enter any number to return.. ");
    }

    public static void hireWorker() {
        Worker worker = AuthInOut.hireWorker();
        Interrupt.printSuccess(worker.getName() + " Has been hired successfully.");
        System.out.println();
        Interrupt.readChoice(0, "Enter any number to return: ");
    }

    public static void fireWorker() {
        String name = Interrupt.readString("Enter the worker name: ");
        ArrayList<Worker> workers = DBApi.searchForWorkers(name);
        if (workers.isEmpty()) {
            Interrupt.printError("There is no workers with that name..");
            Interrupt.readChoice(0, "Type any number to return.. ");
        } else {
            UtilsInOut.printSeparator();
            int i = 1;
            for (Worker worker : workers) {
                UtilsInOut.printContentRow(String.format("%d. %s - %s", i++, worker.getName(), worker.getEmail()));
            }
            UtilsInOut.printContentRow(String.format("%d. Return", i));
            UtilsInOut.printSeparator();
            int choice = Interrupt.readChoice(i);
            if (choice != i) {
                Worker worker = workers.get(choice - 1);
                DBApi.deleteWorker(worker.getId());
                Interrupt.printSuccess(worker.getName() + " has been fired successfully. ");
                Interrupt.readChoice(0, "Enter any number to return: ");
            }
        }
    }
}
