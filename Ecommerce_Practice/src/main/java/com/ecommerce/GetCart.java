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

@WebServlet("/cart")
public class GetCart extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        int userId;

        // Validate user ID
        try {
            userId = Integer.parseInt(request.getParameter("user_id")); // Assuming user ID is passed in the request
        } catch (NumberFormatException e) {
            out.print("<h3 style='color:red'>Invalid user ID.</h3>");
            return;
        }

        String jdbcUrl = "jdbc:mysql://localhost:3306/ecommerce_db";
        String jdbcUser = "root"; // Replace with your DB username
        String jdbcPassword = "root"; // Replace with your DB password

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcPassword);

            // Retrieve cart details
            PreparedStatement getCart = con.prepareStatement(
                "SELECT p.product_id, p.name, p.description, p.price, c.user_id " +
                "FROM cart c " +
                "JOIN products p ON c.product_id = p.product_id " +
                "WHERE c.user_id = ?"
            );
            getCart.setInt(1, userId);
            ResultSet cartItems = getCart.executeQuery();

            // Set content type for HTML response
            response.setContentType("text/html");
            out.println("<html><head><title>Your Cart</title>");
            out.println("<style>table { width: 100%; border-collapse: collapse; }");
            out.println("th, td { padding: 8px; text-align: left; border: 1px solid #ddd; }");
            out.println("th { background-color: #f2f2f2; }</style></head><body>");

            if (!cartItems.isBeforeFirst()) { // Check if there are no items in the cart
                out.print("<h3 style='color:orange'>Your cart is empty.</h3>");
                return;
            }

            double totalAmount = 0;
            out.print("<h2>Your Cart</h2>");
            out.print("<table><tr><th>Product ID</th><th>Name</th><th>Description</th><th>Price</th></tr>");

            while (cartItems.next()) {
                int productId = cartItems.getInt("product_id");
                String name = cartItems.getString("name");
                String description = cartItems.getString("description");
                double price = cartItems.getDouble("price");
                
                out.print("<tr>");
                out.print("<td>" + productId + "</td>");
                out.print("<td>" + name + "</td>");
                out.print("<td>" + description + "</td>");
                out.print("<td>$" + price + "</td>");
                out.print("</tr>");

                totalAmount += price; // Assuming quantity is always 1 for each product in the cart
            }
            out.print("</table>");
            out.print("<h3>Total Amount: $" + totalAmount + "</h3>");
            out.print("</body></html>");

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
            out.print("<h3 style='color:red'>Error: " + e.getMessage() + "</h3>");
        }
    }
}
