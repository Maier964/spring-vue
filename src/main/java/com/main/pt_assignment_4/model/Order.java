package com.main.pt_assignment_4.model;

import java.time.LocalDateTime;
import java.util.List;
import java.io.Serializable;
import java.util.Objects;

public class Order implements Serializable {

    private int orderID;
    private Client client;
    private List<MenuItem> items;
    private LocalDateTime orderDate;
    private int totalPrice;

    public Order(){
    }

    public Order(int orderID, Client client, List<MenuItem> items, LocalDateTime orderDate) {
        this.orderID = orderID;
        this.client = client;
        this.items = items;
        this.orderDate = orderDate;
        this.totalPrice = 0;
        for ( MenuItem item : items ){
            totalPrice += item.computePrice();
        }
    }

    public Order(int orderID, Client client, List<MenuItem> items) {
        this.orderID = orderID;
        this.client = client;
        this.items = items;
        this.orderDate = LocalDateTime.now();
        this.totalPrice = 0;
        for ( MenuItem item : items ){
            totalPrice += item.computePrice();
        }
    }


    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        orderID = orderID;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public List<MenuItem> getItems() {
        return items;
    }

    public void setItems(List<MenuItem> items) {
        this.items = items;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return orderID == order.orderID && totalPrice == order.totalPrice && client.equals(order.client) && items.equals(order.items) && orderDate.equals(order.orderDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderID, client, items, orderDate, totalPrice);
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderID=" + orderID +
                ",\nclient=" + client +
                ",\nitems=" + items +
                ",\norderDate=" + orderDate +
                ",\ntotalPrice=" + totalPrice +
                '}';
    }
}
