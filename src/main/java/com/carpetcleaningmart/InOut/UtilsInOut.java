package com.carpetcleaningmart.InOut;

import com.carpetcleaningmart.Utils.Colors;

public class UtilsInOut {
    public static void printHeader(String title) {
        printSeparator();
        System.out.printf("|%38s%-38s|\n", title.substring(0, title.length() / 2), title.substring(title.length() / 2 + 1));
        printSeparator();
    }

    public static void printSeparator() {
        System.out.println("+----------------------------------------------------------------------------+");
    }

    public static void clear() {
        System.out.println("\n\n\n\n\n\n\n\n");
        System.out.flush();
    }


    public static void printContentRow(String content) {
        printContentRow(content, Colors.ANSI_DEFAULT);
    }

    public static void printContentRow(String content, String color) {
        System.out.printf("|      " + color + "%-70s" + Colors.ANSI_DEFAULT + "|\n", content);

    }
}
