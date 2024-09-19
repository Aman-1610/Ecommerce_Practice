<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Add Product to Cart</title>
</head>
<body>
    <h2>Add Product to Cart</h2>
    <form action="cart/add" method="post">
        Product ID: <input type="number" name="product_id" required /><br/><br/>
        User ID: <input type="number" name="user_id" required /><br/><br/>
        <input type="submit" value="Add to Cart" />
    </form>
</body>
</html>
