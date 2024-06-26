package com.carpetcleaningmart.functions;
import com.carpetcleaningmart.model.Product;

public class OrdersPage {

    private OrdersPage(){
        // Do nothing
    }
    public static double calculatePrice(Product.Category category, double height, double width) {
        double size = height * width;
        return size * category.price;
    }

    public static double getDiscount(double price,int timeServed) {
        double discount = 0;
        if (timeServed > 10) {
            if (price > 600) discount = 0.08;
            else if (price > 200) discount = 0.05;
        } else if (timeServed > 3) {
            if (price > 600) discount = 0.04;
            if (price > 400) discount = 0.02;
        }
        return discount;
    }
}
