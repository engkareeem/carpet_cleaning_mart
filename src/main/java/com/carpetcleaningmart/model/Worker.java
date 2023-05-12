package com.carpetcleaningmart.model;

public class Worker extends User {
    public enum WorkerType {EMPLOYEE, ADMIN}

    private WorkerType type;



    public Worker(){

    }

    public Worker(String id, String name, String phone, String address, String email, WorkerType type) {
        super(id, name, phone, address, email);
        this.type = type;
    }

    public WorkerType getType() {
        return type;
    }

    public void setType(WorkerType type) {
        this.type = type;
    }

    public void setType(String type) {
        if(type.equalsIgnoreCase("EMPLOYEE")){
            this.type = WorkerType.EMPLOYEE;
        } else if (type.equalsIgnoreCase("ADMIN")) {
            this.type = WorkerType.ADMIN;
        } else {
            this.type = null;
        }
    }

}
