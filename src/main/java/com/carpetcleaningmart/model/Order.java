package com.carpetcleaningmart.model;

public class Order extends Product {
    public enum Status {WAITING, IN_TREATMENT, COMPLETE}

    private Status status;

    private Double price;

    private String customerId;

    public Order() {

    }

    public Order(Category category, String name, String description, Status status, Double price, String customerId) {
        super(category, name, description);
        this.status = status;
        this.price = price;
        this.customerId = customerId;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setStatus(String status) {
        if(status.equalsIgnoreCase("WAITING")){
            this.status = Status.WAITING;
        } else if (status.equalsIgnoreCase("IN_TREATMENT")) {
            this.status = Status.IN_TREATMENT;
        } else if (status.equalsIgnoreCase("COMPLETE")) {
            this.status = Status.COMPLETE;
        } else {
            this.status = null;
        }
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    @Override
    public String toString() {
        return super.toString() + "Order{" +
                "status=" + status +
                ", price=" + price +
                ", customerId='" + customerId + '\'' +
                '}';
    }
}
