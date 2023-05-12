package com.carpetcleaningmart.functions;

import com.carpetcleaningmart.inout.*;
import com.carpetcleaningmart.utils.Auth;
import com.carpetcleaningmart.utils.Interrupt;

public class HomePage {

    private HomePage(){
        // Do nothing
    }
    public static void homePage() {
        do {
            if (!Auth.isLoggedIn()) {
                AuthInOut.authorizeUser();
                if(!Auth.isLoggedIn()) {
                    Interrupt.printError("An error occurred, please try again after few moments..");
                    System.exit(0);
                }
                Interrupt.printSuccess("Welcome " + Auth.getCurrentUser().getName());
            }
            if (Auth.isLoggedIn()) {
                MainInOut.mainMenu();
            }
        } while (true);
    }

}
