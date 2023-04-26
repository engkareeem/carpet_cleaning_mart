package com.carpetcleaningmart.InOut;

import com.carpetcleaningmart.Utils.Colors;
import com.carpetcleaningmart.Utils.Interrupt;

import java.util.Objects;
import java.util.Scanner;

public class MainInOut {
    public static int mainMenu(String role) {
        Scanner scanner = new Scanner(System.in);
        int choice = -1;
        System.out.print(Colors.ANSI_DEFAULT);
        if(Objects.equals(role, "customer")) {
            System.out.println("+----------------------------------------------------------------------------+");
            System.out.println("|                                 Main Menu                                  |");
            System.out.println("+----------------------------------------------------------------------------+");
            System.out.println("|     1. Make new order                                                      |");
            System.out.println("|     2. Track your orders                                                   |");
            System.out.println("|     3. Logout                                                              |");
            System.out.println("+----------------------------------------------------------------------------+");
            choice = Interrupt.readChoice(3);
        } else if(Objects.equals(role, "worker")) {
            System.out.println("+----------------------------------------------------------------------------+");
            System.out.println("|                                 Main Menu                                  |");
            System.out.println("+----------------------------------------------------------------------------+");
            System.out.println("|     1. View current order                                                  |");
            System.out.println("|     2. Logout                                                              |");
            System.out.println("+----------------------------------------------------------------------------+");
            choice = Interrupt.readChoice(2);
        }  else if(Objects.equals(role, "admin")) {
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
        return choice;
    }
}