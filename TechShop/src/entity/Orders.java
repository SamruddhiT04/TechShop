package entity;

import java.util.Date;

public class Orders {
    private int orderID;
    private Customers customer;
    private Date orderDate;
    private double totalAmount;

   
    public Orders(Customers customer, Date orderDate, double totalAmount) {
        this.customer = customer;
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
    }

    // Add Getters & Setters

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public Customers getCustomer() {
        return customer;
    }

    public void setCustomer(Customers customer) {
        this.customer = customer;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }
}
