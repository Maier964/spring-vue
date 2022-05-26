package com.main.pt_assignment_4.util;

public class JSONOrderWrapper {
    String id, client, products, date;

    public JSONOrderWrapper(String id, String client, String products, String date) {
        this.id = id;
        this.client = client;
        this.products = products;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getProducts() {
        return products;
    }

    public void setProducts(String products) {
        this.products = products;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
