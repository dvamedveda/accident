<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<body>
<c:set var="types" value="${requestScope['types']}"/>
<c:set var="rules" value="${requestScope['rules']}"/>
<form  action="<c:url value='/save'/>" method='POST' enctype="multipart/form-data">
    <table>
        <tr>
            <td>Сообщивший:</td>
            <td><input type='text' name='name'></td>
        </tr>
        <tr>
            <td>Описание:</td>
            <td><input type='text' name='text'></td>
        </tr>
        <tr>
            <td>Место:</td>
            <td><input type='text' name='address'></td>
        </tr>
        <tr>
            <td>Номер машины:</td>
            <td><input type='text' name='carNumber'></td>
        </tr>
        <tr>
            <td>Фото инцидента:</td>
            <td><input type='file' name='accidentPhoto'></td>
        </tr>
        <tr>
            <td>Тип нарушения:</td>
            <td>
                <select name="type.id">
                    <c:forEach var="type" items="${types}" >
                        <option value="${type.id}">${type.name}</option>
                    </c:forEach>
                </select>
            </td>
        </tr>
        <tr>
            <td>Статьи:</td>
            <td>
                <select name="rIds" multiple required>
                    <c:forEach var="rule" items="${rules}" >
                        <option value="${rule.id}">${rule.name}</option>
                    </c:forEach>
                </select>
            </td>
        </tr>
        <tr>
            <td colspan='2'><input name="submit" type="submit" value="Сохранить" /></td>
        </tr>
    </table>
</form>
</body>
</html>