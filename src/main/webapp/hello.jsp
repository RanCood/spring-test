<%--
  Created by IntelliJ IDEA.
  User: zg
  Date: 2020/6/10
  Time: 3:38 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>hello jsp</title>
</head>
<body>
    <%-- JSP Comment --%>
    <h1>Hello World!</h1>
    <p>
        <%
            out.println("Your IP address is ");
        %>
        <span style="color:red">
            <%= request.getRemoteAddr() %>
        </span>
    </p>
</body>
</html>
