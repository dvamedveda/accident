<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<body>
<c:set var="accident" value="${requestScope['accident']}" />
<c:set var="types" value="${requestScope['types']}"/>
<c:set var="rules" value="${requestScope['rules']}"/>
<form action="<c:url value='/edit'/>" method='POST' enctype="multipart/form-data">
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
            <td>Номер машины:</td>
            <td><input type='text' value="${accident.carNumber}" name='carNumber'></td>
        </tr>
        <tr>
            <td colspan="2">
                <c:if test="${accident.encodedPhoto != ''}">
                    <img src="data:image/jpeg;base64,${accident.encodedPhoto}" width="300" height="200" />
                </c:if>
            </td>
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
                        <option
                                value="${type.id}"
                                <c:if test="${type.id == accident.type.id}">
                                    selected
                                </c:if>
                        >${type.name}</option>
                    </c:forEach>
                </select>
            </td>
        </tr>
        <tr>
            <td>Статьи:</td>
            <td>
                <select name="rIds" multiple required>
                    <c:forEach var="rule" items="${rules}" >
                        <option
                                value="${rule.id}"
                                <c:if test="${accident.rules.contains(rule)}">
                                    selected
                                </c:if>
                        >${rule.name}
                        </option>
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