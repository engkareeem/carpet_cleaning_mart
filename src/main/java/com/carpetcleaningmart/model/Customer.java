package com.carpetcleaningmart.model;


public class Customer extends User {

    private Integer timesServed;

    public Customer(){
        // Do nothing
    }


    public Integer getTimesServed() {
        return timesServed;
    }

    public void setTimesServed(Integer timesServed) {
        this.timesServed = timesServed;
    }

}
