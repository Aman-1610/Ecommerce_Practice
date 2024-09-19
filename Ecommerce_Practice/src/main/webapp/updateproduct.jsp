<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Update Product</title>
</head>
<body>
    <h2>Update Product</h2>
    <form action="updateproduct" method="post">
        Product ID: <input type="text" name="productId" required /><br/><br/>
        Name: <input type="text" name="name" required /><br/><br/>
        Description: <textarea name="description"></textarea><br/><br/>
        Price: <input type="number" name="price" required /><br/><br/>
        Category: <input type="text" name="category" required /><br/><br/>
        <input type="submit" value="Update Product" />
    </form>
</body>
</html>
