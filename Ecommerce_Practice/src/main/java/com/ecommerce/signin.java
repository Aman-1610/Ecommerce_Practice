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
import org.mindrot.jbcrypt.BCrypt;

@WebServlet("/Signin")
public class signin extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        String email = req.getParameter("email1");
        String password = req.getParameter("password1");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ecommerce_db", "root", "root");

            // Query to check user credentials
            PreparedStatement ps = con.prepareStatement("SELECT password FROM users WHERE email = ?");
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String storedHash = rs.getString("password");
                if (BCrypt.checkpw(password, storedHash)) {
                    resp.setContentType("text/html");
                    out.print("<h3 style='color:green'>Signin successful!</h3>");
                } else {
                    resp.setContentType("text/html");
                    out.print("<h3 style='color:red'>Email and/or password is incorrect.</h3>");
                }
            } else {
                resp.setContentType("text/html");
                out.print("<h3 style='color:red'>Email not registered.</h3>");
            }
            RequestDispatcher rd = req.getRequestDispatcher("/Signin.jsp");
            rd.include(req, resp);

        } catch (Exception e) {
            e.printStackTrace();
            resp.setContentType("text/html");
            out.print("<h3 style='color:red'>Exception Occurred: " + e.getMessage() + "</h3>");
            RequestDispatcher rd = req.getRequestDispatcher("/Signin.jsp");
            rd.include(req, resp);
        }
    }
}
