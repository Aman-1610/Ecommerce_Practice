<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Manage Cart</title>
</head>
<body>
    <h2>Add Product to Cart</h2>
  <form action="/cart/add" method="post">
    Product ID: <input type="text" name="productId" required /><br/><br/>
    <input type="submit" value="Add to Cart" />
</form>

    
    <h2>Remove Product from Cart</h2>
    <form action="/cart/delete" method="post">
        Product ID: <input type="text" name="productId" required /><br/><br/>
        <input type="submit" value="Remove from Cart" />
    </form>

    <% 
        String message = request.getParameter("message");
        String error = request.getParameter("error");

        if (message != null) {
    %>
        <h3 style="color:green"><%= message %></h3>
    <% 
        } else if (error != null) {
    %>
        <h3 style="color:red"><%= error %></h3>
    <% 
        }
    %>
    
</body>
</html>
