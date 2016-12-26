<%--
  Created by IntelliJ IDEA.
  User: tt
  Date: 2016/11/22
  Time: 21:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="/login" method="post">
    <input type="text" name="username">
    <input type="password" name="password">
    <input type="hidden"
           name="${_csrf.parameterName}" value="${_csrf.token}">
    <input type="submit" value="submit">
</form>
</body>
</html>
