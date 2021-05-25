<%--
  Created by IntelliJ IDEA.
  User: banan
  Date: 25-May-21
  Time: 21:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Sport</title>
</head>
<body>

<h2>Add new match</h2>

<form action="match?action=submit" method="POST">
    <table border="0">
        <tr>
            <td>Match's name</td>
            <td>
                <input type="text" name="match_name">
            </td>
        </tr>
        <tr>
            <td>Rival1</td>
            <td>
                <input type="text" name="participant1">
            </td>
        </tr>
        <tr>
            <td>First rival's result</td>
            <td>
                <input type="text" name="participant1_result">
            </td>
        </tr>
        <tr>
            <td>Second rival's result</td>
            <td>
                <input type="text" name="participant2_result">
            </td>
        </tr>
        <tr>
            <td>Rival2</td>
            <td>
                <input type="text" name="participant2">
            </td>
        </tr>
        <tr>
            <td>Day of week</td>
            <td>
                <input type="text" name="day_of_week">
            </td>
        </tr>
        <tr>
            <td>Date</td>
            <td>
                <input type="text" name="date">
            </td>
        </tr>
        <tr>
            <td>Place</td>
            <td>
                <input type="text" name="place">
            </td>
        </tr>
        <tr>
            <td>Attendance < 18</td>
            <td>
                <input type="text" name="attendance_until_18">
            </td>
        </tr>
        <tr>
            <td>Attendance 18-30</td>
            <td>
                <input type="text" name="attendance_until_30">
            </td>
        </tr>
        <tr>
            <td>Attendance 30-45</td>
            <td>
                <input type="text" name="attendance_until_45">
            </td>
        </tr>
        <tr>
            <td>Attendance 45-60</td>
            <td>
                <input type="text" name="attendance_until_60">
            </td>
        </tr>
        <tr>
            <td>Attendance 60+</td>
            <td>
                <input type="text" name="attendance_after_60">
            </td>
        </tr>

    </table>
    <button type="submit">Add</button>
</form>
<a href="/Sport/match">Back to match page</a>

</body>
</html>
