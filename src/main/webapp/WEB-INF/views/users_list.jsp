<%--
  Created by IntelliJ IDEA.
  User: tt
  Date: 2016/11/21
  Time: 11:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<ul>
    <c:forEach items="${users}" var="inspector">
        <li>${inspector.id}|${inspector.username}|${inspector.gender}</li>
    </c:forEach>
</ul>
</body>
</html>
