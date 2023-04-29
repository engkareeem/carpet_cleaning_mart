package com.carpetcleaningmart.Utils;

import com.carpetcleaningmart.Functions.Utils;

import java.util.Scanner;
import java.util.regex.Pattern;

public class Interrupt {
    //=== Menu reader section ===\\
    public static int readChoice(int limit) {
        return readChoice(limit, "Enter your choice: ");
    }

    public static int readChoice(int limit, String message) {
        int choice;
        Scanner scanner = new Scanner(System.in);
        System.out.print(message);
        String input = scanner.next();
        while (!Utils.isNum(input)) {
            System.out.println(Colors.ANSI_RED + "Your choice is invalid." + Colors.ANSI_DEFAULT);
            System.out.print(message);
            input = scanner.next();
        }
        choice = Integer.parseInt(input);
        if (limit < 1) return -1;
        while (choice < 1 || choice > limit) {
            System.out.println(Colors.ANSI_RED + "Your choice is invalid." + Colors.ANSI_DEFAULT);
            System.out.print(message);
            choice = scanner.nextInt();
        }
        return choice;
    }

    public static double readNumber(String message) {
        Scanner scanner = new Scanner(System.in);
        System.out.print(message);
        String number = scanner.next();
        while (!Utils.isNum(number)) {
            System.out.println(Colors.ANSI_RED + "The number is invalid." + Colors.ANSI_DEFAULT);
            System.out.print(message);
            number = scanner.next();
        }
        return Double.parseDouble(number);
    }

    public static String readString(String message) {
        Scanner scanner = new Scanner(System.in);
        System.out.print(message);
        return scanner.nextLine();
    }

    public static void waitKey() {
        Scanner scanner = new Scanner(System.in);
        System.out.print(Colors.ANSI_PURPLE + "Enter anything to return... ");
        scanner.next();
        scanner.reset();
    }

    //=== Auth reader section ===\\
    public static String readEmail() {
        Scanner scanner = new Scanner(System.in);
        String message = "Enter email: ";
        System.out.print(message);
        String regex = "^[\\w!#$%&amp;'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&amp;'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        Pattern pattern = Pattern.compile(regex);
        String email = scanner.next();
        while (!pattern.matcher(email).matches()) {
            printError("The email is invalid.");
            System.out.print(message);
            email = scanner.next();
        }
        return email.toLowerCase();
    }

    public static String readPassword() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter password: ");
        return scanner.next();
    }

    public static String readPhoneNumber() {
        Scanner scanner = new Scanner(System.in);
        String message = "Enter phone number: ";
        System.out.print(message);
        String phone = scanner.next();
        while (!Utils.isNum(phone)) {
            printError("The phone number is invalid.");
            System.out.print(message);
            phone = scanner.next();
        }
        return phone;
    }

    //=== System interrupt section ===\\
    public static void printSuccess(String message) {
        System.out.println(Colors.ANSI_GREEN + message + Colors.ANSI_DEFAULT);
    }

    public static void printError(String message) {
        System.out.println(Colors.ANSI_RED + message + Colors.ANSI_DEFAULT);

    }

}
