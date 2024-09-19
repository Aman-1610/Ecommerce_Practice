<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Add Product</title>
</head>
<body>
    <h2>Add a New Product</h2>
    <form action="addproduct" method="post">
        Name: <input type="text" name="name" required /><br/><br/>
        Description: <textarea name="description" required></textarea><br/><br/>
        Price: <input type="text" name="price" required /><br/><br/>
        Category: <input type="text" name="category" required /><br/><br/>
        <input type="submit" value="Add Product" />
    </form>

</body>
</html>
