<%--
  Created by IntelliJ IDEA.
  User: banan
  Date: 23-May-21
  Time: 00:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Sport</title>
</head>
<body>

<h2>Tennis matches</h2>
<table border="5">
    <tr>
        <td>ID</td>
        <td>Game's name</td>
        <td>Rival1</td>
        <td>Rival2</td>
        <td>Rival1 won</td>
        <td>Rival2 won</td>
        <td>Date</td>
        <td>Place</td>
        <td>Attendance < 18</td>
        <td>Attendance 18-30</td>
        <td>Attendance 30-45</td>
        <td>Attendance 45-60</td>
        <td>Attendance 60+</td>
        <td>Total attendance</td>
        <td>Options</td>
    </tr>
    <c:forEach var="item" items="${list}">
        <tr>
            <td>${item.uuid}</td>
            <td>${item.matchName}</td>
            <td>${item.matchParticipant1.name}</td>
            <td>${item.matchParticipant2.name}</td>
            <c:choose>
                <c:when test="${item.matchParticipant1.result == true}">
                    <c:set var="result1">Yes</c:set>
                </c:when>
                <c:otherwise>
                    <c:set var="result1">No</c:set>
                </c:otherwise>
            </c:choose>
            <c:choose>
                <c:when test="${item.matchParticipant2.result == true}">
                    <c:set var="result2">Yes</c:set>
                </c:when>
                <c:otherwise>
                    <c:set var="result2">No</c:set>
                </c:otherwise>
            </c:choose>
            <td>${result1}</td>
            <td>${result2}</td>
            <td>${item.date}</td>
            <td>${item.place}</td>
            <td>${item.attendance.peopleUntil18}</td>
            <td>${item.attendance.peopleUntil30}</td>
            <td>${item.attendance.peopleUntil45}</td>
            <td>${item.attendance.peopleUntil60}</td>
            <td>${item.attendance.peopleAfter60}</td>
            <td>${item.attendance.total}</td>
            <td>
                <button onclick="location.href='/delete'">Delete</button>
                <button onclick="location.href='/edit'">Edit</button>
            </td>
        </tr>
    </c:forEach>
</table>
<button onclick="location.href='/add'">Add</button>
<a href="/Sport">Back to main page</a>

</body>
</html>
