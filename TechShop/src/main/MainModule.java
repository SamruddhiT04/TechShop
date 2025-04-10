package main;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import dao.TechShopDAO;
import dao.impl.TechShopDAOImpl;
import entity.Customers;
import entity.Inventory;
import entity.OrderDetails;
import entity.Orders;
import entity.Products;
import exception.*;

public class MainModule {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        TechShopDAO dao = null;

        try {
            dao = new TechShopDAOImpl();
            boolean exit = false;

            while (!exit) {
                System.out.println("\n===== TechShop Management System =====");
                System.out.println("1. Register Customer");
                System.out.println("2. Add Product");
                System.out.println("3. Place Order");
                System.out.println("4. View Orders");
                System.out.println("5. Manage Inventory");
                System.out.println("6. Exit");
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // consume newline

                switch (choice) {
                    case 1:
                        System.out.println("\n--- Register Customer ---");
                        System.out.print("First Name: ");
                        String fName = scanner.nextLine();
                        System.out.print("Last Name: ");
                        String lName = scanner.nextLine();
                        System.out.print("Email: ");
                        String email = scanner.nextLine();
                        System.out.print("Phone: ");
                        String phone = scanner.nextLine();
                        System.out.print("Address: ");
                        String address = scanner.nextLine();

                        Customers customer = new Customers(fName, lName, email, phone, address);
                        dao.registerCustomer(customer);
                        System.out.println("Customer registered successfully.");
                        break;

                    case 2:
                        System.out.println("\n--- Add Product ---");
                        System.out.print("Product Name: ");
                        String pName = scanner.nextLine();
                        System.out.print("Description: ");
                        String desc = scanner.nextLine();
                        System.out.print("Price: ");
                        double price = scanner.nextDouble();
                        scanner.nextLine();

                        Products product = new Products(pName, desc, price);
                        dao.addProduct(product);
                        System.out.println("Product added successfully.");
                        break;

                    case 3:
                        System.out.println("\n--- Place Order ---");
                        System.out.print("Enter Customer ID: ");
                        int custId = scanner.nextInt();
                        scanner.nextLine();

                        Customers orderCustomer = dao.getCustomerById(custId);
                        if (orderCustomer == null) {
                            System.out.println("Customer not found.");
                            break;
                        }

                        List<OrderDetails> orderDetailsList = new ArrayList<>();
                        double total = 0.0;
                        boolean addingItems = true;

                        while (addingItems) {
                            System.out.print("Enter Product ID: ");
                            int prodId = scanner.nextInt();
                            scanner.nextLine();

                            Products orderProd = dao.getProductById(prodId);
                            if (orderProd == null) {
                                System.out.println("Product not found.");
                                continue;
                            }

                            System.out.print("Quantity: ");
                            int quantity = scanner.nextInt();
                            scanner.nextLine();

                            OrderDetails od = new OrderDetails();
                            od.setProduct(orderProd);
                            od.setQuantity(quantity);
                            orderDetailsList.add(od);

                            total += orderProd.getPrice() * quantity;

                            System.out.print("Add another product? (y/n): ");
                            String ans = scanner.nextLine();
                            addingItems = ans.equalsIgnoreCase("y");
                        }

                        Orders order = new Orders(orderCustomer, new Date(), total);

                        try {
                            dao.placeOrder(order, orderDetailsList);
                            System.out.println("Order placed successfully.");
                        } catch (InsufficientStockException e) {
                            System.out.println("Insufficient stock: " + e.getMessage());
                        }
                        break;

                    case 4:
                        System.out.println("\n--- View Orders ---");
                        List<Orders> orders = dao.getAllOrders();
                        for (Orders o : orders) {
                            System.out.println("Order ID: " + o.getOrderID() +
                                    ", Customer: " + o.getCustomer().getFirstName() +
                                    ", Date: " + o.getOrderDate() +
                                    ", Total: " + o.getTotalAmount());
                        }
                        break;

                    case 5:
                        System.out.println("\n--- Inventory Management ---");
                        List<Inventory> inventoryList = dao.listAllInventory();
                        for (Inventory inv : inventoryList) {
                            System.out.println("Product ID: " + inv.getProduct().getProductID() +
                                    ", Name: " + inv.getProduct().getProductName() +
                                    ", Quantity: " + inv.getQuantityInStock());
                        }
                        break;

                    case 6:
                        exit = true;
                        dao.closeConnection();
                        System.out.println("Exiting... Goodbye!");
                        break;

                    default:
                        System.out.println("Invalid choice.");
                }
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace(); 
        } finally {
            scanner.close();
        }
    }
}
