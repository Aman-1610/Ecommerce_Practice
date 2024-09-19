package com.ecommerce;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/addproduct")
public class AddProduct extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String price = request.getParameter("price");
        String category = request.getParameter("category");

        // Input validation
        if (name == null || description == null || price == null || category == null || name.isEmpty() || description.isEmpty() || category.isEmpty()) {
            out.println("<h3 style='color:red'>All fields are required!</h3>");
            return;
        }

        try {
            double productPrice = Double.parseDouble(price);
            if (productPrice <= 0) {
                out.println("<h3 style='color:red'>Price must be a positive number!</h3>");
                return;
            }

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ecommerce_db", "root", "root");

            PreparedStatement ps = con.prepareStatement("INSERT INTO products (name, description, price, category) VALUES (?, ?, ?, ?)");
            ps.setString(1, name);
            ps.setString(2, description);
            ps.setDouble(3, productPrice);
            ps.setString(4, category);

            int result = ps.executeUpdate();

            if (result > 0) {
                out.println("<h3 style='color:green'>Product added successfully!</h3>");
            } else {
                out.println("<h3 style='color:red'>Failed to add product!</h3>");
            }
        } catch (NumberFormatException e) {
            out.println("<h3 style='color:red'>Invalid price format!</h3>");
        } catch (Exception e) {
            e.printStackTrace();
            out.println("<h3 style='color:red'>Exception occurred: " + e.getMessage() + "</h3>");
        }
    }
}
