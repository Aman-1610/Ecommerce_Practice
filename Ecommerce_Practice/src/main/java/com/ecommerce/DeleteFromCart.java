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

@WebServlet("/cart/delete")
public class DeleteFromCart extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        int productId = Integer.parseInt(request.getParameter("product_id"));
        int userId = Integer.parseInt(request.getParameter("user_id")); // Assuming user ID is passed in the request

        String jdbcUrl = "jdbc:mysql://localhost:3306/ecommerce_db";
        String jdbcUser = "root"; // Replace with your DB username
        String jdbcPassword = "root"; // Replace with your DB password

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcPassword);

            // Check if the product exists in the cart
            PreparedStatement checkCart = con.prepareStatement("SELECT * FROM cart WHERE product_id = ? AND user_id = ?");
            checkCart.setInt(1, productId);
            checkCart.setInt(2, userId);
            ResultSet cartResult = checkCart.executeQuery();

            if (!cartResult.next()) {
                out.print("<h3 style='color:red'>Product not found in the cart.</h3>");
                return;
            }

            // Delete the product from the cart
            PreparedStatement deleteProduct = con.prepareStatement("DELETE FROM cart WHERE product_id = ? AND user_id = ?");
            deleteProduct.setInt(1, productId);
            deleteProduct.setInt(2, userId);
            deleteProduct.executeUpdate();

            out.print("<h3 style='color:green'>Product removed from cart successfully!</h3>");
            out.print("<h4>Updated Cart:</h4>");
            displayUpdatedCart(userId, con, out);

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
            out.print("<h3 style='color:red'>Error: " + e.getMessage() + "</h3>");
        }
    }

    private void displayUpdatedCart(int userId, Connection con, PrintWriter out) throws Exception {
        PreparedStatement getCart = con.prepareStatement("SELECT * FROM cart WHERE user_id = ?");
        getCart.setInt(1, userId);
        ResultSet cartItems = getCart.executeQuery();

        out.println("<ul>");
        while (cartItems.next()) {
            int productId = cartItems.getInt("product_id");
            out.println("<li>Product ID: " + productId + "</li>");
        }
        out.println("</ul>");
    }
}
