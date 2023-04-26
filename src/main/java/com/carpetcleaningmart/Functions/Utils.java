package com.carpetcleaningmart.Functions;

public class Utils {
    public static String textToName(String text) {
        String name = text;
        if(name.length() > 1) name = name.substring(0,1).toUpperCase() + name.substring(1).toLowerCase();
        return name;
    }
    public static String getFirstName(String name) {
        if(name.isEmpty()) return "";
        String[] nameSlices = name.split(" ");
        return nameSlices[0];
    }
    public static boolean isNum(String number) {
        try {
            Double.parseDouble(number);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
