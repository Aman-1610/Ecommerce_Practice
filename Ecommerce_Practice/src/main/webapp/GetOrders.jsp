<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Get Orders</title>
</head>
<body>
    <h2>Retrieve Your Orders</h2>
    <form action="getorders" method="post">
        User ID: <input type="text" name="user_id" required /><br/><br/>
        <input type="submit" value="Get Orders" />
    </form>
</body>
</html>
