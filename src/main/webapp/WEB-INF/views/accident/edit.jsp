<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<body>
<c:set var="accident" value="${requestScope['accident']}" />
<form action="<c:url value='/edit'/>" method='POST'>
    <table>
        <tr>
            <td><input type='text' hidden value="${accident.id}" name='id'></td>
        </tr>
        <tr>
            <td>Имя:</td>
            <td><input type='text' value="${accident.name}" name='name'></td>
        </tr>
        <tr>
            <td>Описание:</td>
            <td><input type='text' value="${accident.text}" name='text'></td>
        </tr>
        <tr>
            <td>Место:</td>
            <td><input type='text' value="${accident.address}" name='address'></td>
        </tr>
        <tr>
            <td colspan='2'><input name="submit" type="submit" value="Сохранить" /></td>
        </tr>
    </table>
</form>
</body>
</html>