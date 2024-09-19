<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Signin Page</title>
</head>
<body>
    <h2>User Signin</h2>
    <form action="Signin" method="post">
        Email: <input type="text" name="email1" required /><br/><br/>
        Password: <input type="password" name="password1" required /><br/><br/>
        <input type="submit" value="Signin" />
    </form>
    <a href="Signup.jsp">Go to Signup</a> <!-- Link to Signup page -->
</body>
</html>
