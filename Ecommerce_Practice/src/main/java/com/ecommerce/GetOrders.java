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

@WebServlet("/getorders")
public class GetOrders extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        String userId = req.getParameter("user_id");

        if (userId == null || userId.isEmpty()) {
            out.print("<h3 style='color:red'>User ID is required.</h3>");
            return;
        }

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ecommerce_db", "root", "root");
            String query = "SELECT o.order_id, o.order_date, o.shipping_address, p.name AS product_name, p.price " +
                           "FROM orders o JOIN products p ON o.product_id = p.product_id WHERE o.user_id = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, Integer.parseInt(userId));
            ResultSet rs = ps.executeQuery();

            resp.setContentType("text/html");
            out.print("<h2>Your Orders</h2>");
            out.print("<table border='1'><tr><th>Order ID</th><th>Order Date</th><th>Shipping Address</th><th>Product Name</th><th>Price</th></tr>");

            boolean hasOrders = false;
            while (rs.next()) {
                hasOrders = true;
                out.print("<tr><td>" + rs.getInt("order_id") + "</td>" +
                          "<td>" + rs.getTimestamp("order_date") + "</td>" +
                          "<td>" + rs.getString("shipping_address") + "</td>" +
                          "<td>" + rs.getString("product_name") + "</td>" +
                          "<td>" + rs.getBigDecimal("price") + "</td></tr>");
            }

            out.print("</table>");

            if (!hasOrders) {
                out.print("<h3>No orders found for this user.</h3>");
            }

            // Cleanup
            rs.close();
            ps.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
            out.print("<h3 style='color:red'>Error: " + e.getMessage() + "</h3>");
        }
    }
}
