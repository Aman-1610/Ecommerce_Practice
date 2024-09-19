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

@WebServlet("/cart/add")
public class AddToCart extends HttpServlet {
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

            // Check if the product exists
            PreparedStatement checkProduct = con.prepareStatement("SELECT * FROM products WHERE product_id = ?");
            checkProduct.setInt(1, productId);
            ResultSet productResult = checkProduct.executeQuery();

            if (!productResult.next()) {
                out.print("<h3 style='color:red'>Product does not exist.</h3>");
                return;
            }

            // Check if the product is already in the cart
            PreparedStatement checkCart = con.prepareStatement("SELECT * FROM cart WHERE product_id = ? AND user_id = ?");
            checkCart.setInt(1, productId);
            checkCart.setInt(2, userId);
            ResultSet cartResult = checkCart.executeQuery();

            if (cartResult.next()) {
                out.print("<h3 style='color:red'>Product already exists in the cart.</h3>");
                return;
            }

            // Add the product to the cart
            PreparedStatement addProduct = con.prepareStatement("INSERT INTO cart (product_id, user_id) VALUES (?, ?)");
            addProduct.setInt(1, productId);
            addProduct.setInt(2, userId);
            addProduct.executeUpdate();

            out.print("<h3 style='color:green'>Product added to cart successfully!</h3>");

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
            out.print("<h3 style='color:red'>Error: " + e.getMessage() + "</h3>");
        }
    }
}
