<%--
  Created by IntelliJ IDEA.
  User: rail
  Date: 9/2/23
  Time: 11:44 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<div>
    <SPAN>Rus</SPAN>
    <p>Size: ${requestScope.flights.size()}</p>
    <p>Description: ${requestScope.flights.get(9).description()}</p>
    <p>Id: ${requestScope.flights[1].id()}</p>
</div>
</body>
</html>
