<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/5.0.0-alpha2/css/bootstrap.min.css"
          integrity="sha384-DhY6onE6f3zzKbjUPRc2hOzGAdEf4/Dz+WJwBvEYL/lkkIsI3ihufq9hk9K4lVoK" crossorigin="anonymous">
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/5.0.0-alpha2/js/bootstrap.bundle.min.js"
            integrity="sha384-BOsAfwzjNJHrJ8cZidOg56tcQWfp6y72vEJ8xQ9w6Quywb24iOsW913URv1IS4GD"
            crossorigin="anonymous"></script>

    <title>Accident</title>
</head>
<body>
<div class="container">
    <div class="row">
        <div>
            Login as : ${user.username}
        </div>
        <a href="<c:url value='/create'/>">Добавить инцидент</a>
        <table class="table table-striped">
            <tr>
                <th>Редактирование</th>
                <th>Номер</th>
                <th>Наименование</th>
                <th>Описание</th>
                <th>Адрес</th>
                <th>Тип</th>
                <th>Нарушения</th>
            </tr>
            <c:forEach items="${accidents}" var="accident">
                <tr>
                    <td><a href="<c:url value='/update?id=${accident.id}'/>">Редактировать инцидент</a></td>
                    <td><c:out value="${accident.id}"/></td>
                    <td><c:out value="${accident.name}"/></td>
                    <td><c:out value="${accident.text}"/></td>
                    <td><c:out value="${accident.address}"/></td>
                    <td><c:out value="${accident.type.name}"/></td>
                    <td>
                        <c:forEach var="rule" items="${accident.rules}">
                            <c:out value="${rule.name}"/><br>
                        </c:forEach>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>
</div>
</body>
</html>