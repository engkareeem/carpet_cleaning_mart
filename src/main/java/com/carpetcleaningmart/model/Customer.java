package com.carpetcleaningmart.model;


public class Customer extends User {

    private Integer timesServed;

    public Customer(){

    }

    public Customer(String id, String name, String phone, String address, String email, Integer timesServed) {
        super(id, name, phone, address, email);
        this.timesServed = timesServed;
    }

    public Integer getTimesServed() {
        return timesServed;
    }

    public void setTimesServed(Integer timesServed) {
        this.timesServed = timesServed;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "timesServed=" + timesServed +
                '}';
    }
}
