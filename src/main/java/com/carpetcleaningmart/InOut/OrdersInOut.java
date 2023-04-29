package com.carpetcleaningmart.InOut;

import com.carpetcleaningmart.Functions.OrdersPage;
import com.carpetcleaningmart.Functions.Utils;
import com.carpetcleaningmart.Utils.Auth;
import com.carpetcleaningmart.Utils.Colors;
import com.carpetcleaningmart.Utils.DBApi;
import com.carpetcleaningmart.Utils.Interrupt;
import com.carpetcleaningmart.model.Order;
import com.carpetcleaningmart.model.Product;

import java.util.ArrayList;
import java.util.Scanner;

public class OrdersInOut {
    // === Customer Page === \\
    public static void createNewOrder() {
        Product.Category category;
        Scanner scanner = new Scanner(System.in);
        int choice;

        System.out.print(Colors.ANSI_DEFAULT);
        UtilsInOut.printHeader("New Order");
        UtilsInOut.printContentRow("Select product category");
        UtilsInOut.printContentRow(" 1. Carpet");
        UtilsInOut.printContentRow(" 2. Cover");
        UtilsInOut.printContentRow(" 3. Mattress");
        UtilsInOut.printContentRow(" 4. BLIND");
        UtilsInOut.printContentRow(" 5. CURTAIN");
        UtilsInOut.printContentRow(" 6. TILE");
        UtilsInOut.printContentRow(" 7. Return");
        UtilsInOut.printSeparator();
        choice = Interrupt.readChoice(7);

        if (choice == 1) {
            category = Product.Category.CARPET;
        } else if (choice == 2) {
            category = Product.Category.COVER;
        } else if(choice == 3){
            category = Product.Category.MATTRESS;
        }else if(choice == 4){
            category = Product.Category.BLIND;
        }else if(choice == 5){
            category = Product.Category.CURTAIN;
        } else if(choice == 6){
            category = Product.Category.TILE;
        } else {
            return;
        }

        UtilsInOut.printSeparator();
        UtilsInOut.printContentRow("Please enter product dimensions.");
        double height = Interrupt.readNumber("|        Height: ");
        double width = Interrupt.readNumber("|        width: ");

        UtilsInOut.printSeparator();
        UtilsInOut.printContentRow("Please describe the product.");
        UtilsInOut.printSeparator();
        System.out.print("Enter the description: ");
        scanner.reset();
        String description = scanner.nextLine();
        description = String.format("%.1f*%.1f %s, %s", height, width, Utils.textToName(category.toString()), description);
        String name = Utils.getFirstName(Auth.getCurrentUser().getName()) + "`s " + Utils.textToName(category.toString()); // TODO: User name + category

        double price = OrdersPage.calculatePrice(category, height, width);

        Order order = new Order(category, name, description, Order.Status.WAITING, price, Auth.getCurrentUser().getId());
        DBApi.addOrder(order);
        Interrupt.printSuccess("The order created successfully");
        Interrupt.waitKey();

        System.out.print(Colors.ANSI_RESET);

    }

    public static void displayOrders() {
        do {
            UtilsInOut.clear();
            ArrayList<Order> orders = DBApi.getCustomerOrder(Auth.getCurrentUser().getId()); // 76
            System.out.print(Colors.ANSI_DEFAULT);
            UtilsInOut.printHeader("Your Orders");

            int i = 1;
            for (Order order : orders) {
                UtilsInOut.printContentRow(i++ + ". " + order.getCategory() + " - " + order.getDescription() + " - " + Utils.textToName(order.getStatus().toString().replace("_"," ")));
            }
            if (orders.isEmpty()) {
                UtilsInOut.printContentRow("You does not have any order..");
            }
            UtilsInOut.printContentRow(i + ". " + "Return");

            UtilsInOut.printSeparator();

            int choice = Interrupt.readChoice(i, "Choose order to view: ");
            if (choice != i) {
                displayOrder(orders.get(choice - 1));
            } else {
                break;
            }
        } while (true);


        System.out.print(Colors.ANSI_RESET);
    }

    public static void displayOrder(Order order) {
        UtilsInOut.clear();
        String title = "Your " + order.getCategory().toString();
        UtilsInOut.printHeader(title);
        UtilsInOut.printContentRow("Order name: " + order.getName());
        UtilsInOut.printContentRow("Order status: " + Utils.textToName(order.getStatus().toString().replace("_"," ")));
        UtilsInOut.printContentRow("Order description: " + order.getDescription());
        UtilsInOut.printContentRow("Total price: " + order.getPrice());
        int i = 1;
        if(!Auth.getIsWorker() && order.getStatus() != Order.Status.COMPLETE) UtilsInOut.printContentRow(String.format(" %d. %s",i++,"Cancel order"), Colors.ANSI_GREEN);
        UtilsInOut.printContentRow(String.format(" %d. %s",i,"Return"), Colors.ANSI_GREEN);
        UtilsInOut.printSeparator();
        int choice = Interrupt.readChoice(i);
        if (choice == i - 1) {
            DBApi.cancelOrder(order.getId());
            Interrupt.printSuccess("The order canceled successfully.");
            Interrupt.waitKey();
        }
    }

    // === Worker Page === \\
    public static void displayCurrentOrder() {
        Order order = DBApi.getWorkerCurrentOrder(Auth.getCurrentUser().getId());

        UtilsInOut.printHeader("Your current order");
        if (order == null) {
            UtilsInOut.printContentRow("You dont have order to work at.");
            UtilsInOut.printContentRow("1. Return");
            UtilsInOut.printSeparator();
            Interrupt.readChoice(1);
        } else {
            UtilsInOut.printContentRow("Order name: " + order.getName());
            UtilsInOut.printContentRow("Order description: " + order.getDescription());
            UtilsInOut.printContentRow("Total price: " + order.getPrice());
            UtilsInOut.printContentRow(" 1. Finish order", Colors.ANSI_GREEN);
            UtilsInOut.printContentRow(" 2. Return", Colors.ANSI_GREEN);
            UtilsInOut.printSeparator();
            int choice = Interrupt.readChoice(2);
            if (choice == 1) {
                DBApi.finishWorkOnAnOrder(order.getId());
            }
        }
    }

    public static void displayPreviousOrders() {
        do {
            UtilsInOut.clear();
            UtilsInOut.printHeader("Your Previous Orders");
            ArrayList<Order> orders = DBApi.getWorkerPreviousOrders(Auth.getCurrentUser().getId());
            int i = 1;
            for(Order order:orders) {
                UtilsInOut.printContentRow(String.format("%d. %s - %s",i++,order.getName(),order.getDescription()));
            }
            if(orders.isEmpty()) UtilsInOut.printContentRow("You dont have any previous orders.");
            UtilsInOut.printContentRow(String.format("%d. %s",i,"Return"));
            UtilsInOut.printSeparator();
            int choice = Interrupt.readChoice(i,"Enter order number to view: ");
            if(choice != i) {
                displayOrder(orders.get(choice - 1));
            } else {
                break;
            }
        } while(true);

    }


}
