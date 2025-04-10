package entity;

public class Products {
    private int productID;
    private String productName;
    private String description;
    private double price;

    public Products() {
    }

    public Products(String name, String description, double price) {
        this.productName = name;
        this.description = description;
        this.price = price;
    }
    
    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    // ✅ Custom method to return product details as string
    public String getProductDetails() {
        return "ProductID: " + productID + ", Name: " + productName + ", Description: " + description + ", Price: " + price;
    }
}
