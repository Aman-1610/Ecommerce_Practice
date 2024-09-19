<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Delete Product</title>
</head>
<body>
    <h2>Delete Product</h2>
    <form action="deleteproduct" method="post">
        Product ID: <input type="text" name="productId" required /><br/><br/>
        <input type="submit" value="Delete Product" />
    </form>
</body>
</html>
