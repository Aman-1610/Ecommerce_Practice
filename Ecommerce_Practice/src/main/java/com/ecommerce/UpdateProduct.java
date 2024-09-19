package com.ecommerce;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/updateproduct")
public class UpdateProduct extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();

        // Retrieve parameters from the request
        String productId = req.getParameter("productId");
        String name = req.getParameter("name");
        String description = req.getParameter("description");
        String priceStr = req.getParameter("price");
        String category = req.getParameter("category");

        // Input validation: check if all required fields are provided
        if (productId == null || name == null || priceStr == null || category == null || name.isEmpty() || priceStr.isEmpty()) {
            out.println("<h3 style='color:red'>All required fields must be provided!</h3>");
            RequestDispatcher rd = req.getRequestDispatcher("updateproduct.jsp");
            rd.include(req, resp);
            return;
        }

        // Validate the price
        double price = 0;
        try {
            price = Double.parseDouble(priceStr);
            if (price <= 0) {
                out.println("<h3 style='color:red'>Price must be a positive number!</h3>");
                RequestDispatcher rd = req.getRequestDispatcher("updateproduct.jsp");
                rd.include(req, resp);
                return;
            }
        } catch (NumberFormatException e) {
            out.println("<h3 style='color:red'>Invalid price value!</h3>");
            RequestDispatcher rd = req.getRequestDispatcher("updateproduct.jsp");
            rd.include(req, resp);
            return;
        }

        // Database connection
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ecommerce_db", "root", "root");

            // Check if the product ID exists
            PreparedStatement checkProduct = con.prepareStatement("SELECT * FROM products WHERE product_id = ?");
            checkProduct.setString(1, productId);
            if (!checkProduct.executeQuery().next()) {
                out.println("<h3 style='color:red'>Product ID not found!</h3>");
                RequestDispatcher rd = req.getRequestDispatcher("updateproduct.jsp");
                rd.include(req, resp);
                return;
            }

            // Update product query
            String updateQuery = "UPDATE products SET name = ?, description = ?, price = ?, category = ? WHERE product_id = ?";
            PreparedStatement ps = con.prepareStatement(updateQuery);
            ps.setString(1, name);
            ps.setString(2, description);
            ps.setDouble(3, price);
            ps.setString(4, category);
            ps.setString(5, productId);

            // Execute the update
            int result = ps.executeUpdate();
            if (result > 0) {
                out.println("<h3 style='color:green'>Product updated successfully!</h3>");
            } else {
                out.println("<h3 style='color:red'>Failed to update product. Please try again.</h3>");
            }
            
            // Forward back to the JSP for confirmation
            RequestDispatcher rd = req.getRequestDispatcher("updateproduct.jsp");
            rd.include(req, resp);

        } catch (Exception e) {
            e.printStackTrace();
            out.println("<h3 style='color:red'>Exception occurred: " + e.getMessage() + "</h3>");
            RequestDispatcher rd = req.getRequestDispatcher("updateproduct.jsp");
            rd.include(req, resp);
        }
    }
}
