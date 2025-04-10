package entity;

import java.time.LocalDate;

public class Inventory {
    private int inventoryID;
    private Products product;
    private int quantityInStock;
    private LocalDate lastStockUpdate;

    public Inventory(int inventoryID, Products product, int quantityInStock) {
        this.inventoryID = inventoryID;
        this.product = product;
        this.quantityInStock = quantityInStock;
        this.lastStockUpdate = LocalDate.now();
    }

    public Products getProduct() { return product; }
    public int getQuantityInStock() { return quantityInStock; }

    public void addToInventory(int quantity) {
        quantityInStock += quantity;
        lastStockUpdate = LocalDate.now();
    }

    public void removeFromInventory(int quantity) {
        if (quantity > quantityInStock) throw new IllegalArgumentException("Insufficient stock");
        quantityInStock -= quantity;
        lastStockUpdate = LocalDate.now();
    }

    public void updateStockQuantity(int newQuantity) {
        quantityInStock = newQuantity;
        lastStockUpdate = LocalDate.now();
    }

    public boolean isProductAvailable(int quantityToCheck) {
        return quantityToCheck <= quantityInStock;
    }

    public double getInventoryValue() {
        return product.getPrice() * quantityInStock;
    }
}
