package com.carpetcleaningmart.utils;

import com.carpetcleaningmart.functions.Utils;

import java.util.Scanner;
import java.util.regex.Pattern;

public class Interrupt {

    private Interrupt(){
        // Do nothing
    }
    //=== Menu reader section ===\\
    public static int readChoice(int limit) {
        return readChoice(limit, "Enter your choice: ");
    }

    public static int readChoice(int limit, String message) {
        int choice;
        Scanner scanner = new Scanner(System.in);
        Printer.print(message);
        String input = scanner.next();
        while (Utils.isNotNum(input)) {
            Printer.println(Colors.ANSI_RED + "Your choice is invalid." + Colors.ANSI_DEFAULT);
            Printer.print(message);
            input = scanner.next();
        }
        choice = Integer.parseInt(input);
        if (limit < 1) return -1;
        while (choice < 1 || choice > limit) {
            Printer.println(Colors.ANSI_RED + "Your choice is invalid." + Colors.ANSI_DEFAULT);
            Printer.print(message);
            choice = scanner.nextInt();
        }
        return choice;
    }

    public static double readNumber(String message) {
        Scanner scanner = new Scanner(System.in);
        Printer.print(message);
        String number = scanner.next();
        while (Utils.isNotNum(number)) {
            Printer.println(Colors.ANSI_RED + "The number is invalid." + Colors.ANSI_DEFAULT);
            Printer.print(message);
            number = scanner.next();
        }
        return Double.parseDouble(number);
    }

    public static String readString(String message) {
        Scanner scanner = new Scanner(System.in);
        Printer.print(message);
        return scanner.nextLine();
    }

    public static void waitKey() {
        Scanner scanner = new Scanner(System.in);
        Printer.print(Colors.ANSI_PURPLE + "Enter anything to return... ");
        scanner.next();
        scanner.reset();
    }

    //=== Auth reader section ===\\
    public static String readEmail() {
        Scanner scanner = new Scanner(System.in);
        String message = "Enter email: ";
        Printer.print(message);
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        String email = scanner.next();
        while (!pattern.matcher(email).matches()) {
            printError("The email is invalid.");
            Printer.print(message);
            email = scanner.next();
        }
        return email.toLowerCase();
    }

    public static String readPassword() {
        Scanner scanner = new Scanner(System.in);
        Printer.print("Enter password: ");
        return scanner.next();
    }

    public static String readPhoneNumber() {
        Scanner scanner = new Scanner(System.in);
        String message = "Enter phone number: ";
        Printer.print(message);
        return scanner.next();
    }

    //=== System interrupt section ===\\
    public static void printSuccess(String message) {
        Printer.println(Colors.ANSI_GREEN + message + Colors.ANSI_DEFAULT);
    }

    public static void printError(String message) {
        Printer.println(Colors.ANSI_RED + message + Colors.ANSI_DEFAULT);

    }

}
