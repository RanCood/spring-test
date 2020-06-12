<%@ page import="com.zg.st.servlet.entity.User" %><%--
  Created by IntelliJ IDEA.
  User: zg
  Date: 2020/6/10
  Time: 3:44 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    User user = (User) request.getAttribute("user");
%>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <h1>Hello <%= user.name %>!</h1>
    <p>School Name:
        <span style="color:red">
            <%= user.school.name %>
        </span>
    </p>
    <p>School Address:
        <span style="color:red">
            <%= user.school.address %>
        </span>
    </p>
</body>
</html>
