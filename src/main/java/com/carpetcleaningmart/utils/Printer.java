package com.carpetcleaningmart.utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Printer {
    private static final Logger logger = LogManager.getRootLogger();;

    public static void printf(String string, Object... args) {
        logger.info(String.format(string, args));
    }

    public static void print(String string){
        logger.info(string);

    }

    public static void println(){
        logger.info(String.format("%n"));

    }

    public static void println(String string){
        logger.info(String.format("%s%n", string));

    }

    public static void printError(String string){
        logger.error(string);
    }

}
