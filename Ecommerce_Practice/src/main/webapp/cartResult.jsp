<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Cart Result</title>
</head>
<body>
    <h2>Cart Operation Result</h2>
    <%
        // Retrieve the message from the request
        String message = (String) request.getAttribute("message");
        
        // Check if message is not null and display it
        if (message != null) {
    %>
        <h3 style='color:green'><%= message %></h3>
    <%
        } else {
    %>
        <h3 style='color:red'>No message available.</h3>
    <%
        }
    %>
    <a href="products.jsp">Go back to products</a>
</body>
</html>
