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

@WebServlet("/deleteproduct")
public class DeleteProduct extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();

        // Retrieve product ID from the request
        String productId = req.getParameter("productId");

        // Input validation: check if product ID is provided
        if (productId == null || productId.isEmpty()) {
            out.println("<h3 style='color:red'>Product ID must be provided!</h3>");
            RequestDispatcher rd = req.getRequestDispatcher("deleteproduct.jsp");
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
                RequestDispatcher rd = req.getRequestDispatcher("deleteproduct.jsp");
                rd.include(req, resp);
                return;
            }

            // Delete product query
            String deleteQuery = "DELETE FROM products WHERE product_id = ?";
            PreparedStatement ps = con.prepareStatement(deleteQuery);
            ps.setString(1, productId);

            // Execute the delete
            int result = ps.executeUpdate();
            if (result > 0) {
                out.println("<h3 style='color:green'>Product deleted successfully!</h3>");
            } else {
                out.println("<h3 style='color:red'>Failed to delete product. Please try again.</h3>");
            }

            // Forward back to the JSP for confirmation
            RequestDispatcher rd = req.getRequestDispatcher("deleteproduct.jsp");
            rd.include(req, resp);

        } catch (Exception e) {
            e.printStackTrace();
            out.println("<h3 style='color:red'>Exception occurred: " + e.getMessage() + "</h3>");
            RequestDispatcher rd = req.getRequestDispatcher("deleteproduct.jsp");
            rd.include(req, resp);
        }
    }
}
