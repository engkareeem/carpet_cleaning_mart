package com.carpetcleaningmart.Functions;

import com.carpetcleaningmart.InOut.*;
import com.carpetcleaningmart.Utils.Auth;
import com.carpetcleaningmart.Utils.Interrupt;

public class HomePage {

    public static void homePage() {
        do {
            if (!Auth.isLoggedIn()) {
                AuthInOut.authorizeUser();
                if(!Auth.isLoggedIn()) {
                    Interrupt.printError("An error occurred, please try again after few moments..");
                    System.exit(0);
                }
                Interrupt.printSuccess("Welcome " + Auth.getCurrentUser().getName());
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            if (Auth.isLoggedIn()) {
                MainInOut.mainMenu();
            }
        } while (true);
    }

}
