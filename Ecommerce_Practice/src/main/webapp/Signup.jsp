<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Signup Page</title>
</head>
<body>
    <h2>User Signup</h2>
    <form action="Signup" method="post">
        Name: <input type="text" name="name1" required /><br/><br/>
        Email: <input type="email" name="email1" required /><br/><br/>
        Password: <input type="password" name="password1" required /><br/><br/>
        Address: <input type="text" name="address1" required /><br/><br/>
        <input type="submit" value="Signup" />
    </form>
    <a href="Signin.jsp">Go to Signin</a> <!-- Link to Signin page -->
</body>
</html>
