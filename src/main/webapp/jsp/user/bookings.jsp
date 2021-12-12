<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="page" value="/jsp/user/bookings.jsp" scope="request"/>
<fmt:setLocale value="${not empty sessionScope.locale ? sessionScope.locale : pageContext.request.locale}"
               scope="session"/>
<fmt:setBundle basename="messages"/>

<html>
<head>
    <title><fmt:message key="label.bookings"/></title>
</head>
<body>
<div>
    <c:import url="../common/language-form.jsp"/>
</div>
<div>
    <c:import url="../common/header.jsp"/>
</div>
<table>
    <c:forEach var="booking" items="${requestScope.bookings}">
        <tr>
            <td>
                <form action="<c:url value="/controller"/>" method="post">
                    <input type="hidden" name="command" value="booking-detail">
                    <input type="hidden" name="booking-id" value="${ booking.bookingId }">
                    <input type="submit" name="submit"
                           value="<c:out value="${ booking.arrival} - ${booking.departure}"/>">
                </form>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
