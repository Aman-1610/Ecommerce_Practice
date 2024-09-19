<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Place Order</title>
</head>
<body>
    <h2>Place Order</h2>
    <form action="placeorder" method="post">
        User ID: <input type="text" name="user_id" required /><br/><br/>
        Shipping Address: <input type="text" name="shipping_address" required /><br/><br/>
        <input type="submit" value="Place Order" />
    </form>
</body>
</html>
