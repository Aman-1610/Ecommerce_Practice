<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Remove Product from Cart</title>
</head>
<body>
    <h2>Remove Product from Cart</h2>
    <form action="cart/delete" method="post">
        Product ID: <input type="number" name="product_id" required /><br/><br/>
        User ID: <input type="number" name="user_id" required /><br/><br/>
        <input type="submit" value="Remove from Cart" />
    </form>
</body>
</html>
