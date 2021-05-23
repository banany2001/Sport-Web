<%--
  Created by IntelliJ IDEA.
  User: banan
  Date: 23-May-21
  Time: 14:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Sport</title>
</head>
<body>

    <form action="addGameHandler.jsp" method="POST">
        <table border="0">
            <tr>
                <td>Rival1</td>
                <td>
                    <input type="text" name="participant1">
                </td>
            </tr>
            <tr>
                <td>Rival2</td>
                <td>
                    <input type="text" name="participant2">
                </td>
            </tr>
            <tr>
                <td>Result</td>
                <td>
                    <input type="text" name="result">
                </td>
            </tr>
        </table>
    </form>

</body>
</html>
