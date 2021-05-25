<%--
  Created by IntelliJ IDEA.
  User: banan
  Date: 25-May-21
  Time: 22:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Sport</title>
</head>
<body>

<h2>Edit the existing game</h2>

<jsp:useBean id="item" scope="request" type="by.bsu.fpmi.siachko.lab1.sportevent.events.game.Game"/>
<form action="game?action=edit&id=${item.uuid}" method="POST">
    <table border="0">
        <tr>
            <td>Game's name</td>
            <td>
                <input type="text" name="games_name" value=${item.gameName} placeholder="${item.gameName}">
            </td>
        </tr>
        <tr>
            <td>Rival1</td>
            <td>
                <input type="text" name="participant1" value="${item.gameParticipant1.name}" placeholder="${item.gameParticipant1.name}">
            </td>
        </tr>
        <tr>
            <td>First rival's result</td>
            <td>
                <input type="text" name="participant1_result" value="${item.gameParticipant1.points}" placeholder="${item.gameParticipant1.points}">
            </td>
        </tr>
        <tr>
            <td>Second rival's result</td>
            <td>
                <input type="text" name="participant2_result" value="${item.gameParticipant2.points}" placeholder="${item.gameParticipant2.points}">
            </td>
        </tr>
        <tr>
            <td>Rival2</td>
            <td>
                <input type="text" name="participant2" value="${item.gameParticipant2.name}" placeholder="${item.gameParticipant2.name}">
            </td>
        </tr>
        <tr>
            <td>Day of week</td>
            <td>
                <input type="text" name="day_of_week" value="${item.date.dayOfWeek}" placeholder="${item.date.dayOfWeek}">
            </td>
        </tr>
        <tr>
            <td>Date</td>
            <td>
                <input type="text" name="date" value="${item.date.dayNumber}" placeholder="${item.date.dayNumber}">
            </td>
        </tr>
        <tr>
            <td>Place</td>
            <td>
                <input type="text" name="place" value="${item.place}" placeholder="${item.place}">
            </td>
        </tr>
        <tr>
            <td>Attendance < 18</td>
            <td>
                <input type="text" name="attendance_until_18" value="${item.attendance.peopleUntil18}" placeholder="${item.attendance.peopleUntil18}">
            </td>
        </tr>
        <tr>
            <td>Attendance 18-30</td>
            <td>
                <input type="text" name="attendance_until_30" value="${item.attendance.peopleUntil30}" placeholder="${item.attendance.peopleUntil30}">
            </td>
        </tr>
        <tr>
            <td>Attendance 30-45</td>
            <td>
                <input type="text" name="attendance_until_45" value="${item.attendance.peopleUntil45}" placeholder="${item.attendance.peopleUntil45}">
            </td>
        </tr>
        <tr>
            <td>Attendance 45-60</td>
            <td>
                <input type="text" name="attendance_until_60" value="${item.attendance.peopleUntil60}" placeholder="${item.attendance.peopleUntil60}">
            </td>
        </tr>
        <tr>
            <td>Attendance 60+</td>
            <td>
                <input type="text" name="attendance_after_60" value="${item.attendance.peopleAfter60}" placeholder="${item.attendance.peopleAfter60}">
            </td>
        </tr>

    </table>
    <button type="submit">Edit</button>
</form>

</body>
</html>
