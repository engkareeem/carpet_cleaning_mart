package com.carpetcleaningmart.utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Printer {
    private static final Logger logger = LogManager.getRootLogger();

    private Printer(){
        // Do nothing
    }

    public static void printf(String string, Object... args) {
        if(args.length == 0) {
            logger.info(string);
        } else {
            logger.info(String.format(string, args));
        }
    }

    public static void print(String string){
        if (string != null){
            logger.info(string);
        }

    }

    public static void println(){
        logger.info(String.format("%n"));

    }

    public static void println(String string){
        if (string != null){
            logger.info(String.format("%s%n", string));
        }
    }

    public static void printError(String string){
        logger.error(string);
    }

}
