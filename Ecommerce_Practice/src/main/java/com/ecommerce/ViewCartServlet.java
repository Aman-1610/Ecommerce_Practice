package com.ecommerce;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/cart/view")
public class ViewCartServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int userId = Integer.parseInt(request.getParameter("user_id")); // Assuming user ID is provided
        
        List<Product> cartProducts = new ArrayList<>();
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ecommerce_db", "root", "root");
            String query = "SELECT p.* FROM cart c JOIN products p ON c.product_id = p.product_id WHERE c.user_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Product product = new Product(
                    resultSet.getInt("product_id"),
                    resultSet.getString("name"),
                    resultSet.getString("description"),
                    resultSet.getDouble("price"),
                    resultSet.getString("category")
                );
                cartProducts.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        request.setAttribute("cartProducts", cartProducts);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/cart/cart.jsp");
        dispatcher.forward(request, response);
    }
}
