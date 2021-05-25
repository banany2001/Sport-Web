<%--
  Created by IntelliJ IDEA.
  User: banan
  Date: 26-May-21
  Time: 00:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Sport</title>
</head>
<body>

<h2>Attendance statistics</h2>
<table border="5">
    <tr>
        <td></td>
        <td>Monday</td>
        <td>Tuesday</td>
        <td>Wednesday</td>
        <td>Thursday</td>
        <td>Friday</td>
        <td>Saturday</td>
        <td>Sunday</td>
    </tr>
    <tr>
        <td>Average attendance</td>
        <td>${monday}</td>
        <td>${tuesday}</td>
        <td>${wednesday}</td>
        <td>${thursday}</td>
        <td>${friday}</td>
        <td>${saturday}</td>
        <td>${sunday}</td>
    </tr>
</table>


<h3>Events sorted by attendance</h3>
<table border="5">
    <tr>
        <td>ID</td>
        <td>Date</td>
        <td>Place</td>
        <td>Attendance</td>
    </tr>
    <c:forEach var="item" items="${list}">
        <tr>
            <td>${item.uuid}${item.eventType}</td>
            <td>${item.date}</td>
            <td>${item.place}</td>
            <td>${item.attendance.total}</td>
        </tr>
    </c:forEach>
</table>

<form action="attendance-statistics?action=sort" method="post">
    <table border="0">
        <tr>
            <td>Sort by 18-:</td>
            <td>
                <input type="radio" name="radios" value="18-"/>
            </td>
        </tr>
        <tr>
            <td>Sort by 18-30:</td>
            <td>
                <input type="radio" name="radios" value="18-30"/>
            </td>
        </tr>
        <tr>
            <td>Sort by 30-45:</td>
            <td>
                <input type="radio" name="radios" value="30-45"/>
            </td>
        </tr>
        <tr>
            <td>Sort by 45-60:</td>
            <td>
                <input type="radio" name="radios" value="45-60"/>
            </td>
        </tr>
        <tr>
            <td>Sort by 60+:</td>
            <td>
                <input type="radio" name="radios" value="60+"/>
            </td>
        </tr>
    </table>
    <button type="submit">Sort</button>
</form>

<a href="/Sport">Back to main page</a>

</body>
</html>
