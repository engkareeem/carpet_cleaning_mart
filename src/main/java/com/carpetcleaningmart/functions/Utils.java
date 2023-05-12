package com.carpetcleaningmart.functions;

public class Utils {

    private Utils(){
        // Do nothing
    }
    public static String textToName(String text) {
        if(text.length() <= 1) return text;
        String[] slices = text.split(" ");
        StringBuilder name = new StringBuilder();
        for(String slice: slices) {
            if (slice.length() > 1) slice = slice.substring(0, 1).toUpperCase() + slice.substring(1).toLowerCase();
            name.append(slice);
            name.append(" ");
        }
        return name.toString();
    }

    public static String getFirstName(String name) {
        if (name.isEmpty()) return "";
        String[] nameSlices = name.split(" ");
        return nameSlices[0];
    }

    public static boolean isNotNum(String number) {
        try {
            Double.parseDouble(number);
            return false;
        } catch (Exception e) {
            return true;
        }
    }
}
