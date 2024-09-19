package com.ecommerce;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/placeorder")
public class PlaceOrder extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        int userId = Integer.parseInt(request.getParameter("user_id")); // Assuming user ID is passed in the request
        String shippingAddress = request.getParameter("shipping_address"); // Get shipping address

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ecommerce_db", "root", "root");

            // Check if the cart is not empty
            String checkCartQuery = "SELECT COUNT(*) FROM cart WHERE user_id = ?";
            PreparedStatement checkCartStmt = con.prepareStatement(checkCartQuery);
            checkCartStmt.setInt(1, userId);
            ResultSet rs = checkCartStmt.executeQuery();
            if (rs.next() && rs.getInt(1) == 0) {
                response.setContentType("text/html");
                out.print("<h3 style='color:red'>Your cart is empty. Please add products before placing an order.</h3>");
                return;
            }

            // Insert order into the orders table
            String insertOrderQuery = "INSERT INTO orders (user_id, product_id, shipping_address) VALUES (?, ?, ?)";
            PreparedStatement insertOrderStmt = con.prepareStatement(insertOrderQuery, PreparedStatement.RETURN_GENERATED_KEYS);

            // Fetch product IDs from the cart and create orders
            String fetchCartQuery = "SELECT product_id FROM cart WHERE user_id = ?";
            PreparedStatement fetchCartStmt = con.prepareStatement(fetchCartQuery);
            fetchCartStmt.setInt(1, userId);
            ResultSet cartRs = fetchCartStmt.executeQuery();
            while (cartRs.next()) {
                int productId = cartRs.getInt("product_id");

                // Set parameters for the order
                insertOrderStmt.setInt(1, userId);
                insertOrderStmt.setInt(2, productId);
                insertOrderStmt.setString(3, shippingAddress);
                insertOrderStmt.executeUpdate();
            }

            // Return success message with the order ID
            response.setContentType("text/html");
            out.print("<h3 style='color:green'>Order placed successfully!</h3>");
        } catch (Exception e) {
            e.printStackTrace();
            response.setContentType("text/html");
            out.print("<h3 style='color:red'>An error occurred: " + e.getMessage() + "</h3>");
        }
    }
}
