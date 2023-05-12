package com.carpetcleaningmart.InOut;

import com.carpetcleaningmart.utils.Colors;
import com.carpetcleaningmart.utils.Printer;

public class UtilsInOut {

    private UtilsInOut(){
        // Do nothing
    }
    public static void printHeader(String title) {
        printSeparator();
        Printer.printf("|%41s%-41s|\n", title.substring(0, title.length() / 2), title.substring(title.length() / 2 + 1));
        printSeparator();
    }

    public static void printSeparator() {
        Printer.println("+----------------------------------------------------------------------------------+");
    }

    public static void clear() {
        Printer.println("\n\n\n\n\n\n\n\n");

    }


    public static void printContentRow(String content) {
        printContentRow(content, Colors.ANSI_DEFAULT);
    }

    public static void printContentRow(String content, String color) {
        Printer.printf("|      " + color + "%-76s" + Colors.ANSI_DEFAULT + "|\n", content);

    }
}
