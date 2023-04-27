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
    public static void createNewOrder() {
        Product.Category category;
        Scanner scanner = new Scanner(System.in);
        int choice;

        System.out.print(Colors.ANSI_DEFAULT);
        System.out.println("+----------------------------------------------------------------------------+");
        System.out.println("|                                 New Order                                  |");
        System.out.println("+----------------------------------------------------------------------------+");
        System.out.println("|     Select product category                                                |");
        System.out.println("|        1. Carpet                                                           |");
        System.out.println("|        2. Cover                                                            |");
        System.out.println("|        3. Mattress                                                         |");
        System.out.println("+----------------------------------------------------------------------------+");
        choice = Interrupt.readChoice(3);

        if (choice == 1) {
            category = Product.Category.CARPET;
        } else if (choice == 2) {
            category = Product.Category.COVER;
        } else {
            category = Product.Category.MATTRESS;
        }

        System.out.println("+----------------------------------------------------------------------------+");
        System.out.println("|      Please enter product dimensions.                                      |");
        double height = Interrupt.readNumber("|        Height: ");
        double width = Interrupt.readNumber("|        width: ");

        System.out.println("+----------------------------------------------------------------------------+");
        System.out.println("|      Please describe the product.                                          |");
        System.out.println("+----------------------------------------------------------------------------+");
        System.out.print("Enter the description: ");
        scanner.reset();
        String description = scanner.nextLine();
        description = String.format("%.1f*%.1f %s, %s", height, width, Utils.textToName(category.toString()), description);
        String name = Utils.getFirstName(Auth.getCurrentUser().getName()) + "`s " + Utils.textToName(category.toString()); // TODO: User name + category

        double price = OrdersPage.calculatePrice(category, height, width);

        Order order = new Order(category, name, description, Order.Status.WAITING, price, Auth.getCurrentUser().getId());
        DBApi.addOrder(order);
        Interrupt.printSuccess("The order created successfully");
        Interrupt.readChoice(0, "Enter any number to return..");

        System.out.print(Colors.ANSI_RESET);

    }

    public static void displayOrders() {
        do {
            UtilsInOut.clear();
            ArrayList<Order> orders = DBApi.getCustomerOrder(Auth.getCurrentUser().getId()); // 76
            System.out.print(Colors.ANSI_DEFAULT);
            System.out.println("+----------------------------------------------------------------------------+");
            System.out.println("|                                Your Orders                                 |");
            System.out.println("+----------------------------------------------------------------------------+");

            int i = 1;
            for (Order order : orders) {
                Interrupt.printContentRow(i++ + ". " + order.getCategory() + " - " + order.getDescription());
            }
            if (orders.isEmpty()) {
                Interrupt.printContentRow("You does not have any order..");
            }
            Interrupt.printContentRow(i + ". " + "Return");

            System.out.println("+----------------------------------------------------------------------------+");

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
        System.out.println("+----------------------------------------------------------------------------+");
        System.out.printf("|%38s%-38s|\n", title.substring(0, title.length() / 2), title.substring(title.length() / 2 + 1));
        System.out.println("+----------------------------------------------------------------------------+");
        Interrupt.printContentRow("Order name: " + order.getName());
        Interrupt.printContentRow("Order status: " + order.getStatus());
        Interrupt.printContentRow("Order description: " + order.getDescription());
        Interrupt.printContentRow("Total price: " + order.getPrice());
        Interrupt.printContentRow(" 1. Cancel order", Colors.ANSI_GREEN);
        Interrupt.printContentRow(" 2. Return", Colors.ANSI_GREEN);
        System.out.println("+----------------------------------------------------------------------------+");
        int choice = Interrupt.readChoice(2);
        if (choice == 1) {
            DBApi.cancelOrder(order.getId());
            Interrupt.printSuccess("The order canceled successfully.");
            Interrupt.readChoice(0, "Enter any number to return..");
        }
    }

    public static void displayCurrentOrder() {
        Order order = DBApi.getWorkerCurrentOrder(Auth.getCurrentUser().getId());

        System.out.println("+----------------------------------------------------------------------------+");
        System.out.println("|                             Your current order                             |");
        System.out.println("+----------------------------------------------------------------------------+");
        if (order == null) {
            Interrupt.printContentRow("You dont have order to work at.");
            Interrupt.printContentRow("1. Return");
            System.out.println("+----------------------------------------------------------------------------+");
            Interrupt.readChoice(1);
        } else {
            Interrupt.printContentRow("Order name: " + order.getName());
            Interrupt.printContentRow("Order description: " + order.getDescription());
            Interrupt.printContentRow("Total price: " + order.getPrice());
            Interrupt.printContentRow(" 1. Finish order", Colors.ANSI_GREEN);
            Interrupt.printContentRow(" 2. Return", Colors.ANSI_GREEN);
            System.out.println("+----------------------------------------------------------------------------+");
            int choice = Interrupt.readChoice(2);
            if (choice == 1) {
                DBApi.finishWorkOnAnOrder(order.getId());
            }
        }


    }


}
