package dao.impl;

import dao.TechShopDAO;
import entity.*;
import exception.*;
import util.DBConnUtil;
import util.DBPropertyUtil;

import java.sql.*;
import java.util.*;

public class TechShopDAOImpl implements TechShopDAO {
    private Connection conn;

    public TechShopDAOImpl() throws SQLException {
        try {
            this.conn = DBConnUtil.getConnection(); // ✅ Assign to instance variable
        } catch (Exception e) {
            throw new SQLException("Failed to establish database connection", e);
        }
    }


    @Override
    public void registerCustomer(Customers customer) throws SQLException {
        String query = "INSERT INTO Customers (FirstName, LastName, Email, Phone, Address) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, customer.getFirstName());
            pstmt.setString(2, customer.getLastName());
            pstmt.setString(3, customer.getEmail());
            pstmt.setString(4, customer.getPhone());
            pstmt.setString(5, customer.getAddress());
            pstmt.executeUpdate();
        }
    }

    @Override
    public List<Products> getAllProducts() throws SQLException {
        List<Products> products = new ArrayList<>();
        String query = "SELECT * FROM Products";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Products p = new Products();
                p.setProductID(rs.getInt("ProductID"));
                p.setProductName(rs.getString("ProductName"));
                p.setDescription(rs.getString("Description"));
                p.setPrice(rs.getDouble("Price"));
                products.add(p);
            }
        }
        return products;
    }

    @Override
    public void placeOrder(Orders order, List<OrderDetails> orderDetailsList) throws SQLException, InsufficientStockException {
        try {
            conn.setAutoCommit(false);

            String insertOrder = "INSERT INTO Orders (CustomerID, OrderDate, TotalAmount) VALUES (?, ?, ?)";
            try (PreparedStatement orderStmt = conn.prepareStatement(insertOrder, Statement.RETURN_GENERATED_KEYS)) {
                orderStmt.setInt(1, order.getCustomer().getCustomerID());
                orderStmt.setDate(2, new java.sql.Date(order.getOrderDate().getTime()));
                orderStmt.setDouble(3, order.getTotalAmount());
                orderStmt.executeUpdate();

                try (ResultSet keys = orderStmt.getGeneratedKeys()) {
                    int orderId = 0;
                    if (keys.next()) {
                        orderId = keys.getInt(1);
                    }

                    for (OrderDetails od : orderDetailsList) {
                        int productId = od.getProduct().getProductID();
                        int quantity = od.getQuantity();

                        String checkStock = "SELECT QuantityInStock FROM Inventory WHERE ProductID = ?";
                        try (PreparedStatement stockStmt = conn.prepareStatement(checkStock)) {
                            stockStmt.setInt(1, productId);
                            try (ResultSet rs = stockStmt.executeQuery()) {
                                if (rs.next()) {
                                    int stock = rs.getInt("QuantityInStock");
                                    if (stock < quantity) {
                                        throw new InsufficientStockException("Insufficient stock for Product ID: " + productId);
                                    }
                                } else {
                                    throw new SQLException("Product not found in inventory.");
                                }
                            }
                        }

                        String insertDetail = "INSERT INTO OrderDetails (OrderID, ProductID, Quantity) VALUES (?, ?, ?)";
                        try (PreparedStatement detailStmt = conn.prepareStatement(insertDetail)) {
                            detailStmt.setInt(1, orderId);
                            detailStmt.setInt(2, productId);
                            detailStmt.setInt(3, quantity);
                            detailStmt.executeUpdate();
                        }

                        String updateInventory = "UPDATE Inventory SET QuantityInStock = QuantityInStock - ? WHERE ProductID = ?";
                        try (PreparedStatement invStmt = conn.prepareStatement(updateInventory)) {
                            invStmt.setInt(1, quantity);
                            invStmt.setInt(2, productId);
                            invStmt.executeUpdate();
                        }
                    }
                }
            }

            conn.commit();
        } catch (SQLException | InsufficientStockException e) {
            conn.rollback();
            throw e;
        } finally {
            conn.setAutoCommit(true);
        }
    }

    @Override
    public void updateProduct(Products product) throws SQLException {
        String query = "UPDATE Products SET ProductName=?, Description=?, Price=? WHERE ProductID=?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, product.getProductName());
            pstmt.setString(2, product.getDescription());
            pstmt.setDouble(3, product.getPrice());
            pstmt.setInt(4, product.getProductID());
            pstmt.executeUpdate();
        }
    }

    @Override
    public void closeConnection() throws SQLException {
        if (conn != null && !conn.isClosed()) {
            conn.close();
        }
    }

    // ------------------------- Stub Methods --------------------------

    @Override public Customers getCustomerById(int customerId) throws SQLException { return null; }
    @Override public List<Customers> getAllCustomers() throws SQLException { return null; }
    @Override public void updateCustomer(Customers customer) throws InvalidDataException, SQLException {}
    @Override public void addProduct(Products product) throws InvalidDataException, SQLException {}
    @Override public Products getProductById(int productId) throws SQLException { return null; }
    @Override public void deleteProduct(int productId) throws SQLException {}
    @Override public Orders getOrderById(int orderId) throws SQLException { return null; }
    @Override public List<Orders> getAllOrders() throws SQLException { return null; }
    @Override public void cancelOrder(int orderId) throws SQLException {}
    @Override public void updateOrderStatus(int orderId, String status) throws SQLException {}
    @Override public void addInventory(Inventory inventory) throws SQLException {}
    @Override public void updateInventory(Inventory inventory) throws InsufficientStockException, SQLException {}
    @Override public Inventory getInventoryByProductId(int productId) throws SQLException { return null; }
    @Override public List<Inventory> listAllInventory() throws SQLException { return null; }
    @Override public List<Inventory> listLowStock(int threshold) throws SQLException { return null; }
    @Override public List<Inventory> listOutOfStock() throws SQLException { return null; }
}
