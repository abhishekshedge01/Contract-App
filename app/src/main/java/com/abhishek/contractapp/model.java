package com.abhishek.contractapp;

public class model {

    String vendor;
    String category;
    String cost;
    String date;

    public model()
    {

    }

    public model(String vendor, String category, String cost, String date) {
        this.vendor = vendor;
        this.category = category;
        this.cost = cost;
        this.date = date;
    }


    public String getVendor() {
        return vendor;
    }

    public String getCategory() {
        return category;
    }

    public String getCost() {
        return cost;
    }

    public String getDate() {
        return date;
    }
}
