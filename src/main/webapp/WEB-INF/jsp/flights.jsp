<%--
  Created by IntelliJ IDEA.
  User: rail
  Date: 9/3/23
  Time: 8:10 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Flights</title>
</head>
<body>
<h1>List of flights</h1>
<c:if test="${not empty requestScope.flights}">
    <c:forEach var="flight" items="${requestScope.flights}">
        <li><a href="${pageContext.request.contextPath}/tickets?flightId=${flight.id()}">${flight.description()}</a></li>
    </c:forEach>
</c:if>

</body>
</html>
