package com.carpetcleaningmart;

import com.carpetcleaningmart.Functions.HomePage;
import com.carpetcleaningmart.Utils.Auth;
import com.carpetcleaningmart.Utils.DBApi;
import com.carpetcleaningmart.Utils.DBConnection;
import com.carpetcleaningmart.model.Worker;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Auth.logIn("naser@gmail.com", "54321");
        HomePage.homePage();

//        ArrayList<Worker> workers = DBApi.getFreeWorkers();
//        for(Worker worker : workers){
//            System.out.println(worker);
//        }
//        DBApi.assignWorkerToAnOrder("2", "1");
//
//        workers = DBApi.getFreeWorkers();
//        for(Worker worker : workers){
//            System.out.println(worker);
//        }
//
//        DBApi.finishWorkOnAnOrder("1");

    }

}