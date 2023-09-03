<%@ page import="service.TicketService" %>
<%@ page import="dto.TicketDto" %><%--
  Created by IntelliJ IDEA.
  User: rail
  Date: 9/3/23
  Time: 6:21 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>


<html>
<head>
    <title>Tickets</title>
</head>
<body>
<h1>Ticket</h1>
<ul>
    <c:forEach var="ticket" items="${requestScope.tickets}">
        <li>${ticket.seatNo()}</li>
    </c:forEach>

</ul>

</body>
</html>
