package com.carpetcleaningmart.model;

public class Worker extends User {
    public enum WorkerType {EMPLOYEE, ADMIN}

    private WorkerType type;

    private String orderId;

    public Worker(){

    }

    public Worker(String id, String name, String phone, String address, String email, WorkerType type, String orderId) {
        super(id, name, phone, address, email);
        this.type = type;
        this.orderId = orderId;
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

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    @Override
    public String toString() {
        return "Worker{" +
                "type=" + type +
                ", orderId='" + orderId + '\'' +
                '}';
    }
}
