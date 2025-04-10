package entity;

public class OrderDetails {
    private int orderDetailID;
    private Orders order;
    private Products product;
    private int quantity;
    private double discount;

    // Default constructor
    public OrderDetails() {
        this.discount = 0.0;
    }

    // Parameterized constructor
    public OrderDetails(int orderDetailID, Orders order, Products product, int quantity) {
        this.orderDetailID = orderDetailID;
        this.order = order;
        this.product = product;
        this.quantity = quantity;
        this.discount = 0.0;
    }

    // Method to calculate subtotal with discount
    public double calculateSubtotal() {
        return (product.getPrice() * quantity) * (1 - discount);
    }

    // Method to update quantity
    public void updateQuantity(int quantity) {
        this.quantity = quantity;
    }

    // Method to apply discount
    public void addDiscount(double discount) {
        this.discount = discount;
    }

    // Display order detail information
    public String getOrderDetailInfo() {
        return "OrderDetailID: " + orderDetailID +
               ", Product: " + product.getProductDetails() +
               ", Quantity: " + quantity +
               ", Subtotal: " + calculateSubtotal();
    }

    // Getters and setters
    public int getOrderDetailID() {
        return orderDetailID;
    }

    public void setOrderDetailID(int orderDetailID) {
        this.orderDetailID = orderDetailID;
    }

    public Orders getOrder() {
        return order;
    }

    public void setOrder(Orders order) {
        this.order = order;
    }

    public Products getProduct() {
        return product;
    }

    public void setProduct(Products product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }
}
