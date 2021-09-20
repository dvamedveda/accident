<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <title>Accident</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"
            integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj"
            crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.3/dist/umd/popper.min.js"
            integrity="sha384-eMNCOe7tC1doHpGoWe/6oMVemdAVTMs2xqW4mwXrXsW0L84Iytr2wi5v2QjrP/xp"
            crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/js/bootstrap.min.js"
            integrity="sha384-cn7l7gDp0eyniUwwAZgrzD06kc/tftFf19TOAs2zVinnD/C7E91j9yyk5//jjpt/"
            crossorigin="anonymous"></script>
</head>
<body>
    <div class="container pt-3 px-3 mx-auto">
        <h3 class="text-center">
            Hello : Accident
        </h3>
        <div class="d-flex justify-content-between">
            <a class="btn btn-primary" href="<c:url value='/create'/>">Добавить инцидент</a>
            <div class="d-flex justify-content-end">
                <button type="button" class="btn btn-outline-success mx-2">Вы вошли как: ${requestScope['user'].username}</button>
                <a class="btn btn-primary" href="<c:url value='/logout'/>">Выйти</a>
            </div>
        </div>
        <table class="table table-sm table-striped">
            <thead>
                <tr>
                    <th scope="col">ID инцидента</th>
                    <th scope="col">Описание</th>
                    <th scope="col">Сообщивший</th>
                    <th scope="col">Место</th>
                    <th scope="col"></th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="accident" items="${requestScope['accidents']}">
                    <c:out value="<tr>" escapeXml="false" />
                    <c:out value="<th scope='row'>${accident.id}</th>" escapeXml="false"/>
                    <c:out value="<td>${accident.text}</td>" escapeXml="false" />
                    <c:out value="<td>${accident.name}</td>" escapeXml="false" />
                    <c:out value="<td>${accident.address}</td>" escapeXml="false" />
                    <c:set var="editUrl" value="update?id=${accident.id}"/>
                    <c:out value="<td>
                                    <a class=\"btn btn-outline-secondary\"
                                    href=\"${editUrl}\">
                                            Редактировать
                                    </a>
                                   </td>"
                           escapeXml="false" />
                    <c:out value="</tr>" escapeXml="false" />
                </c:forEach>
            </tbody>
        </table>
    </div>
</body>
</html>