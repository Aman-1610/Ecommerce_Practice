<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>View Cart</title>
</head>
<body>
    <h2>View Your Cart</h2>
    <form action="cart" method="get">
        User ID: <input type="number" name="user_id" required /><br/><br/>
        <input type="submit" value="Get Cart" />
    </form>
</body>
</html>
