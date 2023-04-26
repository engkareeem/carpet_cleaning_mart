package com.carpetcleaningmart.InOut;

import com.carpetcleaningmart.Functions.Utils;
import com.carpetcleaningmart.Utils.Auth;
import com.carpetcleaningmart.Utils.Colors;
import com.carpetcleaningmart.Utils.Interrupt;
import com.carpetcleaningmart.model.Product;

import java.util.Objects;
import java.util.Scanner;

public class OrdersInOut {
    public static void newOrder() {
        Product.Category category;
        Scanner scanner = new Scanner(System.in);
        int choice = -1;
        System.out.print(Colors.ANSI_DEFAULT);
        System.out.println("+----------------------------------------------------------------------------+");
        System.out.println("|                                 New Order                                  |");
        System.out.println("+----------------------------------------------------------------------------+");
        System.out.println("|     Select product category                                                |");
        System.out.println("|        1. Carpet                                                           |");
        System.out.println("|        2. Cover                                                            |");
        System.out.println("|        3. Mattress                                                         |");
        System.out.println("+----------------------------------------------------------------------------+");
        System.out.print("Enter your selection: ");
        choice = Interrupt.readChoice(3);
        if(choice == 1) {
            category = Product.Category.CARPET;
        } else if(choice == 2) {
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
        description += ", Height: " + height + ", Width: " + width;
        String name = Utils.getFirstName(Auth.getCurrentUser().getName()) + "'s " +  Utils.textToName(category.toString()); // TODO: User name + category
        Product product = new Product(category, name,description);
        System.out.print(Colors.ANSI_RESET);
        System.out.println(product.toString());
    }


}
