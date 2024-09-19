package com.ecommerce;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.mindrot.jbcrypt.BCrypt;

@WebServlet("/Signup")
public class signup extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        String name = req.getParameter("name1");
        String email = req.getParameter("email1");
        String password = req.getParameter("password1");
        String address = req.getParameter("address1");

        // Validate input data
        if (!isValidEmail(email) || !isValidPassword(password)) {
            resp.setContentType("text/html");
            out.print("<h3 style='color:red'>Invalid email format or password strength.</h3>");
            RequestDispatcher rd = req.getRequestDispatcher("/Signup.jsp");
            rd.include(req, resp);
            return;
        }

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ecommerce_db", "root", "root");

            // Check if email already exists
            PreparedStatement checkEmail = con.prepareStatement("SELECT * FROM users WHERE email = ?");
            checkEmail.setString(1, email);
            ResultSet rs = checkEmail.executeQuery();
            if (rs.next()) {
                resp.setContentType("text/html");
                out.print("<h3 style='color:red'>Email is already registered.</h3>");
                RequestDispatcher rd = req.getRequestDispatcher("/Signup.jsp");
                rd.include(req, resp);
                return;
            }

            // Hash the password
            String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

            // Insert new user
            PreparedStatement ps = con.prepareStatement("INSERT INTO users (name, email, password, address) VALUES (?, ?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, hashedPassword);
            ps.setString(4, address);

            int rowsInserted = ps.executeUpdate();
            if (rowsInserted > 0) {
                ResultSet generatedKeys = ps.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int userId = generatedKeys.getInt(1);
                    resp.setContentType("text/html");
                    out.print("<h3 style='color:green'>Registration successful! Your customer ID is: " + userId + "</h3>");
                } else {
                    resp.setContentType("text/html");
                    out.print("<h3 style='color:red'>Registration failed. Try again.</h3>");
                }
                RequestDispatcher rd = req.getRequestDispatcher("/Signup.jsp");
                rd.include(req, resp);
            } else {
                resp.setContentType("text/html");
                out.print("<h3 style='color:red'>Registration failed. Try again.</h3>");
                RequestDispatcher rd = req.getRequestDispatcher("/Signup.jsp");
                rd.include(req, resp);
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            resp.setContentType("text/html");
            out.print("<h3 style='color:red'>Exception Occurred: " + e.getMessage() + "</h3>");
            RequestDispatcher rd = req.getRequestDispatcher("/Signup.jsp");
            rd.include(req, resp);
        }
    }

    // Validate email format
    private boolean isValidEmail(String email) {
        return email != null && email.matches("^[\\w-_.+]*[\\w-_.]@([\\w]+\\.)+[\\w]+[\\w]$");
    }

    // Validate password strength
    private boolean isValidPassword(String password) {
        // Password should be at least 8 characters long and contain a mix of letters and digits
        return password != null && password.length() >= 8 && password.matches(".*[a-zA-Z].*") && password.matches(".*[0-9].*");
    }
}
