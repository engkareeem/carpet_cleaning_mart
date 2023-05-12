package com.carpetcleaningmart.model;

public class Product {
    public enum Category {
        CARPET(5), BLIND(2), CURTAIN(3), MATTRESS(4), TILE(6);
        public final double price;
        private Category(double price) {
            this.price=price;
        }
    }
    private String id;
    private Category category;
    private String name;
    private String description;

    public Product(){

    }

    public Product(Category category, String name, String description) {
        this.category = category;
        this.name = name;
        this.description = description;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setCategory(String category) {
        if(category.equalsIgnoreCase("CARPET")){
            this.category = Category.CARPET;
        } else if (category.equalsIgnoreCase("BLIND")) {
            this.category = Category.BLIND;
        } else if (category.equalsIgnoreCase("CURTAIN")) {
            this.category = Category.CURTAIN;
        } else if (category.equalsIgnoreCase("MATTRESS")) {
            this.category = Category.MATTRESS;
        } else if (category.equalsIgnoreCase("TILE")) {
            this.category = Category.TILE;
        } else {
            this.category = null;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }





}
