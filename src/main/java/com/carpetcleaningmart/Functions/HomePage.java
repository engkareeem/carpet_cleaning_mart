package com.carpetcleaningmart.Functions;

import com.carpetcleaningmart.InOut.MainInOut;
import com.carpetcleaningmart.InOut.OrdersInOut;
import com.carpetcleaningmart.InOut.UtilsInOut;

public class HomePage {
    static String loggedAs = "customer";
    public static void homePage() {
        int choice = MainInOut.mainMenu(loggedAs);
        if(choice==1) {
            UtilsInOut.clear();
            OrdersInOut.newOrder();
        }
    }
}
