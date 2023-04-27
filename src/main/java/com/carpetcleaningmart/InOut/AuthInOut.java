package com.carpetcleaningmart.InOut;

import com.carpetcleaningmart.Utils.Auth;
import com.carpetcleaningmart.Utils.Colors;
import com.carpetcleaningmart.Utils.Interrupt;

public class AuthInOut {
    public static void authorizeUser() {
        UtilsInOut.clear();
        System.out.println(Colors.ANSI_DEFAULT);
        System.out.println("+----------------------------------------------------------------------------+");
        System.out.println("|                               Authentication                               |");
        System.out.println("+----------------------------------------------------------------------------+");
        Interrupt.printContentRow("1. Login");
        Interrupt.printContentRow("2. Register new account");
        Interrupt.printContentRow("3. Exit");
        System.out.println("+----------------------------------------------------------------------------+");

        int choice = Interrupt.readChoice(3);
        if (choice == 1) {
            while (!Auth.isLoggedIn()) {
                String email = Interrupt.readEmail();
                String password = Interrupt.readPassword();
                Auth.logIn(email,password);
            }
        } else if(choice == 2) {
            String name = Interrupt.readString("Enter your name: ");
            String email = Interrupt.readEmail();
            String phone = Interrupt.readPhoneNumber();
            String address = Interrupt.readString("Enter your address: ");
            String password = Interrupt.readPassword();
            Auth.signUp(email,name,address,phone,password);
        } else {
            System.exit(0);
        }
    }

}
