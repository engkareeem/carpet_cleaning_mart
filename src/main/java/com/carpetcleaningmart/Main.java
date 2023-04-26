package com.carpetcleaningmart;

import com.carpetcleaningmart.Utils.DBApi;
import com.carpetcleaningmart.Utils.DBConnection;
import com.carpetcleaningmart.model.Worker;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        //DBApi.addWorker("Ahmad Yasin", "0592323186", "Nablus", "ahmad@gmail.com", Worker.WorkerType.ADMIN.toString());
//        DBApi.updateWorker("1", "Ahmad Yasen", "0592323186", "Nablus", "ahmad@gmail.com", Worker.WorkerType.EMPLOYEE.toString());
//        DBApi.deleteWorker("1");

        ArrayList<Worker> workers = DBApi.getFreeWorkers();
        for(Worker worker : workers){
            System.out.println(worker);
        }
        DBApi.assignWorkerToAnOrder("2", "1");

        workers = DBApi.getFreeWorkers();
        for(Worker worker : workers){
            System.out.println(worker);
        }

        DBApi.finishWorkOnAnOrder("1");
    }

}