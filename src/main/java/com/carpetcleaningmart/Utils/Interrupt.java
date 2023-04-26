package com.carpetcleaningmart.Utils;

import com.carpetcleaningmart.Functions.Utils;

import java.util.Scanner;

public class Interrupt {
    public static int readChoice(int limit) {
        int choice = -1;
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your choice: ");
        choice = scanner.nextInt();
        while(choice < 1 || choice > limit) {
            System.out.println(Colors.ANSI_RED + "Your choice is invalid." + Colors.ANSI_DEFAULT);
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
        }
        return choice;
    }
    public static double readNumber(String message) {
        Scanner scanner = new Scanner(System.in);
        System.out.print(message);
        String number = scanner.next();
        while(!Utils.isNum(number)) {
            System.out.println(Colors.ANSI_RED + "The number is invalid." + Colors.ANSI_DEFAULT);
            System.out.print(message);
            number = scanner.next();
        }
        return Double.parseDouble(number);
    }
}
