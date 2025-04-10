package dao;

import entity.*;
import exception.*;

import java.sql.SQLException;
import java.util.List;

public interface TechShopDAO {

    void registerCustomer(Customers customer) throws SQLException;

    List<Customers> getAllCustomers() throws SQLException;

    Customers getCustomerById(int customerId) throws SQLException;

    void updateCustomer(Customers customer) throws InvalidDataException, SQLException;

    void addProduct(Products product) throws InvalidDataException, SQLException;

    Products getProductById(int productId) throws SQLException;

    List<Products> getAllProducts() throws SQLException;

    void updateProduct(Products product) throws SQLException;

    void deleteProduct(int productId) throws SQLException;

    void placeOrder(Orders order, List<OrderDetails> orderDetailsList)
            throws SQLException, InsufficientStockException;

    Orders getOrderById(int orderId) throws SQLException;

    List<Orders> getAllOrders() throws SQLException;

    void cancelOrder(int orderId) throws SQLException;

    void updateOrderStatus(int orderId, String status) throws SQLException;

    void addInventory(Inventory inventory) throws SQLException;

    void updateInventory(Inventory inventory) throws SQLException, InsufficientStockException;

    Inventory getInventoryByProductId(int productId) throws SQLException;

    List<Inventory> listAllInventory() throws SQLException;

    List<Inventory> listLowStock(int threshold) throws SQLException;

    List<Inventory> listOutOfStock() throws SQLException;

    void closeConnection() throws SQLException;
}
