package com.ecommerce;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/products")
public class GetAllProducts extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<h2>Product List</h2>");
        
        String jdbcUrl = "jdbc:mysql://localhost:3306/ecommerce_db";
        String jdbcUser = "root"; // Replace with your DB username
        String jdbcPassword = "root"; // Replace with your DB password
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcPassword);
            PreparedStatement ps = con.prepareStatement("SELECT * FROM products");
            ResultSet rs = ps.executeQuery();

            if (!rs.isBeforeFirst()) { // Check if no rows found
                out.println("<p>No products available.</p>");
            } else {
                out.println("<table border='1'><tr><th>Product ID</th><th>Name</th><th>Description</th><th>Price</th><th>Category</th></tr>");
                while (rs.next()) {
                    out.println("<tr>");
                    out.println("<td>" + rs.getInt("product_id") + "</td>");
                    out.println("<td>" + rs.getString("name") + "</td>");
                    out.println("<td>" + rs.getString("description") + "</td>");
                    out.println("<td>" + rs.getBigDecimal("price") + "</td>");
                    out.println("<td>" + rs.getString("category") + "</td>");
                    out.println("</tr>");
                }
                out.println("</table>");
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
            out.println("<h3 style='color:red'>Error retrieving products: " + e.getMessage() + "</h3>");
        }
    }
}
